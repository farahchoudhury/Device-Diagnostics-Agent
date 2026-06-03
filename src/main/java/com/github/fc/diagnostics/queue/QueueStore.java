package com.github.fc.diagnostics.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public final class QueueStore {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private final Path pendingDir;
    private final Path processingDir;
    private final Path failedDir;

    public QueueStore(Path root) {
        this.pendingDir = root.resolve("pending");
        this.processingDir = root.resolve("processing");
        this.failedDir = root.resolve("failed");
    }

    public void init() {
        try {
            Files.createDirectories(pendingDir);
            Files.createDirectories(processingDir);
            Files.createDirectories(failedDir);
        } catch (Exception ignored) {}
    }

    public void savePending(QueueEntry entry) {
        write(pendingDir, entry);
    }

    public List<QueueEntry> loadPending() {
        return readAll(pendingDir);
    }

    public void moveToProcessing(QueueEntry entry) {
        move(entry.id(), pendingDir, processingDir);
    }

    public void moveToFailed(QueueEntry entry) {
        move(entry.id(), processingDir, failedDir);
    }

    public void delete(String id) {
        try {
            Files.deleteIfExists(pendingDir.resolve(id + ".json"));
            Files.deleteIfExists(processingDir.resolve(id + ".json"));
        } catch (Exception ignored) {}
    }

    // ---------------- private ----------------

    private void write(Path dir, QueueEntry entry) {
        try {
            Path target = dir.resolve(entry.id() + ".json");
            Path tmp = dir.resolve(entry.id() + ".json.tmp");

            mapper.writeValue(tmp.toFile(), entry);

            Files.move(
                    tmp,
                    target,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void move(String id, Path from, Path to) {
        try {
            Files.move(
                    from.resolve(id + ".json"),
                    to.resolve(id + ".json"),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (Exception ignored) {}
    }

    private List<QueueEntry> readAll(Path dir) {
        List<QueueEntry> list = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {

            for (Path p : stream) {
                try {
                    list.add(mapper.readValue(p.toFile(), QueueEntry.class));
                } catch (Exception ignored) {}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
