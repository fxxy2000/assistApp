package com.wilson.assisapi;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.text.MessageFormat;

public class NMSAppAdvisor extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (Intent.ACTION_ASSIST.equals(intent.getAction())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                String packageName = intent.getStringExtra(Intent.EXTRA_ASSIST_PACKAGE);
                PackageManager pm = getPackageManager();
                try {
                    ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
                    String appName = info.loadLabel(pm).toString();
                    startActivity(getAppInsightIntent(appName, packageName));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                intent.setClass(this, AssistService.class);
                startService(intent);
            }
        }
        finish();
    }

    private static final String APP_INFO_SHARED_SUBJECT = "Check out \"{0}\"";
    private static final String APP_INFO_SHARED_TEXT = "https://play.google.com/store/apps/details?id={0}";
    private static final String APP_INSIGHT_PACKAGE_NAME = "com.symantec.mobilesecurity";
    private static final String APP_INSIGHT_CLASS_NAME = "com.symantec.feature.appadvisor.GooglePlayDetailActivity";
    public static Intent getAppInsightIntent(String appName, String packageName) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(APP_INSIGHT_PACKAGE_NAME, APP_INSIGHT_CLASS_NAME));
        intent.putExtra(Intent.EXTRA_SUBJECT, MessageFormat.format(APP_INFO_SHARED_SUBJECT, appName));
        intent.putExtra(Intent.EXTRA_TEXT, MessageFormat.format(APP_INFO_SHARED_TEXT, packageName));

        return intent;
    }
}
