package com.github.fc.diagnostics.collector;

import com.github.fc.diagnostics.logging.DiagnosticsLoggers;
import org.slf4j.Logger;

public final class StartupDiagnosticsCollector {

    private static final Logger logger =
            DiagnosticsLoggers.diagnostics();

    public void collect() {

        logger.info("Java version: {}",
                System.getProperty("java.version"));

        logger.info("OS name: {}",
                System.getProperty("os.name"));

        logger.info("OS architecture: {}",
                System.getProperty("os.arch"));

        logger.info("User home: {}",
                System.getProperty("user.home"));

        logger.info("Available processors: {}",
                Runtime.getRuntime()
                        .availableProcessors());

        logger.info("Max memory MB: {}",
                Runtime.getRuntime()
                        .maxMemory() / 1024 / 1024);
    }
}
