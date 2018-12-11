package com.titan.settings.system.reset;

import com.titan.settings.base.IBaseContract;

interface IResetContract {

    interface View extends IBaseContract.BaseView {
        void showProgressDialog();
        void dismissProgressDialog();
    }

    interface Presenter extends IBaseContract.BasePresenter {
        String onFactoryReset();
    }
}
