package com.titan.titv.settings.sound;

import com.titan.titv.settings.base.IBaseContract;

public interface ISoundContract {
    interface Presenter extends IBaseContract.BasePresenter {

        void setSoundMode(String soundMode);

        int getSoundMode();

        void setSurround(int surroundSound);

        int getSurround();

        void setBalance(int volumeBalance);

        int getBalance();

        void setTreble(int treble);

        int getTreble();

        void setBass(int bass);

        int getBass();

        void setSystemSound(String soundEffects);

        int getSystemSound();

        void reset();

    }
}
