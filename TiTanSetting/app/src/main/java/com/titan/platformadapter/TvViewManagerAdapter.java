package com.titan.platformadapter;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import java.util.List;

public class TvViewManagerAdapter {
    private Context mContext;
    private static final String DEFAULT_LICENSE_PATH = "/system/etc/NOTICE.html.gz";
    private static final String PROPERTY_LICENSE_PATH = "ro.config.license_path";
    private static final String SETTINGS_LOCAL_UPDATE_SYSTEM = "android.settings.SYSTEM_LOCAL_UPDATE_SETTINGS";

    public TvViewManagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void startLegalInformationView() {

    }

    public void startSubscriberAgreementView() {

    }

    public void systemLocalUpdate() {
        try {
            Intent intent = systemIntent(SETTINGS_LOCAL_UPDATE_SYSTEM);
            if (intent != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Intent systemIntent(String action) {
        final Intent intent = new Intent(action);

        // Limit the intent to an activity that is in the system image.
        final PackageManager pm = mContext.getPackageManager();
        final List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo info : activities) {
            if ((info.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                if (info.activityInfo.isEnabled()) {
                    intent.setPackage(info.activityInfo.packageName);
                    return intent;
                }
            }
        }
        return null;  // No system image package found.
    }


    public void systemNetworkUpdate() {
        try {
            Intent sendIntent = new Intent(Intent.ACTION_MAIN);
            ComponentName componentName = new ComponentName("com.titan.tvupgrade",
                    "com.titan.tvupgrade.UpgradeActivity");
            sendIntent.setComponent(componentName);
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
