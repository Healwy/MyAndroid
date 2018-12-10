package com.titan.titv.settings.system.informatoin;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.titan.platformadapter.TvStrAdapter;
import com.titan.titv.settings.R;
import com.titan.titv.settings.base.BaseModel;
import com.titan.titv.settings.model.SystemInfoModel;
import com.titan.titv.settings.utils.LogUtils;
import com.titan.titv.settings.utils.MacUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class InfoPresenter implements IInfoContract.Presenter {

    private final IInfoContract.View mView;
    private final Context mContext;
    private final TvStrAdapter mTvStrAdapter;
    private List<BaseModel> mData;

    public InfoPresenter(IInfoContract.View view, Context context) {
        this.mView = view;
        this.mContext = context;
        this.mTvStrAdapter = new TvStrAdapter(context);
    }

    @Override
    public String getDisplaySize() {
        return mTvStrAdapter.getDisplaySize();
    }

    @Override
    public String getResolution() {
        return mTvStrAdapter.getResolution();
    }

    @Override
    public String getCpuInfo() {
        return mTvStrAdapter.getCpuInfo();
    }

    @Override
    public String getGpuInfo() {
        return mTvStrAdapter.getGpuInfo();
    }

    @Override
    public String getRomRamSize() {
        return getRomSize() + "/" + getRamSize();
    }

    private String getRomSize() {
        File path = Environment.getDataDirectory();
        StatFs statFs = new StatFs(path.getPath());
        long totalSize = statFs.getTotalBytes();
        return changeFileSize(totalSize);
    }

    private String getRamSize() {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        if (manager == null) {
            return "UNKNOWN";
        }
        manager.getMemoryInfo(info);
        String ramSize = changeFileSize(info.totalMem);
        return ramSize;
    }

    private String changeFileSize(long sizeByte) {
        StringBuilder stringBuffer = new StringBuilder();
        DecimalFormat format = new DecimalFormat("###.0");
        if (sizeByte >= 1024.0 * 1024.0 * 1024.0) {
            double result = sizeByte / (1024.0 * 1024.0 * 1024.0);
            stringBuffer.append(format.format(result)).append("GB");
        } else if (sizeByte >= 1024.0 * 1024.0) {
            double result = sizeByte / (1024.0 * 1024.0);
            stringBuffer.append(format.format(result)).append("MB");
        } else if (sizeByte >= 1024.0) {
            double result = sizeByte / (1024.0);
            stringBuffer.append(format.format(result)).append("KB");
        } else if (sizeByte > 0) {
            stringBuffer.append((int) sizeByte).append("B");
        } else {
            stringBuffer.append("0B");
        }
        return stringBuffer.toString();
    }

    @Override
    public String getEthernetMac() {
        String value = MacUtil.getEthernetMac();
        return value;
    }

    @Override
    public String getWirelessMac() {
        String value = MacUtil.getWirelessMac();
        return value;
    }

    @Override
    public String getSysVersion() {
        return mTvStrAdapter.getSysVersion();
    }

    @Override
    public String getSdkVersion() {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        String buildTime = "";
        if (packageManager == null) {
            return versionName;
        }
        try {
            packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            if (packageInfo != null) {
                versionName = packageInfo.versionName;
                //TODO 临时注释
                //buildTime = SystemProperties.get("ro.build.date", "Unknown");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName + " / " + buildTime;
    }

    @Override
    public void loadData() {
        List<BaseModel> mData = new ArrayList<>();
        mData.add(new SystemInfoModel(mContext.getString(R.string.system_info_resolution), getResolution()));
        mData.add(new SystemInfoModel(mContext.getString(R.string.system_info_cpu), getCpuInfo()));
        mData.add(new SystemInfoModel(mContext.getString(R.string.system_info_gpu), getGpuInfo()));
        mData.add(new SystemInfoModel(mContext.getString(R.string.system_info_rom_ram), getRomRamSize()));
        mData.add(new SystemInfoModel(mContext.getString(R.string.system_info_ethernet_mac), getEthernetMac()));
        mData.add(new SystemInfoModel(mContext.getString(R.string.system_info_wireless_mac), getWirelessMac()));
        mData.add(new SystemInfoModel(mContext.getString(R.string.system_info_system_version), getSysVersion()));
        mData.add(new SystemInfoModel(mContext.getString(R.string.system_info_sdk_version), getSdkVersion()));
        mView.onDataLoaded(mData);
    }
}
