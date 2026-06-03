package com.github.fc.diagnostics.queue;

import com.github.fc.diagnostics.logging.DiagnosticsLoggers;
import com.github.fc.diagnostics.upload.R2UploadService;
import com.github.fc.diagnostics.util.SafeExecutor;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
                if (!Files.exists(Path.of(entry.filePath()))) return;
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

        boolean success = uploader.upload(entry.filePath());

        logger.info("Upload result = {}", success);

        if (success) {
            manager.store().delete(entry.id());
        } else {

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
}
