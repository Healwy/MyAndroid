package com.titan.settings.base;

import com.titan.settings.model.SettingType;

public interface OnSettingItemClickListener {
    void onSettingItemClick(SettingType settingType, String value, int pos);
}
