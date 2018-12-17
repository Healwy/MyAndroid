package com.titan.platform.adapter;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

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
            Intent sendIntent = new Intent(Intent.ACTION_MAIN);
            ComponentName componentName = new ComponentName("com.titan.tvlocalupdate",
                    "com.titan.tvlocalupdate.SystemLocalUpdateActivity");
            sendIntent.setComponent(componentName);
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
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
