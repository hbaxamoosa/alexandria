package it.jaschke.alexandria;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

public class AlexandriaApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return AlexandriaApplication.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AlexandriaApplication.context = getApplicationContext();

        // http://facebook.github.io/stetho/
        Stetho.initializeWithDefaults(this);

        // https://github.com/square/leakcanary
        // LeakCanary.install(this);

        //Including Jake Wharton's Timber logging library
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}