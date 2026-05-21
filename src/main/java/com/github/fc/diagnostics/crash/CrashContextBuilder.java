package com.github.fc.diagnostics.crash;

import com.github.fc.diagnostics.collector.RuntimeInfoCollector;
import com.github.fc.diagnostics.collector.SessionManager;
import com.github.fc.diagnostics.collector.SystemInfoCollector;

import java.util.Map;

public final class CrashContextBuilder {

    private final SystemInfoCollector system =
            new SystemInfoCollector();

    private final RuntimeInfoCollector runtime =
            new RuntimeInfoCollector();

    private final SessionManager session;

    public CrashContextBuilder(SessionManager session) {
        this.session = session;
    }

    public Map<String, Object> system() {
        return system.collect();
    }

    public Map<String, Object> runtime() {
        return runtime.collect();
    }

    public String sessionId() {
        return session.getSessionId();
    }
}
