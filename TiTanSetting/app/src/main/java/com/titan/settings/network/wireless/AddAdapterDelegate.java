package com.titan.settings.network.wireless;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.titan.settings.R;
import com.titan.settings.base.OnWifiItemClickListener;
import com.titan.settings.base.adapterdelegates.AbsListItemAdapterDelegate;
import com.titan.settings.model.AddWirelssModel;

import java.util.List;

public class AddAdapterDelegate extends AbsListItemAdapterDelegate<AddWirelssModel, Object,
        AddAdapterDelegate.AddViewHolder> {
    private LayoutInflater inflater;
    private Context mContext;
    private OnWifiItemClickListener mListener;

    public AddAdapterDelegate(Context context, OnWifiItemClickListener l) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mListener = l;
    }

    @Override
    protected boolean isForViewType(Object item, List<Object> items, int position) {
        return item instanceof AddWirelssModel;
    }

    @Override
    protected AddViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AddViewHolder(
                inflater.inflate(R.layout.item_wireless_list, parent, false));
    }

    @Override
    protected void onBindViewHolder(AddWirelssModel item, AddViewHolder holder, final int pos) {
        holder.itemView.setVisibility(item.isOn() ? View.VISIBLE : View.INVISIBLE);

        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    mListener.onWifiItemFocusChange(pos);
            }
        });
        holder.wifiLock.setVisibility(ViewGroup.VISIBLE);
        holder.wifiName.setVisibility(ViewGroup.VISIBLE);
        holder.wifiState.setVisibility(ViewGroup.GONE);
        holder.wifiMark.setVisibility(ViewGroup.GONE);
        holder.wifiSingle.setVisibility(ViewGroup.GONE);

        if ("Add".equals(item.getName())) {
            holder.wifiLock.setImageResource(R.drawable.icon_wifi_add);
            holder.wifiName.setText(mContext.getResources().getString(R.string.wifi_state_add));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onWifiAdd(0);
                }
            });
        } else {
            holder.wifiLock.setImageResource(R.drawable.icon_via_wps);
            holder.wifiName.setText(mContext.getResources().getString(R.string.network_wps));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onWifiAdd(1);
                }
            });
        }
    }

    @Override
    protected void onBindViewHolder(AddWirelssModel item, AddViewHolder viewHolder, int pos, List<Object> payloads) {
    }

    static class AddViewHolder extends RecyclerView.ViewHolder {
        TextView wifiName;
        TextView wifiState;
        ImageView wifiSingle;
        ImageView wifiLock;
        ImageView wifiMark;

        AddViewHolder(View itemView) {
            super(itemView);
            wifiLock = (ImageView) itemView.findViewById(R.id.wifi_item_lock);
            wifiName = (TextView) itemView.findViewById(R.id.wifi_item_title);
            wifiState = (TextView) itemView.findViewById(R.id.wifi_item_summery);
            wifiMark = (ImageView) itemView.findViewById(R.id.wifi_item_mark);
            wifiSingle = (ImageView) itemView.findViewById(R.id.wifi_item_single);
        }
    }
}
