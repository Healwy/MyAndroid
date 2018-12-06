package com.titan.titv.settings.picture;

import android.content.ContentResolver;
import android.content.Context;

import com.titan.platformadapter.TvPictureManagerAdapter;

public class PicturePresenter implements IPictureContract.Presenter {

    private final Context mContext;
    private final ContentResolver mResolver;
    private final TvPictureManagerAdapter mTvAudioManagerAdapter;

    PicturePresenter(Context context) {
        mContext = context;
        mResolver = context.getContentResolver();
        mTvAudioManagerAdapter = new TvPictureManagerAdapter(context);
    }


    @Override
    public void setMode(String value) {
        mTvAudioManagerAdapter.setPictureMode(Integer.parseInt(value));
    }

    @Override
    public int getMode() {
        int value = mTvAudioManagerAdapter.getPictureMode();
        return value;
    }

    @Override
    public void setBrightness(int value) {
        mTvAudioManagerAdapter.setBrightness(value);
    }

    @Override
    public int getBrightness() {
        int value = mTvAudioManagerAdapter.getBrightness();
        return value;
    }

    @Override
    public void setContrast(int value) {
        mTvAudioManagerAdapter.setContrast(value);
    }

    @Override
    public int getContrast() {
        int value = mTvAudioManagerAdapter.getContrast();
        return value;
    }


    @Override
    public void setSaturation(int value) {
        mTvAudioManagerAdapter.setSaturation(value);
    }

    @Override
    public int getSaturation() {
        int value = mTvAudioManagerAdapter.getSaturation();
        return value;
    }

    @Override
    public void setHue(int value) {
        mTvAudioManagerAdapter.setHue(value);
    }

    @Override
    public int getHue() {
        int value =  mTvAudioManagerAdapter.getHue();
        return value;
    }

    @Override
    public void setSharpness(int value) {
        mTvAudioManagerAdapter.setSharpness(value);
    }

    @Override
    public int getSharpness() {
        int value = mTvAudioManagerAdapter.getSharpness();
        return value;
    }

    @Override
    public void setBacklight(int value) {
        mTvAudioManagerAdapter.setBacklight(value);
    }

    @Override
    public int getBacklight() {
        int value = mTvAudioManagerAdapter.getBacklight();
        return value;
    }

    @Override
    public void setColorTemperature(String value) {
        mTvAudioManagerAdapter.setColorTemperature(Integer.parseInt(value));
    }

    @Override
    public int getColorTemperature() {
        int value = mTvAudioManagerAdapter.getColorTemperature();
        return value;
    }
}
