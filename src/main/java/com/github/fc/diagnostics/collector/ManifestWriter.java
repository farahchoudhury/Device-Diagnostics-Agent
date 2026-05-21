package com.github.fc.diagnostics.collector;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class ManifestWriter {

    private final ObjectMapper mapper =
            new ObjectMapper();

    public Path write(
            Path outputDir,
            Map<String, Object> manifest) {

        try {

            Files.createDirectories(outputDir);

            String sessionId =
                    (String) manifest.get("sessionId");

            Path file =
                    outputDir.resolve(
                            "diagnostics-" + sessionId + ".json");

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file.toFile(), manifest);

            return file;

        } catch (Exception e) {

            // NEVER crash diagnostics system
            return null;
        }
    }
}
