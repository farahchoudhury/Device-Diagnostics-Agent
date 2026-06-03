package com.github.fc.diagnostics.queue;

import java.time.Instant;

public record QueueEntry(
        String id,
        String filePath,
        int attempts,
        Instant nextRetry
) {}
