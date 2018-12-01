package com.titan.titv.settings.main;

import android.content.Context;

import com.titan.titv.settings.base.BaseModel;
import com.titan.titv.settings.base.OnSettingItemClickListener;
import com.titan.titv.settings.base.adapterdelegates.ListDelegationAdapter;

import java.util.List;

public class MainAdapter extends ListDelegationAdapter<List<BaseModel>> {

    public MainAdapter(Context context, List<BaseModel> items, OnSettingItemClickListener listener) {
        this.delegatesManager.addDelegate(new MainAdapterDelegate(context, listener));
        setItems(items);
    }
}
