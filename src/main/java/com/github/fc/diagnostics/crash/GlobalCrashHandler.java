package com.github.fc.diagnostics.crash;

import com.github.fc.diagnostics.util.DeviceIdUtils;
import com.github.fc.diagnostics.util.SafeExecutor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;

public final class GlobalCrashHandler
        implements Thread.UncaughtExceptionHandler {

    private final CrashReportWriter writer;
    private final CrashContextBuilder context;
    private final java.nio.file.Path crashDir;

    public GlobalCrashHandler(
            CrashReportWriter writer,
            CrashContextBuilder context,
            java.nio.file.Path crashDir) {

        this.writer = writer;
        this.context = context;
        this.crashDir = crashDir;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        SafeExecutor.run(() -> {

            CrashReport report = new CrashReport(
                    context.sessionId(),
                    DeviceIdUtils.getDeviceId(),
                    Instant.now(),
                    t.getName(),
                    e.getMessage(),
                    e.getClass().getName(),
                    stackTrace(e),
                    context.system(),
                    context.runtime()
            );

            writer.write(crashDir, report);
        });
    }

    private String stackTrace(Throwable e) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
