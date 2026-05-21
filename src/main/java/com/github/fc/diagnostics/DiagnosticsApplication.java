package com.github.fc.diagnostics;

import com.github.fc.diagnostics.collector.DiagnosticsCollector;
import com.github.fc.diagnostics.collector.ManifestWriter;
import com.github.fc.diagnostics.collector.SessionManager;
import com.github.fc.diagnostics.config.ConfigLoader;
import com.github.fc.diagnostics.config.DiagnosticsConfig;
import com.github.fc.diagnostics.crash.CrashContextBuilder;
import com.github.fc.diagnostics.crash.CrashReportWriter;
import com.github.fc.diagnostics.crash.GlobalCrashHandler;
import com.github.fc.diagnostics.logging.DiagnosticsLoggers;
import com.github.fc.diagnostics.logging.LoggingBootstrap;
import com.github.fc.diagnostics.util.FileUtils;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Map;

public final class DiagnosticsApplication {

    private static GlobalCrashHandler crashHandler;

    public static void main(String[] args) {

        DiagnosticsConfig config =
                new ConfigLoader().load();

        initializeDirectories(config);

        LoggingBootstrap.initialize(config);

        Logger logger = org.slf4j.LoggerFactory.getLogger(DiagnosticsApplication.class);

        DiagnosticsCollector collector = new DiagnosticsCollector();

        ManifestWriter manifestWriter = new ManifestWriter();

        Map<String, Object> manifest = collector.collect(config);

        Path manifestFile = manifestWriter.write(config.appRoot().resolve("manifests"), manifest);

        logger.info("Diagnostics manifest created: {}", manifestFile);

        logger.info("Diagnostics system fully initialized");

        logger.info("Application root: {}", config.appRoot());

        logger.info("Upload interval: {}", config.uploadInterval());

        CrashReportWriter crashWriter = new CrashReportWriter();

        SessionManager session = new SessionManager();

        CrashContextBuilder context = new CrashContextBuilder(session);

        crashHandler = new GlobalCrashHandler(
                crashWriter,
                context,
                config.appRoot().resolve("crashes")
        );

        Thread.setDefaultUncaughtExceptionHandler(crashHandler);

        simulateCrash();
        simulateLogging();
    }

    private static void initializeDirectories(
            DiagnosticsConfig config) {

        FileUtils.ensureDirectory(config.appRoot());

        FileUtils.ensureDirectory(
                config.appRoot().resolve("logs"));

        FileUtils.ensureDirectory(
                config.appRoot().resolve("queue"));

        FileUtils.ensureDirectory(
                config.appRoot().resolve("crashes"));

        FileUtils.ensureDirectory(
                config.appRoot().resolve("exports"));
    }

    private static void simulateLogging() {

        Logger appLogger =
                org.slf4j.LoggerFactory.getLogger(
                        DiagnosticsApplication.class);

        Logger diagnosticsLogger =
                DiagnosticsLoggers.diagnostics();

        appLogger.info("Application logger online");

        diagnosticsLogger.info("Diagnostics logger online");

        DiagnosticsLoggers.crashes()
                .error("Crash logger test entry");
    }

    private static void simulateCrash() {

        throw new RuntimeException("Test crash system");
    }
}