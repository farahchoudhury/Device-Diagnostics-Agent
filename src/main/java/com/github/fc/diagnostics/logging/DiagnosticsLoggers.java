package com.github.fc.diagnostics.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DiagnosticsLoggers {

    private DiagnosticsLoggers() {
    }

    public static Logger diagnostics() {

        return LoggerFactory.getLogger("diagnostics");
    }

    public static Logger crashes() {

        return LoggerFactory.getLogger("crash");
    }
}
