package me.shodiq.jatijepara.log;

import android.os.Environment;
import android.util.Log;

import java.io.File;

import me.shodiq.jatijepara.LogStorage;

/**
 * Created by amri.shodiq on 4/18/2016.
 */
public class AndroidLogStorage extends LogStorage {

    private static AndroidLogStorage storage;
    public static AndroidLogStorage getStorage() {
        if (storage == null) {
            storage = new AndroidLogStorage();
        }
        return storage;
    }

    private static final String LOG_DIR = "Logs";

    private AndroidLogStorage() {
    }

    @Override
    public boolean isAbleToWrite() {
        return true;
    }

    @Override
    public File getLogDirectory() {
        File file = null;

        if ( 0 == Environment.getExternalStorageState().compareTo(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+LOG_DIR);
        } else {
            new File(Environment.getDataDirectory().getAbsolutePath()+"/"+LOG_DIR);
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    @Override
    public String getAppName() {
        return "JatiJepara";
    }

    @Override
    public void logVerbose(String tag, String message) {
        Log.v(tag, message);
    }

    @Override
    public void logDebug(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void logInfo(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    public void logWarn(String tag, String message) {
        Log.w(tag, message);
    }

    @Override
    public void logError(String tag, String message) {
        Log.e(tag, message);
    }

}
