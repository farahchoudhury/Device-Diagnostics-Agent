package com.github.fc.diagnostics.collector;

import com.github.fc.diagnostics.config.DiagnosticsConfig;
import com.github.fc.diagnostics.util.DeviceIdUtils;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public final class DiagnosticsCollector {

    private final SystemInfoCollector systemInfoCollector =
            new SystemInfoCollector();

    private final RuntimeInfoCollector runtimeInfoCollector =
            new RuntimeInfoCollector();

    private final SessionManager sessionManager =
            new SessionManager();

    public Map<String, Object> collect(
            DiagnosticsConfig config) {

        Map<String, Object> manifest =
                new LinkedHashMap<>();

        manifest.put("timestamp",
                Instant.now().toString());

        manifest.put("sessionId",
                sessionManager.getSessionId());

        manifest.put("uptimeSeconds",
                sessionManager.uptimeSeconds());

        manifest.put("appRoot",
                config.appRoot().toString());

        manifest.put("deviceId",
                DeviceIdUtils.getDeviceId());

        manifest.put("system",
                systemInfoCollector.collect());

        manifest.put("runtime",
                runtimeInfoCollector.collect());

        return manifest;
    }
}
