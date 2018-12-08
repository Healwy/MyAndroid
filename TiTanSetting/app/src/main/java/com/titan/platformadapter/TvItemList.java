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

    public static class TvSecurityItem {
        public static final int ITEM_INSTALL_UNKNOW_SOURCE = 0;
        public static final int ITEM_ADB_DEBUG = 1;
        public static int[] securityCategoryList = {ITEM_INSTALL_UNKNOW_SOURCE, ITEM_ADB_DEBUG,};
    }
}
