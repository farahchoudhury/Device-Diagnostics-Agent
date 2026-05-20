package com.github.fc.diagnostics.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {

    private FileUtils() {
    }

    public static void ensureDirectory(Path path) {

        try {

            Files.createDirectories(path);

        } catch (IOException ignored) {
        }
    }
}
