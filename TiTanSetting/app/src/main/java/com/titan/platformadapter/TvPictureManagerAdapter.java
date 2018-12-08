package com.titan.platformadapter;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;


public class TvPictureManagerAdapter {
    private static final String TAG = "TvPictureManagerAdapter";
    private Context mConetxt = null;

    /**
     * picture mode user
     */
    public static final int PICTURE_MODE_USER = 0;
    /**
     * picture mode normal
     */
    public static final int PICTURE_MODE_NORMAL = 1;
    /**
     * picture mode vivid
     */
    public static final int PICTURE_MODE_VIVID_ = 2;
    /**
     * picture mode cinma
     */
    public static final int PICTURE_MODE_CINMA = 3;


    /* This value is mapping to EN_MS_COLOR_TEMP */
    /**
     * color temperature cool
     */
    public static final int COLOR_TEMP_COOL = 0;
    /**
     * color temperature standard
     */
    public static final int COLOR_TEMP_NATURE = 1;
    /**
     * color temperature warm
     */
    public static final int COLOR_TEMP_WARM = 2;


    public static final int DEFAULT_PICTUREMODE = PICTURE_MODE_USER;
    public static final int DEFAULT_BRIGHTNESS = 50;
    public static final int DEFAULT_CONTRAST = 50;
    public static final int DEFAULT_SATURATION = 50;
    public static final int DEFAULT_SHARPNESS = 50;
    public static final int DEFAULT_HUE = 0;
    public static final int DEFAULT_BACKLIGHT = 50;
    public static final int DEFAULT_COLORTEMPERATURE = COLOR_TEMP_NATURE;


    private static int PictureMode;
    private static int Brightness;
    private static int Contrast;
    private static int Saturation;
    private static int Sharpness;
    private static int Hue;
    private static int Backlight;
    private static int ColorTemperature;


    public TvPictureManagerAdapter(Context mConetxt) {
        this.mConetxt = mConetxt;
    }

    public boolean setPictureMode(int value) {
        PictureMode = value;
        return true;
    }


    public int getPictureMode() {
        return PictureMode;
    }

    public boolean setBrightness(int value) {
        Brightness = value;
        return true;
    }

    public int getBrightness() {
        return Brightness;
    }

    public boolean setContrast(int value) {
        Contrast = value;
        return true;
    }


    public int getContrast() {
        return Contrast;
    }

    public boolean setSaturation(int value) {
        Saturation = value;
        return true;
    }

    public int getSaturation() {
        return Saturation;
    }


    public boolean setSharpness(int value) {
        Sharpness = value;
        return true;
    }

    public int getSharpness() {
        return Sharpness;
    }


    public boolean setHue(int value) {
        Hue = value;
        return true;
    }


    public int getHue() {
        return Hue;
    }

    public boolean setBacklight(int value) {
        Backlight = value;
        return true;
    }


    public int getBacklight() {
        return Backlight;
    }

    public boolean setColorTemperature(int mMode) {
        ColorTemperature = mMode;
        return true;
    }

    public int getColorTemperature() {
        return ColorTemperature;
    }

}
