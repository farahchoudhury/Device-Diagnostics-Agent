package com.github.fc.diagnostics.logging;

import java.nio.file.Path;

public final class LogDirectoryResolver {

    private LogDirectoryResolver() {
    }

    public static Path resolve(Path appRoot) {

        return appRoot.resolve("logs");
    }
}
