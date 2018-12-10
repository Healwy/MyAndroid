package com.titan.titv.settings.system.informatoin;

import com.titan.titv.settings.base.BaseModel;
import com.titan.titv.settings.base.IBaseContract;

import java.util.List;

interface IInfoContract {

    interface View extends IBaseContract.BaseView {
        void onDataLoaded(List<BaseModel> data);
    }

    interface Presenter extends IBaseContract.BasePresenter {
        String getDisplaySize();

        String getResolution();

        String getCpuInfo();

        String getGpuInfo();

        String getRomRamSize();

        String getEthernetMac();

        String getWirelessMac();

        String getSysVersion();

        String getSdkVersion();

        void loadData();
    }
}
