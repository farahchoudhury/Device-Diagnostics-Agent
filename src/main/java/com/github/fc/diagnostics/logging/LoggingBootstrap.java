package com.github.fc.diagnostics.logging;

import com.github.fc.diagnostics.config.DiagnosticsConfig;
import com.github.fc.diagnostics.util.FileUtils;

import java.nio.file.Path;

public final class LoggingBootstrap {

    private LoggingBootstrap() {
    }

    public static void initialize(
            DiagnosticsConfig config) {

        Path logDir =
                LogDirectoryResolver.resolve(
                        config.appRoot());

        FileUtils.ensureDirectory(logDir);

        FileUtils.ensureDirectory(
                logDir.resolve("archived"));

        /*
         * Makes log directory available to Log4j2
         */
        System.setProperty(
                "DIAGNOSTICS_LOG_DIR",
                logDir.toString());
    }
}
