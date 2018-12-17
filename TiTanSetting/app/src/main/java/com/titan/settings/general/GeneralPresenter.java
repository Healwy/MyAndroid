package com.titan.settings.general;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;

import com.titan.settings.general.daydream.DreamBackend;

import java.util.Locale;

//TODO 2018.12.6 临时注释
//import com.android.internal.app.LocalePicker;

public class GeneralPresenter implements IGeneralContract.Presenter {

    private final Context mContext;
    private final ContentResolver mResolver;
    private final AudioManager mAudioManager;
    private DreamBackend mDreamBackend;

    public GeneralPresenter(Context context) {
        mContext = context;
        mResolver = context.getContentResolver();
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        this.mDreamBackend = new DreamBackend(mContext);
    }

    @Override
    public void setLauanges(String value) {
        if (value.equals("zh")) {
            //TODO 2018.12.6 临时注释
//            LocalePicker.updateLocale(Locale.SIMPLIFIED_CHINESE);
        } else {
            //TODO 2018.12.6 临时注释
//            LocalePicker.updateLocale(Locale.ENGLISH);
        }
    }

    @Override
    public String getLauanges() {
        Locale locale = mContext.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

    @Override
    public void setDefaultIME(String value) {
        Settings.Secure.putString(mResolver, Settings.Secure.DEFAULT_INPUT_METHOD, value);
    }

    @Override
    public String getDefaultIME() {
        String defIME = Settings.Secure.getString(mResolver, Settings.Secure.DEFAULT_INPUT_METHOD);
        return defIME;
    }

    @Override
    public void setScreenSaverTimeout(long l) {
        if (!mDreamBackend.isEnabled()) {
            mDreamBackend.setEnabled(true);
        }
        mDreamBackend.setDefaultDream();
        mDreamBackend.setScreenSaverTimeout(l);
    }

    @Override
    public long getScreenSaverTimeout() {
        return mDreamBackend.getScreenSaverTimeout();
    }
}
