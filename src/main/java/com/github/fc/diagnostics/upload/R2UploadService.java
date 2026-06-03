package com.github.fc.diagnostics.upload;


import com.github.fc.diagnostics.logging.DiagnosticsLoggers;
import org.slf4j.Logger;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public final class R2UploadService {

    private final Logger logger = DiagnosticsLoggers.diagnostics();

    private final Optional<S3Client> clientOpt;
    private final String bucket;

    public R2UploadService() {

        this.clientOpt =
                new S3ClientFactory().create();

        this.bucket =
                System.getenv().getOrDefault(
                        "R2_BUCKET",
                        "diagnostics-agent"
                );

        if (clientOpt.isEmpty()) {
            logger.warn("R2 disabled (missing config or credentials)");
        }
    }

    public UploadResult upload(String filePath) {

        if (clientOpt.isEmpty()) {
            logger.warn("Upload attempted but R2 is disabled");
            return UploadResult.fail("R2 upload not configured");
        }

        try {

            Path path = Path.of(filePath);

            if (!Files.exists(path)) {
                logger.error("File does not exist: {}", path);
                return UploadResult.fail("File does not exist");
            }

            String key = buildKey(path);

            PutObjectRequest request =
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .contentType("application/octet-stream")
                            .build();

            clientOpt.get().putObject(
                    request,
                    RequestBody.fromFile(path)
            );

            logger.info("Uploaded to R2: {}", key);

            return UploadResult.ok(key);

        } catch (Exception e) {

            logger.error("R2 upload failed: {}", filePath, e);

            return UploadResult.fail("Upload failed: " + e.getMessage());
        }
    }

    private String buildKey(Path path) {

        String deviceId =
                System.getenv().getOrDefault(
                        "DEVICE_ID",
                        "unknown-device"
                );

        return deviceId + "/"
                + path.getFileName().toString();
    }
}
