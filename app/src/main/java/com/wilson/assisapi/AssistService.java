package com.wilson.assisapi;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

public class AssistService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public AssistService() {
        super(AssistService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (Intent.ACTION_ASSIST.equals(intent.getAction())) {
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            Log.d("topActivity", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName());
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            String packageName = componentInfo.getPackageName();
            PackageManager pm  = getPackageManager();
            try {
                ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
                String label = info.loadLabel(pm).toString();
                startActivity(NMSAppAdvisor.getAppInsightIntent(packageName, label));
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
    }
}
