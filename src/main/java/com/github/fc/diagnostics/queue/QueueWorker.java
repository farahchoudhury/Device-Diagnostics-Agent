package com.github.fc.diagnostics.queue;

import com.github.fc.diagnostics.logging.DiagnosticsLoggers;
import com.github.fc.diagnostics.upload.R2UploadService;
import com.github.fc.diagnostics.upload.UploadResult;
import com.github.fc.diagnostics.util.SafeExecutor;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;

public final class QueueWorker implements Runnable {

    private final UploadQueueManager manager;
    private final R2UploadService uploader;

    private final Logger logger = DiagnosticsLoggers.diagnostics();

    public QueueWorker(UploadQueueManager manager, R2UploadService uploader) {

        this.manager = manager;
        this.uploader = uploader;
    }

    @Override
    public void run() {

        SafeExecutor.run(() -> {

            for (QueueEntry entry : manager.store().loadPending()) {
                if (!Files.exists(Path.of(entry.filePath()))) {
                    continue;
                }
                process(entry);
            }
        });
    }

    private void process(QueueEntry entry) {

        logger.info("Processing {}", entry.filePath());

        if (!manager.retryPolicy().isReady(entry)) {
            logger.info("Not ready yet");
            return;
        }

        manager.store().moveToProcessing(entry);

        UploadResult result = uploader.upload(entry.filePath());

        if (result.success()) {
            logger.info("Upload succeeded: {}", result.key());
            manager.store().delete(entry.id());
            return;
        }

        logger.warn("Upload failed: {}", result.error());

            QueueEntry updated = new QueueEntry(
                    entry.id(),
                    entry.filePath(),
                    entry.attempts() + 1,
                    java.time.Instant.now()
                            .plus(manager.retryPolicy()
                                    .nextDelay(entry.attempts()))
            );

            manager.store().moveToFailed(updated);
            manager.store().savePending(updated);

    }
}
