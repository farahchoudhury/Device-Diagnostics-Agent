package com.github.fc.diagnostics.collector;

import java.time.Instant;
import java.util.UUID;

public final class SessionManager {

    private final String sessionId =
            UUID.randomUUID().toString();

    private final Instant startTime =
            Instant.now();

    public String getSessionId() {
        return sessionId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public long uptimeSeconds() {
        return java.time.Duration
                .between(startTime, Instant.now())
                .getSeconds();
    }
}
