package com.titan.titv.settings.security;

import android.content.Context;
import android.provider.Settings;

public class SecurityPresenter implements ISecurityContract.Presenter {
    private Context mContext;

    public SecurityPresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void setUnknowSource(String value) {
         Settings.Secure.putString(mContext.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, value);
    }

    @Override
    public int getUnknowSource() {
        return Settings.Secure.getInt(mContext.getContentResolver(),Settings.Secure.INSTALL_NON_MARKET_APPS, 0);
    }

    @Override
    public void setADB(int value) {
        Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.ADB_ENABLED, value);
    }

    @Override
    public int getADB() {
       return Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.ADB_ENABLED, 0);
    }
}