package me.shodiq.jatijepara.log;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import timber.log.Timber;

/**
 * Created by amri.shodiq on 4/18/2016.
 */
public class ActivityMonitor implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Timber.d("Activity '%s' created", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.d("Activity '%s' started", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.d("Activity '%s' resumed", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.d("Activity '%s' paused", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.d("Activity '%s' stopped", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Timber.d("Activity '%s' saving instance state", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.d("Activity '%s' destroyed", activity.getClass().getSimpleName());
        if (activity.isTaskRoot()) {
            Timber.d("Application closed normally");
        }
    }
}
