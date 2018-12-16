package com.titan.settings.network.wireless;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//TODO 临时注释
//import com.android.settingslib.wifi.AccessPoint;
//import com.titan.settings.R;
//import com.titan.settings.base.OnWifiItemClickListener;
//
//import java.util.List;
//
//public class AccessPointAdapterDelegate extends AbsListItemAdapterDelegate<AccessPoint, Object,
//        AccessPointAdapterDelegate.APViewHolder> {
//    private LayoutInflater inflater;
//    private Context mContext;
//    private OnWifiItemClickListener mListener;
//
//    public AccessPointAdapterDelegate(Context context, OnWifiItemClickListener l) {
//        mContext = context;
//        inflater = LayoutInflater.from(context);
//        mListener = l;
//    }
//
//    @Override
//    protected boolean isForViewType(Object item, List<Object> items, int position) {
//        return item instanceof AccessPoint;
//    }
//
//    @Override
//    protected APViewHolder onCreateViewHolder(ViewGroup parent) {
//        return new APViewHolder(
//                inflater.inflate(R.layout.item_wireless_list, parent, false));
//    }
//
//    @Override
//    protected void onBindViewHolder(AccessPoint item, APViewHolder holder, final int pos) {
//        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                mListener.onWifiItemFocusChange(pos);
//            }
//        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onWifiItemClick(pos);
//            }
//        });
//        holder.wifiName.setVisibility(ViewGroup.VISIBLE);
//        holder.wifiName.setText(item.getSsid());
//
//        if (!TextUtils.isEmpty(item.getSettingsSummary())) {
//            holder.wifiState.setVisibility(ViewGroup.VISIBLE);
//            holder.wifiState.setText(mContext.getResources().getString(R.string
//                    .wifi_list_summary, item.getSettingsSummary()));
//        } else {
//            holder.wifiState.setVisibility(ViewGroup.GONE);
//        }
//
//        holder.wifiLock.setImageResource(R.drawable.icon_wifi_lock);
//        if (item.getSecurity() != AccessPoint.SECURITY_NONE) {
//            holder.wifiLock.setVisibility(ViewGroup.VISIBLE);
//        } else {
//            holder.wifiLock.setVisibility(ViewGroup.INVISIBLE);
//        }
//
//        if (item.isActive()) {
//            holder.wifiMark.setVisibility(ViewGroup.VISIBLE);
//        } else {
//            holder.wifiMark.setVisibility(ViewGroup.GONE);
//        }
//
//        holder.wifiSingle.setVisibility(ViewGroup.VISIBLE);
//        switch (item.getLevel()) {
//            case 0:
//                holder.wifiSingle.setImageResource(R.drawable.wifi_signal_1);
//                break;
//            case 1:
//                holder.wifiSingle.setImageResource(R.drawable.wifi_signal_2);
//                break;
//            case 2:
//                holder.wifiSingle.setImageResource(R.drawable.wifi_signal_3);
//                break;
//            case 3:
//                holder.wifiSingle.setImageResource(R.drawable.wifi_signal_4);
//                break;
//            default:
//                holder.wifiSingle.setImageResource(R.drawable.wifi_signal_0);
//                break;
//
//        }
//    }
//
//    @Override
//    protected void onBindViewHolder(AccessPoint item, APViewHolder viewHolder, int pos, List<Object> payloads) {
//        //TODO
//    }
//
//    static class APViewHolder extends RecyclerView.ViewHolder {
//        TextView wifiName;
//        TextView wifiState;
//        ImageView wifiSingle;
//        ImageView wifiLock;
//        ImageView wifiMark;
//
//        APViewHolder(View itemView) {
//            super(itemView);
//            wifiLock = (ImageView) itemView.findViewById(R.id.wifi_item_lock);
//            wifiName = (TextView) itemView.findViewById(R.id.wifi_item_title);
//            wifiState = (TextView) itemView.findViewById(R.id.wifi_item_summery);
//            wifiMark = (ImageView) itemView.findViewById(R.id.wifi_item_mark);
//            wifiSingle = (ImageView) itemView.findViewById(R.id.wifi_item_single);
//        }
//
//    }
//}
