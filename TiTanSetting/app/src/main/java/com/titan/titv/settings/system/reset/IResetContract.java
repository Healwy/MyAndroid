package com.titan.titv.settings.system.reset;

import com.titan.titv.settings.base.IBaseContract;

interface IResetContract {

    interface View extends IBaseContract.BaseView {
        void showProgressDialog();
        void dismissProgressDialog();
    }

    interface Presenter extends IBaseContract.BasePresenter {
        String onFactoryReset();
    }
}
