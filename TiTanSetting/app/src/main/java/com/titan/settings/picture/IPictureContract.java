package com.titan.settings.picture;

import com.titan.settings.base.IBaseContract;

public interface IPictureContract {
    interface Presenter extends IBaseContract.BasePresenter {
        void setMode(String imageMode);

        int getMode();

        void setBrightness(int value);

        int getBrightness();

        void setContrast(int value);

        int getContrast();

        void setSaturation(int value);

        int getSaturation();

        void setHue(int value);

        int getHue();

        void setSharpness(int value);

        int getSharpness();

        void setBacklight(int value);

        int getBacklight();

        void setColorTemperature(String colorTemperature);

        int getColorTemperature();

        void reset();

    }
}
