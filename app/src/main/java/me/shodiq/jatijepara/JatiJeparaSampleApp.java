package me.shodiq.jatijepara;

import android.app.Application;

import me.shodiq.jatijepara.log.ActivityMonitor;
import me.shodiq.jatijepara.log.AndroidLogStorage;
import me.shodiq.jatijepara.log.LogProxy;
import timber.log.Timber;

/**
 * Created by amri.shodiq on 4/18/2016.
 */
public class JatiJeparaSampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initLogger();

        Timber.i("Application started.");

        registerActivityLifecycleCallbacks(new ActivityMonitor());
    }

    private void initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new LogProxy(AndroidLogStorage.getStorage()));
        }

        // Put this line to report unhandled exception and reduce force close.
        LogProxy.overrideDefaultExceptionHandler();
    }

}
