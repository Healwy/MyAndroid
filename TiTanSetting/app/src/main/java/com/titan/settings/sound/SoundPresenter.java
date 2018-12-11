package com.titan.settings.sound;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;

import com.titan.platformadapter.TvAudioManagerAdapter;

import static com.titan.platformadapter.TvAudioManagerAdapter.DEFAULT_BALANCE;
import static com.titan.platformadapter.TvAudioManagerAdapter.DEFAULT_BASS;
import static com.titan.platformadapter.TvAudioManagerAdapter.DEFAULT_SOUNDMODE;
import static com.titan.platformadapter.TvAudioManagerAdapter.DEFAULT_SURROUND;
import static com.titan.platformadapter.TvAudioManagerAdapter.DEFAULT_TREBLE;

public class SoundPresenter implements ISoundContract.Presenter {

    private final Context mContext;
    private final AudioManager mAudioManager;
    private final TvAudioManagerAdapter mTvAudioManagerAdapter;

    public SoundPresenter(Context context) {
        mContext = context;
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mTvAudioManagerAdapter = new TvAudioManagerAdapter(context);
    }

    @Override
    public void setSoundMode(String soundMode) {
        mTvAudioManagerAdapter.setAudioSoundMode(Integer.parseInt(soundMode));
    }

    @Override
    public int getSoundMode() {
        int value = mTvAudioManagerAdapter.getAudioSoundMode();
        return value;
    }

    @Override
    public void setSurround(int surroundSound) {
        mTvAudioManagerAdapter.setSurround(surroundSound);
    }

    @Override
    public int getSurround() {
        int value = mTvAudioManagerAdapter.getSurround();
        return value;
    }

    @Override
    public void setBalance(int volumeBalance) {
        mTvAudioManagerAdapter.setBalance(volumeBalance);
    }

    @Override
    public int getBalance() {
        int value = mTvAudioManagerAdapter.getBalance();
        return value;
    }

    @Override
    public void setTreble(int treble) {
        mTvAudioManagerAdapter.setTreble(treble);
    }

    @Override
    public int getTreble() {
        int value = mTvAudioManagerAdapter.getTreble();
        return value;
    }

    @Override
    public void setBass(int bass) {
        mTvAudioManagerAdapter.setBass(bass);
    }

    @Override
    public int getBass() {
        int value = mTvAudioManagerAdapter.getBass();
        return value;
    }

    @Override
    public void setSystemSound(String soundEffects) {
        if (Integer.valueOf(soundEffects) != 0) {
            mAudioManager.loadSoundEffects();
            int current = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
            mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, current, 0);
        } else {
            mAudioManager.unloadSoundEffects();
        }
        Settings.System.putInt(mContext.getContentResolver(),
                Settings.System.SOUND_EFFECTS_ENABLED, Integer.valueOf(soundEffects));
    }

    @Override
    public int getSystemSound() {
        return Settings.System.getInt(mContext.getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 0);
    }

    @Override
    public void reset() {
        setSoundMode(DEFAULT_SOUNDMODE+"");
        setSurround(DEFAULT_SURROUND);
        setBalance(DEFAULT_BALANCE);
        setTreble(DEFAULT_TREBLE);
        setBass(DEFAULT_BASS);
    }
}
