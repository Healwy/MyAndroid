package com.titan.settings.network.wireless;

import android.content.Context;

import com.titan.settings.base.adapterdelegates.ListDelegationAdapter;
import com.titan.settings.base.OnWifiItemClickListener;


import java.util.List;

public class WirelessAdapter extends ListDelegationAdapter<List<Object>> {
    public WirelessAdapter(Context context, List<Object> items, OnWifiItemClickListener l) {
        //TODO 临时注释
//        this.delegatesManager.addDelegate(new AccessPointAdapterDelegate(context, l));
        this.delegatesManager.addDelegate(new AddAdapterDelegate(context, l));
        setItems(items);
    }
}
