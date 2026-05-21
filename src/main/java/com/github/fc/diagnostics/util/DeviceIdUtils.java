package com.github.fc.diagnostics.util;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

public final class DeviceIdUtils {

    private DeviceIdUtils() {}

    public static String getDeviceId() {

        try {

            String host = InetAddress
                    .getLocalHost()
                    .getHostName();

            String base = host + System.getProperty("user.name");

            return sha256(base).substring(0, 16);

        } catch (Exception e) {

            return UUID.randomUUID().toString()
                    .substring(0, 16);
        }
    }

    private static String sha256(String input)
            throws Exception {

        MessageDigest digest =
                MessageDigest.getInstance("SHA-256");

        byte[] hash =
                digest.digest(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();

        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
