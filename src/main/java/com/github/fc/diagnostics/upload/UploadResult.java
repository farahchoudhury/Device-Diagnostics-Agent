package com.github.fc.diagnostics.upload;

public record UploadResult(
        boolean success,
        String key,
        String error
) {
    public static UploadResult ok(String key) {
        return new UploadResult(true, key, null);
    }

    public static UploadResult fail(String error) {
        return new UploadResult(false, null, error);
    }
}
