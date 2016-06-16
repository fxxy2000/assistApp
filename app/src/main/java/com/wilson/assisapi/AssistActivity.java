package com.wilson.assisapi;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class AssistActivity extends AppCompatActivity{

    private static final String TAG = "AssistActivity";
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (Intent.ACTION_ASSIST.equals(intent.getAction())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                String packageName = intent.getStringExtra(Intent.EXTRA_ASSIST_PACKAGE);
                PackageManager pm = getApplicationContext().getPackageManager();
                try {
                    PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
                    String versionName = packageInfo.versionName;
                    int versionCode = packageInfo.versionCode;
                    String appName = pm.getApplicationLabel(packageInfo.applicationInfo).toString();
                    mDialog = new AlertDialog.Builder(this)
                            .setTitle(packageName)
                            .setMessage("AppName:"+appName+"\nVersionName:"+versionName+"\nVersionCode:"+versionCode)
                            .setIcon(pm.getApplicationIcon(packageName))
                            .setPositiveButton("OK", null)
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    finish();
                                }
                            })
                            .create();
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e(TAG, "", e);
                }
            } else {
                mDialog = new AlertDialog.Builder(this)
                        .setTitle("Launch AccessibilityService to show")
                        .setPositiveButton("OK", null)
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                finish();
                            }
                        })
                        .create();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mDialog != null) {
            mDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
