package me.shodiq.jatijepara;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * LogReporter basically is a class to write down the log into provided storage.
 * Created by amri.shodiq on 4/5/2016.
 */
public class LogReporter {
    private static LogStorage storage;
    /**
     * This method should be called first, before call to Timber's log methods.
     * @param storage
     */
    public static void setStorage(LogStorage storage) {
        LogReporter.storage = storage;
    }

    private static LogReporter instance;
    private static LogReporter getInstance() {
        if (storage == null) {
            throw new RuntimeException("No LogStorage set. Call LogReporter.setStorage() first.");
        }
        if (instance == null) {
            instance = new LogReporter();
        }
        return instance;
    }

    public static void log(int priority, String tag, String message) {
        getInstance().enqueue(new LogRecord(priority, tag, message));
    }

    public static void logWarning(String tag, Throwable t) {
        getInstance().enqueue(new LogRecord(LogStorage.WARN, tag, t.getMessage()));
    }

    public static void logError(String tag, Throwable t) {
        getInstance().enqueue(new LogRecord(LogStorage.ERROR, tag, fromThrowable(t)));
    }

    public static void flush() {
        getInstance().writeToFile();
    }

    private static String fromThrowable(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();

        String result = sw.toString();
        try {
            pw.close();
            sw.close();
        } catch (IOException e) {
        }
        pw = null;
        sw = null;
        return result;
    }

    private LogReporter() {
    }

    private ArrayList<LogRecord> records;
    private long lastTimeFlush = 0;
    private static final int MAX_ITEM_IN_QUEUE = 10;
    private static final long WRITE_TO_SERVER_PERIOD = 2 * 60 * 1000;

    private long lastTimeSync = 0;
    private static final long SEND_TO_SERVER_PERIOD = 60 * 60 * 1000;

    private void enqueue(LogRecord logRecord) {
        if (records == null) {
            records = new ArrayList<>();
        }
        records.add(logRecord);

        // flush to file every 10 items added or two minutes after last time flush
        // in order not too ofter writing to file
        if (records.size() >= MAX_ITEM_IN_QUEUE || (System.currentTimeMillis() - lastTimeFlush) >= WRITE_TO_SERVER_PERIOD) {
            writeToFile();
        }
    }

    private void writeToFile() {
        if (storage.isAbleToWrite()) {
            File file = storage.getLogFile();
            storage.logDebug(LogReporter.class.getSimpleName(), "Write to log file "+file.getAbsolutePath());
            try {
                FileWriter bw = new FileWriter(file, true);

                for (LogRecord log:records) {
                    storage.logInfo(LogReporter.class.getSimpleName(), log.toString());
                    bw.write(log.toString());
                    bw.write("\r\n");
                }

                bw.flush();

                onWriteToFileSucceed();

                bw.close();
            } catch (FileNotFoundException e) {
                storage.logError(LogReporter.class.getSimpleName(), e.getMessage());
            } catch (IOException e) {
                storage.logError(LogReporter.class.getSimpleName(), e.getMessage());
            }
        } else {
            storage.logError(LogReporter.class.getSimpleName(), "Cannot write to external storage.");
            onWriteToFileFailed();
        }
    }

    private void onWriteToFileSucceed() {
        // On success empty the records and reset lastTimeFlush
        records.clear();
        lastTimeFlush = System.currentTimeMillis();

        if ((System.currentTimeMillis() - lastTimeSync) > SEND_TO_SERVER_PERIOD &&
                sendToServerStatusListener != null) {
            storage.sendToServer(sendToServerStatusListener);
        }
    }

    private void onWriteToFileFailed() {
        // TODO: change this behavior
        records.clear();
        lastTimeFlush = System.currentTimeMillis();
    }

    private LogStorage.SendToServerStatusListener sendToServerStatusListener;
    public void setSendToServerStatusListener(LogStorage.SendToServerStatusListener listener) {
        this.sendToServerStatusListener = listener;
    }
}
