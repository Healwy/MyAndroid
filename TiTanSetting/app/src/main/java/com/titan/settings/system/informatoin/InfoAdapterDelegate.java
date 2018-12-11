package com.titan.settings.system.informatoin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.titan.settings.R;
import com.titan.settings.base.BaseModel;
import com.titan.settings.base.OnSettingItemClickListener;
import com.titan.settings.base.adapterdelegates.AbsListItemAdapterDelegate;
import com.titan.settings.model.MainModel;
import com.titan.settings.model.SystemInfoModel;

import java.util.List;

public class InfoAdapterDelegate extends AbsListItemAdapterDelegate<SystemInfoModel, BaseModel,
        InfoAdapterDelegate.InfoViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;

    public InfoAdapterDelegate(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected boolean isForViewType(BaseModel item, List<BaseModel> items, int position) {
        return item instanceof SystemInfoModel;
    }

    @Override
    protected InfoViewHolder onCreateViewHolder(ViewGroup parent) {
        return new InfoViewHolder(
                mInflater.inflate(R.layout.item_system_info, parent, false));
    }

    @Override
    protected void onBindViewHolder(final SystemInfoModel item, final InfoViewHolder viewHolder, final int pos) {
        viewHolder.name.setText(item.getName());
        viewHolder.value.setText(item.getValue());
    }

    @Override
    protected void onBindViewHolder(SystemInfoModel item, InfoViewHolder viewHolder, int pos, List<Object> payloads) {
    }

    static class InfoViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView value;

        InfoViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            value = (TextView) itemView.findViewById(R.id.tv_value);
        }
    }
}
