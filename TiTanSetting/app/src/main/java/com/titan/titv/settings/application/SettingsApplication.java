package com.titan.titv.settings.application;

import android.app.Application;
import android.content.Context;

public class SettingsApplication extends Application {
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public Context getContext(){
        return  mContext;
    }
}
