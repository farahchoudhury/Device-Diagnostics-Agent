package com.github.fc.diagnostics.util;

import com.github.fc.diagnostics.logging.DiagnosticsLoggers;
import org.slf4j.Logger;

public final class SafeExecutor {

    private static final Logger logger =
            DiagnosticsLoggers.diagnostics();

    private SafeExecutor() {
    }

    public static void run(
            Runnable runnable) {

        try {

            runnable.run();

        } catch (Exception ex) {

            logger.error(
                    "Non-fatal diagnostics failure",
                    ex);
        }
    }
}
