package me.shodiq.jatijepara;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amri.shodiq on 4/18/2016.
 */
public final class LogRecord {
    public long time;
    public int pri; // for priority
    public String tag, msg;
    public LogRecord(int priority, String tag, String message) {
        this.time = System.currentTimeMillis();
        this.tag = tag;
        this.pri = priority;
        this.msg = message;
    }

    @Override
    public String toString() {
        return new StringBuffer().append(formatTime(time))
                .append("\t").append(getPriority())
                .append("\t").append(tag)
                .append("\t").append(msg)
                .toString();
    }

    private String getPriority() {
        switch (pri) {
            case LogStorage.DEBUG: return "DEBUG";
            case LogStorage.VERBOSE: return "VERBOSE";
            case LogStorage.INFO: return "INFO";
            case LogStorage.WARN: return "WARN";
            case LogStorage.ERROR: return "ERROR";
        }
        return "INFO";
    }

    private static SimpleDateFormat formatter;
    private static Date date;
    private static final String formatTime(long time) {
        if (formatter == null) {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = new Date();
        }
        date.setTime(time);
        return formatter.format(date);
    }
}
