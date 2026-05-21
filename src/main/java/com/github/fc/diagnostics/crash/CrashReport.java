package com.github.fc.diagnostics.crash;

import java.time.Instant;
import java.util.Map;

public record CrashReport(
        String sessionId,
        String deviceId,
        Instant timestamp,
        String thread,
        String message,
        String exceptionType,
        String stackTrace,
        Map<String, Object> systemInfo,
        Map<String, Object> runtimeInfo
) {}
