package com.github.fc.diagnostics.queue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class QueueScheduler {

    private final ScheduledExecutorService executor =
            Executors.newSingleThreadScheduledExecutor();

    public void start(QueueWorker worker) {

        executor.scheduleAtFixedRate(
                worker,
                5,
                10,
                TimeUnit.SECONDS
        );
    }

    public void stop() {
        executor.shutdown();
    }
}
