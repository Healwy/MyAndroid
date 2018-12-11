package com.titan.platformadapter;

public class TvItemList {

    public static class TvGeneralItem {

        public static final int ITEM_LANGUAGE = 0;
        public static final int ITEM_IME = 1;
        public static final int ITEM_NO_SIGNAL_POWEROFF = 2;
        public static final int ITEM_NOTIFY_MANAGEMENT = 3;
        public static final int ITEM_SCREENSAVER = 4;
        private static final int ITEM_SCREENSAVER_STYLE = 5;
        public static int[] generalCategoryList = {ITEM_LANGUAGE, ITEM_IME,};
        public static int[] saveEnergyCategoryList = {ITEM_SCREENSAVER, ITEM_SCREENSAVER_STYLE,};
    }

    public static class TvPictureItem {
        public static final int ITEM_BACKLIGHT = 0;
        public static final int ITEM_PICTURE_PARAMETER = 1;
        public static int[] pictureCategoryList = {ITEM_BACKLIGHT, ITEM_PICTURE_PARAMETER,};
    }

    public static class TvSoundItem {
        public static final int ITEM_SYSTEM_SOUND = 0;
        public static final int ITEM_SOUND_PARAM = 1;
        public static int[] pictureCategoryList = {ITEM_SYSTEM_SOUND, ITEM_SOUND_PARAM,};
    }

    public static class TvSecurityItem {
        public static final int ITEM_INSTALL_UNKNOW_SOURCE = 0;
        public static final int ITEM_ADB_DEBUG = 1;
        public static int[] securityCategoryList = {ITEM_INSTALL_UNKNOW_SOURCE, ITEM_ADB_DEBUG,};
    }

    public static class TvSystemItem {
        public static final int ITEM_SYSTEM_INFORMATION = 0;
        public static final int ITEM_SOURCE_LICENCE = 1;
        public static final int ITEM_USER_AGREEMENT = 2;
        public static final int ITEM_LOCALE_UPDATE = 3;
        public static final int ITEM_NETWORK_UPDATE = 4;
        public static final int ITEM_SYSTEM_RESTORE = 5;
        public static int[] systemCategoryList = {ITEM_SYSTEM_INFORMATION, ITEM_SOURCE_LICENCE, ITEM_LOCALE_UPDATE, ITEM_NETWORK_UPDATE, ITEM_SYSTEM_RESTORE};
    }

    public static class TvNetworkItem {
        public static final int ITEM_WIRED_CONNECT = 0;
        public static final int ITEM_WIRRELESS_CONNECT = 1;
        public static final int ITEM_BLUETOOTH = 2;
        public static final int ITEM_WIRELESS_HOTSPOT = 3;
        public static final int ITEM_PPPOE = 4;
        public static final int ITEM_SIM_4G = 5;
        public static int[] networkCategoryList = {ITEM_WIRED_CONNECT, ITEM_WIRRELESS_CONNECT, ITEM_WIRELESS_HOTSPOT, ITEM_SIM_4G};
    }
}
