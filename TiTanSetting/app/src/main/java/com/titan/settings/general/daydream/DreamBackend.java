package com.titan.settings.general.daydream;

//TODO 2018.12.17 临时注释
//import static android.providerider.Settings.Secure.SCREENSAVER_ENABLED;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.service.dreams.DreamService;
import android.service.dreams.IDreamManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static android.provider.Settings.System.SCREEN_OFF_TIMEOUT;

/**
 * Manages communication with the dream manager service.
 */
public class DreamBackend {

    private static final String TAG = "DreamBackend";
    private static final boolean DEBUG = false;

    private final ContentResolver mContentResolver;
    private final PackageManager mPackageManager;
    private final Resources mResources;
    //TODO 2018.12.17 临时注释
    private final IDreamManager mDreamManager;
    private final boolean mDreamsEnabledByDefault;
    private static final int FALLBACK_SCREEN_TIMEOUT_VALUE = 1800000;
    private Context mContext;
    private ComponentName mDefaultComponentName;


    public DreamBackend(Context context) {
        this.mContext = context;
        mContentResolver = context.getContentResolver();
        mPackageManager = context.getPackageManager();
        mResources = context.getResources();
        //TODO 2018.12.17 临时注释
//        mDreamManager = IDreamManager.Stub.asInterface(
//                ServiceManager.getService(DreamService.DREAM_SERVICE));
//        mDreamsEnabledByDefault = mResources.getBoolean(
//                com.android.internal.R.bool.config_dreamsEnabledByDefault);
        mDreamManager = null;
        mDreamsEnabledByDefault = false;
        //TODO 2018.12.17 临时注释
        initDream();
    }

    void initDream() {
        mDefaultComponentName = null;
        List<ResolveInfo> resolveInfos = mPackageManager.queryIntentServices(
                new Intent(DreamService.SERVICE_INTERFACE), PackageManager.GET_META_DATA);
        for (int i = 0, size = resolveInfos.size(); i< size; i++) {
            ResolveInfo resolveInfo = resolveInfos.get(i);
            if (resolveInfo.serviceInfo == null) {
                continue;
            }
            mDefaultComponentName = getDreamComponentName(resolveInfos.get(i));
            break;
        }
    }

    private ComponentName getDreamComponentName(ResolveInfo resolveInfo) {
        if (resolveInfo == null || resolveInfo.serviceInfo == null) {
            return null;
        }
        return new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
    }


    public boolean isEnabled() {
        int enableDefault = mDreamsEnabledByDefault ? 1 : 0;
        //TODO 2018.12.17 临时注释
//        return Settings.Secure.getInt(mContentResolver, SCREENSAVER_ENABLED, enableDefault) == 1;
        return true;
        //TODO 2018.12.17 临时注释
    }

    public void setEnabled(boolean value) {
        //TODO 2018.12.17 临时注释
//        Settings.Secure.putInt(mContentResolver, SCREENSAVER_ENABLED, value ? 1 : 0);
    }

    public void setActiveDream(ComponentName dream) {
        if (mDreamManager != null) {
            try {
                ComponentName[] dreams = dream == null ? null : new ComponentName[] { dream };
                mDreamManager.setDreamComponents(dreams);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to set active dream to " + dream, e);
            }
        }
    }


    public ComponentName getActiveDream() {
        if (mDreamManager != null) {
            try {
                ComponentName[] dreams = mDreamManager.getDreamComponents();
                return dreams != null && dreams.length > 0 ? dreams[0] : null;
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to get active dream", e);
            }
        }
        return null;
    }

    public void startDreaming() {
        if (mDreamManager != null) {
            try {
                mDreamManager.dream();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to dream", e);
            }
        }
    }

    public void setDefaultDream(){
        setActiveDream(mDefaultComponentName);
    }


    public void setScreenSaverTimeout(long value){
        Settings.System.putInt(mContentResolver, SCREEN_OFF_TIMEOUT, (int)value);
    }

    public long getScreenSaverTimeout(){
        long value = 0;
        value = Settings.System.getInt(mContentResolver, SCREEN_OFF_TIMEOUT, FALLBACK_SCREEN_TIMEOUT_VALUE);
        return value;
    }
}
