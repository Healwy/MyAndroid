package com.titan.titv.settings.picture;

import android.content.ContentResolver;
import android.content.Context;

import com.titan.platformadapter.TvPictureManagerAdapter;

import static com.titan.platformadapter.TvPictureManagerAdapter.DEFAULT_BRIGHTNESS;
import static com.titan.platformadapter.TvPictureManagerAdapter.DEFAULT_COLORTEMPERATURE;
import static com.titan.platformadapter.TvPictureManagerAdapter.DEFAULT_CONTRAST;
import static com.titan.platformadapter.TvPictureManagerAdapter.DEFAULT_HUE;
import static com.titan.platformadapter.TvPictureManagerAdapter.DEFAULT_PICTUREMODE;
import static com.titan.platformadapter.TvPictureManagerAdapter.DEFAULT_SATURATION;
import static com.titan.platformadapter.TvPictureManagerAdapter.DEFAULT_SHARPNESS;

public class PicturePresenter implements IPictureContract.Presenter {

    private final Context mContext;
    private final ContentResolver mResolver;
    private final TvPictureManagerAdapter mTvPictureManagerAdapter;

    PicturePresenter(Context context) {
        mContext = context;
        mResolver = context.getContentResolver();
        mTvPictureManagerAdapter = new TvPictureManagerAdapter(context);
    }


    @Override
    public void setMode(String value) {
        mTvPictureManagerAdapter.setPictureMode(Integer.parseInt(value));
    }

    @Override
    public int getMode() {
        int value = mTvPictureManagerAdapter.getPictureMode();
        return value;
    }

    @Override
    public void setBrightness(int value) {
        mTvPictureManagerAdapter.setBrightness(value);
    }

    @Override
    public int getBrightness() {
        int value = mTvPictureManagerAdapter.getBrightness();
        return value;
    }

    @Override
    public void setContrast(int value) {
        mTvPictureManagerAdapter.setContrast(value);
    }

    @Override
    public int getContrast() {
        int value = mTvPictureManagerAdapter.getContrast();
        return value;
    }


    @Override
    public void setSaturation(int value) {
        mTvPictureManagerAdapter.setSaturation(value);
    }

    @Override
    public int getSaturation() {
        int value = mTvPictureManagerAdapter.getSaturation();
        return value;
    }

    @Override
    public void setHue(int value) {
        mTvPictureManagerAdapter.setHue(value);
    }

    @Override
    public int getHue() {
        int value = mTvPictureManagerAdapter.getHue();
        return value;
    }

    @Override
    public void setSharpness(int value) {
        mTvPictureManagerAdapter.setSharpness(value);
    }

    @Override
    public int getSharpness() {
        int value = mTvPictureManagerAdapter.getSharpness();
        return value;
    }

    @Override
    public void setBacklight(int value) {
        mTvPictureManagerAdapter.setBacklight(value);
    }

    @Override
    public int getBacklight() {
        int value = mTvPictureManagerAdapter.getBacklight();
        return value;
    }

    @Override
    public void setColorTemperature(String value) {
        mTvPictureManagerAdapter.setColorTemperature(Integer.parseInt(value));
    }

    @Override
    public int getColorTemperature() {
        int value = mTvPictureManagerAdapter.getColorTemperature();
        return value;
    }

    @Override
    public void reset() {
        setMode(DEFAULT_PICTUREMODE + "");
        setBrightness(DEFAULT_BRIGHTNESS);
        setContrast(DEFAULT_CONTRAST);
        setSaturation(DEFAULT_SATURATION);
        setHue(DEFAULT_HUE);
        setSharpness(DEFAULT_SHARPNESS);
        setColorTemperature(DEFAULT_COLORTEMPERATURE + "");
    }
}
