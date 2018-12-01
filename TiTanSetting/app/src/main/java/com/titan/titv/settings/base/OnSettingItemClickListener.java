package com.titan.titv.settings.base;

import com.titan.titv.settings.model.SettingType;

public interface OnSettingItemClickListener {
    void onSettingItemClick(SettingType settingType, String value, int pos);
}
