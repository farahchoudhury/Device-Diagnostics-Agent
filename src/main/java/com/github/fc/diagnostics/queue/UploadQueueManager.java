package com.github.fc.diagnostics.queue;

import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;

public final class UploadQueueManager {

    private final QueueStore store;
    private final RetryPolicy retryPolicy = new RetryPolicy();

    public UploadQueueManager(Path root) {
        this.store = new QueueStore(root);
        this.store.init();
    }

    public void enqueue(String filePath) {

        QueueEntry entry = new QueueEntry(
                UUID.randomUUID().toString(),
                filePath,
                0,
                Instant.now()
        );

        store.savePending(entry);
    }

    public QueueStore store() {
        return store;
    }

    public RetryPolicy retryPolicy() {
        return retryPolicy;
    }
}
