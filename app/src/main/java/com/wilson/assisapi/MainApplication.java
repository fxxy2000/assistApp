package com.wilson.assisapi;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

public class MainApplication extends Application implements Application.OnProvideAssistDataListener{

    @Override
    public void onCreate() {
        super.onCreate();

        registerOnProvideAssistDataListener(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        unregisterOnProvideAssistDataListener(this);
    }

    @Override
    public void onProvideAssistData(Activity activity, Bundle data) {
        Log.d("MainApplication", "onProvideAssistData");
    }
}
