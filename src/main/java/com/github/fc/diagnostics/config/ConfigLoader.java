package com.github.fc.diagnostics.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Properties;

public final class ConfigLoader {

    public DiagnosticsConfig load() {

        Properties properties = new Properties();

        try (InputStream input =
                     getClass().getClassLoader()
                             .getResourceAsStream("diagnostics.properties")) {

            if (input != null) {
                properties.load(input);
            }

        } catch (IOException ignored) {
        }

        Path appRoot = Path.of(
                System.getProperty("user.home"),
                "device-agent"
        );

        Duration uploadInterval =
                Duration.ofSeconds(300);

        int maxQueueSize = 1000;

        return new DiagnosticsConfig(
                appRoot,
                uploadInterval,
                maxQueueSize
        );
    }
}
