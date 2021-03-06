package com.wilson.assisapi;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

public class MainApplication extends Application{

    private BroadcastReceiver mHomeReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        mHomeReceiver = new HomeButtonReceiver();
        registerReceiver(mHomeReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }
}
