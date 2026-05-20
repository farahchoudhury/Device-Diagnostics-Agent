package com.github.fc.diagnostics.config;

import java.nio.file.Path;
import java.time.Duration;

public record DiagnosticsConfig(
        Path appRoot,
        Duration uploadInterval,
        int maxQueueSize
) {
}
