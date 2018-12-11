package com.titan.settings.general;

import com.titan.settings.base.IBaseContract;

public interface IGeneralContract  {
    interface Presenter extends IBaseContract.BasePresenter {
        void setLauanges(String value);

        String getLauanges();

        void setDefaultIME(String value);

        String getDefaultIME();
    }
}
