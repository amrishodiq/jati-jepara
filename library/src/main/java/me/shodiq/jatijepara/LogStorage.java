package me.shodiq.jatijepara;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Abstract class be inherited by a device based log storage, for example AndroidLogStorage.
 * Created by amri.shodiq on 4/6/2016.
 */
public abstract class LogStorage {
    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    /**
     * This method will check wether the system is able to write to this storage or not.
     * @return true if system is writable, otherwise it will return false.
     */
    public abstract boolean isAbleToWrite();

    public abstract File getLogDirectory();

    /**
     * This method will be used to create a directory inside Logs directory. So please give only
     * a valid name for directory. Alphanumeric starting with alphabet is ok.
     * @return
     */
    public abstract String getAppName();

    private File getExtendedLogDirectory() {
        File dir = getLogDirectory();
        File result = new File(dir.getAbsolutePath()+"/"+getAppName());
        if (!result.exists()) {
            result.mkdirs();
        }
        return result;
    }

    /**
     * This method give the concrete file where the log will be written.
     * @return
     */
    public File getLogFile() {
        File directory = getExtendedLogDirectory();
        if (directory == null) {
            logError(getClass().getSimpleName(), "Cannot get log directory.");
        }

        File result = new File(getLogFileName(directory));
        logDebug(getClass().getSimpleName(), "Log file: " + result.getAbsolutePath());
        return result;
    }

    private static final long MAX_FILE_SIZE = 512 * 1024;
    private static final String FILE_EXT = ".txt";

    /**
     * Return rotated log filename. Log rotation based on size, maximum size for a log file is
     * {@link LogStorage#MAX_FILE_SIZE}, and daily basis.
     * @param directory
     * @return
     */
    private String getLogFileName(File directory) {
        String today = LogStorage.formatDate(System.currentTimeMillis());

        if (directory.isDirectory()) {

            File[] files = directory.listFiles();

            // if no log for today, then returns today + "-0"
            if (files == null || files.length == 0) {
                return directory.getAbsolutePath()+"/"+today+"-0"+FILE_EXT;
            }
            int index = 0;
            for (File file: files) {
                if (file.getName().startsWith(today) && file.getName().endsWith(FILE_EXT)) {
                    // if log file found which size is less than max file size, then use that file
                    if (file.length() < MAX_FILE_SIZE) {
                        return directory.getAbsolutePath()+"/"+file.getName();
                    }
                    String s = file.getName().replace(today, "");
                    s = s.replace(FILE_EXT, "");
                    index = Integer.parseInt(s);
                }
            }
            index++;

            // return next file
            return directory.getAbsolutePath()+"/"+today+"-"+index+FILE_EXT;
        }

        // default return, most probably not called.
        return directory.getAbsolutePath()+"/"+today+"-0"+FILE_EXT;
    }

    /**
     * Simply log to console, or the like, based on the underlying sistem. On Android, it should do
     * like android.util.Log.v(tag, message).
     * @param tag
     * @param message
     */
    public abstract void logVerbose(String tag, String message);
    public abstract void logDebug(String tag, String message);
    public abstract void logInfo(String tag, String message);
    public abstract void logWarn(String tag, String message);
    public abstract void logError(String tag, String message);


    public static interface SendToServerStatusListener {
        public void onSendToServerSucceed();
        public void onSendToServerFailed();
    }

    /**
     * Prepared method to enable send log files to server.
     * @param listener
     */
    public void sendToServer(SendToServerStatusListener listener) {
        // Todo: implement the real thing
    };

    private static SimpleDateFormat formatter;
    private static Date date;
    protected static String formatDate(long time) {
        if (formatter == null) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = new Date();
        }
        date.setTime(time);
        return formatter.format(date);
    }

}
