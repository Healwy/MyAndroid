package com.titan.titv.settings.system.informatoin;

import android.content.Context;

import com.titan.titv.settings.base.BaseModel;
import com.titan.titv.settings.base.OnSettingItemClickListener;
import com.titan.titv.settings.base.adapterdelegates.ListDelegationAdapter;
import com.titan.titv.settings.main.MainAdapterDelegate;

import java.util.List;

public class InfoAdapter extends ListDelegationAdapter<List<BaseModel>> {

    public InfoAdapter(Context context, List<BaseModel> items) {
        this.delegatesManager.addDelegate(new InfoAdapterDelegate(context));
        setItems(items);
    }
}
