package com.titan.settings.base;

public interface OnWifiItemClickListener {
    void onWifiItemFocusChange(int pos);

    void onWifiItemClick(int pos);

    void onWifiAdd(int pos);

    void onWifiSwith(boolean on);
}
