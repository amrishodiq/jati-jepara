package me.shodiq.jatijepara.log;

import me.shodiq.jatijepara.LogReporter;
import me.shodiq.jatijepara.LogStorage;
import timber.log.Timber;

/**
 * Created by amri.shodiq on 4/18/2016.
 */
public class LogProxy extends Timber.DebugTree {
    public static void overrideDefaultExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Timber.e(throwable, throwable.getMessage());
                LogReporter.flush();
                System.exit(1);
            }
        });
    }

    public LogProxy(LogStorage logStorage) {
        LogReporter.setStorage(logStorage);
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority < 4) {
            return;
        }

        if (t != null) {
            if (priority == LogStorage.ERROR) {
                LogReporter.logError(tag, t);
            } else if (priority == LogStorage.WARN) {
                LogReporter.logWarning(tag, t);
            }
        } else {
            LogReporter.log(priority, tag, message);
        }
    }
}
