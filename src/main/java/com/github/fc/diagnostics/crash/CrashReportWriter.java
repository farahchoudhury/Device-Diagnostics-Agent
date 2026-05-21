package com.github.fc.diagnostics.crash;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fc.diagnostics.logging.DiagnosticsLoggers;

import java.nio.file.Files;
import java.nio.file.Path;

public final class CrashReportWriter {

    private final ObjectMapper mapper =
            new ObjectMapper().findAndRegisterModules();

    public void write(Path crashDir, CrashReport report) {

        try {

            Files.createDirectories(crashDir);

            String fileName =
                    "crash-" + report.sessionId()
                            + "-" + System.currentTimeMillis()
                            + ".json";

            Path tempFile = crashDir.resolve(fileName + ".tmp");
            Path finalFile = crashDir.resolve(fileName);

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(tempFile.toFile(), report);

            Files.move(
                    tempFile,
                    finalFile,
                    java.nio.file.StandardCopyOption.ATOMIC_MOVE,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

        } catch (Exception e) {
            DiagnosticsLoggers.crashes().error("Failed to write crash report", e);
        }
    }
}
