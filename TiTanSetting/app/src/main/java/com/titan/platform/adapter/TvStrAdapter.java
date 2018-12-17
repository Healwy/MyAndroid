package com.titan.platform.adapter;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.titan.settings.R;

import java.io.File;
import java.text.DecimalFormat;

public class TvStrAdapter {
    Context mContext;

    public TvStrAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public String getDisplaySize() {
        String value = mContext.getString(R.string.system_display_size);
        return value;
    }

    public String getResolution() {
        String value = mContext.getString(R.string.system_resolution);
        return value;
    }

    public String getCpuInfo() {
        String value = mContext.getString(R.string.system_cpu);
        return value;
    }

    public String getGpuInfo() {
        String value = mContext.getString(R.string.system_gpu);
        return value;
    }

    public String getSysVersion() {
        String value = mContext.getString(R.string.system_android, Build.VERSION.RELEASE);
        return value;
    }
}
