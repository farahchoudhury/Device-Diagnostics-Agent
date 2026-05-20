package com.github.fc.diagnostics;

import com.github.fc.diagnostics.config.ConfigLoader;
import com.github.fc.diagnostics.config.DiagnosticsConfig;
import com.github.fc.diagnostics.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DiagnosticsApplication {

    private static final Logger logger =
            LoggerFactory.getLogger(DiagnosticsApplication.class);

    public static void main(String[] args) {

        DiagnosticsConfig config =
                new ConfigLoader().load();

        initializeDirectories(config);

        logger.info("Diagnostics agent started");
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
}