package it.jaschke.alexandria;

import android.app.Application;
import android.content.Context;

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
        }
    }
}