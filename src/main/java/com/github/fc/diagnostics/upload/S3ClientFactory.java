package com.github.fc.diagnostics.upload;

import com.github.fc.diagnostics.logging.DiagnosticsLoggers;
import org.slf4j.Logger;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.util.Optional;

public final class S3ClientFactory {

    private final Logger logger = DiagnosticsLoggers.diagnostics();

    public Optional<S3Client> create() {

        String accessKey = System.getenv("R2_ACCESS_KEY");
        String secretKey = System.getenv("R2_SECRET_KEY");
        String endpoint = System.getenv("R2_ENDPOINT");

        if (isBlank(accessKey) ||
                isBlank(secretKey) ||
                isBlank(endpoint)) {

            return Optional.empty();
        }

        try {

            S3Client client = S3Client.builder()
                    .endpointOverride(URI.create(endpoint))
                    .credentialsProvider(
                            StaticCredentialsProvider.create(
                                    AwsBasicCredentials.create(
                                            accessKey,
                                            secretKey
                                    )
                            )
                    )
                    .region(Region.US_EAST_1) // required by SDK
                    .build();

            return Optional.of(client);

        } catch (Exception e) {
            logger.warn("Failed to create S3 client", e);
            return Optional.empty();
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
