package com.titan.settings.model;

import com.titan.settings.base.BaseModel;


public class MainModel extends BaseModel {
    private String mDisplayName;
    private int mIconResNormal;
    private int mIconResSelected;
    private SettingType mSettingType;
    private boolean isEnabled = true;

    public MainModel(String displayName, int iconResNormal, int iconResSelected,SettingType settingType,boolean isEnabled) {
        mDisplayName = displayName;
        mIconResNormal = iconResNormal;
        mIconResSelected = iconResSelected;
        mSettingType = settingType;
        this.isEnabled = isEnabled;
    }
    public MainModel(String displayName, int iconResNormal, int iconResSelected,SettingType settingType) {
        mDisplayName = displayName;
        mIconResNormal = iconResNormal;
        mIconResSelected = iconResSelected;
        mSettingType = settingType;
    }

    public String getMainName() {
        return mDisplayName;
    }

    public int getIconResNormal() {
        return mIconResNormal;
    }

    public int getIconResSelected() {
        return mIconResSelected;
    }

    public SettingType getSettingType() {
        return mSettingType;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

}
