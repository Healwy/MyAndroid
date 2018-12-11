package com.titan.settings.system.informatoin;

import android.content.Context;

import com.titan.settings.base.BaseModel;
import com.titan.settings.base.OnSettingItemClickListener;
import com.titan.settings.base.adapterdelegates.ListDelegationAdapter;
import com.titan.settings.main.MainAdapterDelegate;

import java.util.List;

public class InfoAdapter extends ListDelegationAdapter<List<BaseModel>> {

    public InfoAdapter(Context context, List<BaseModel> items) {
        this.delegatesManager.addDelegate(new InfoAdapterDelegate(context));
        setItems(items);
    }
}
