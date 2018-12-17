package com.titan.platform.adapter;

import android.content.Context;

public class TvAudioManagerAdapter {

    /* This value is mapping to EN_SOUND_MODE */
    /**
     * Sound Mode , user.
     */
    public static final int SOUND_MODE_USER = 0;
    /**
     * Sound Mode , standard.
     */
    public static final int SOUND_MODE_STANDARD = 1;
    /**
     * Sound Mode , music.
     */
    public static final int SOUND_MODE_MUSIC= 2;
    /**
     * Sound Mode , clear.
     */
    public static final int SOUND_MODE_CLEAR= 3;

    public static final int DEFAULT_SOUNDMODE = SOUND_MODE_USER;
    public static final int DEFAULT_BASS = 50;
    public static final int DEFAULT_TREBLE = 50;
    public static final int DEFAULT_BALANCE = 50;
    public static final int DEFAULT_SURROUND = 0;


    private Context mContext;
    private static int soundMode;
    private static int Bass;
    private static int Treble;
    private static int Balance;
    private static int Surround;

    public TvAudioManagerAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public boolean setAudioSoundMode(int value) {
        soundMode = value;
        return true;
    }

    public int getAudioSoundMode() {
        return soundMode;
    }

    public boolean setBass(int bassValue) {
        Bass = bassValue;
        return false;
    }

    public int getBass() {
        return Bass;
    }

    public boolean setTreble(int trebleValue) {
        Treble = trebleValue;
        return false;
    }


    public int getTreble() {
        return Treble;
    }

    public boolean setBalance(int balance) {
        Balance = balance;
        return false;
    }

    public int getBalance() {

        return Balance;
    }

    public boolean setSurround(int value) {
        Surround = value;
        return false;
    }

    public int getSurround() {

        return Surround;
    }


}
