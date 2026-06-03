package com.github.fc.diagnostics.queue;

import java.time.Duration;
import java.time.Instant;

public final class RetryPolicy {

    private final Duration baseDelay = Duration.ofSeconds(5);
    private final int maxAttempts = 10;

    public boolean canRetry(QueueEntry entry) {
        return entry.attempts() < maxAttempts;
    }

    public boolean isReady(QueueEntry entry) {
        return Instant.now().isAfter(entry.nextRetry());
    }

    public Duration nextDelay(int attempts) {
        long multiplier = Math.min(attempts, 10);
        long seconds = baseDelay.getSeconds() * (1L << multiplier);
        return Duration.ofSeconds(Math.min(seconds, 3600)); // cap 1h
    }
}
