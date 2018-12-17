package com.titan.settings.network;

import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.titan.platform.adapter.TvItemList;
import com.titan.settings.R;
import com.titan.settings.base.BaseView;
import com.titan.settings.network.ethernet.EthernetView;
import com.titan.settings.network.hotspot.HotspotView;
import com.titan.settings.network.wireless.WirelessView;
import com.titan.settings.widgets.SettingCategory;
import com.titan.settings.widgets.SettingItem;
import com.titan.settings.widgets.SwitcherItem;

public class NetworkView extends BaseView {

    private TextView mTitleFirst;
    private ViewGroup mContentHolder;
    private SettingCategory mNetworkCategory;
    private SettingItem mWiredConnectItem;
    private SettingItem mClickedItem;
    private SettingItem mWirelessConnectItem;
    private SettingItem mBlueToothItem;
    private SettingItem mWirelesshotspotItem;
    private SettingItem mPppoeItem;
    private SwitcherItem mSimItem;
    private String[] mSimOption;

    public NetworkView(Application app) {
        super(app);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_common_categoty;
    }

    @Override
    public void onCreate() {
        this.mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.main_network);
        initData();
        loadViews();
        setupNetworkUI();
        initFocus();
    }

    private void setupNetworkUI() {
        this.mSimItem.setCurrentIndex(0);
    }

    private void loadViews() {
        this.mContentHolder = (ViewGroup) mContentView.findViewById(R.id.scrollContentHolder);
        this.mNetworkCategory = SettingCategory.createNewCategory(this.mContentHolder);
        this.mNetworkCategory.setTitle(getResources().getString(R.string.main_network));
        getViews(this.mNetworkCategory, TvItemList.TvNetworkItem.getNetworkCategoryList());
    }

    private void getViews(SettingCategory category, int[] list) {
        for (int id : list) {
            if (id >= 0) {
                SettingItem item = getItem(id, category);
            }
        }
    }

    private SettingItem getItem(int index, ViewGroup group) {
        switch (index) {
            case TvItemList.TvNetworkItem.ITEM_WIRED_CONNECT:
                this.mWiredConnectItem = SettingItem.createItem(group);
                this.mWiredConnectItem.setTitle(R.string.network_ethernet_title);
                this.mWiredConnectItem.setRightArrowVisibility(VISIBLE);
                this.mWiredConnectItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         startView(new EthernetView(mApp));
                        mClickedItem = mWiredConnectItem;
                    }
                });
                break;
            case TvItemList.TvNetworkItem.ITEM_WIRRELESS_CONNECT:
                this.mWirelessConnectItem = SettingItem.createItem(group);
                this.mWirelessConnectItem.setTitle(R.string.network_wireless_title);
                this.mWirelessConnectItem.setRightArrowVisibility(VISIBLE);
                this.mWirelessConnectItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startView(new WirelessView(mApp));
                        mClickedItem = mWirelessConnectItem;
                    }
                });
                break;
            case TvItemList.TvNetworkItem.ITEM_BLUETOOTH:
                this.mBlueToothItem = SettingItem.createItem(group);
                this.mBlueToothItem.setTitle(R.string.network_bluetooth);
                this.mBlueToothItem.setRightArrowVisibility(VISIBLE);
                this.mBlueToothItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //startView(new BluetoothSearchView(mApp));
                        mClickedItem = mBlueToothItem;
                    }
                });
                break;
            case TvItemList.TvNetworkItem.ITEM_WIRELESS_HOTSPOT:
                this.mWirelesshotspotItem = SettingItem.createItem(group);
                this.mWirelesshotspotItem.setTitle(R.string.network_wireless_hotspot);
                this.mWirelesshotspotItem.setRightArrowVisibility(VISIBLE);
                this.mWirelesshotspotItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startView(new HotspotView(mApp));
                        mClickedItem = mWirelesshotspotItem;
                    }
                });
                break;
            case TvItemList.TvNetworkItem.ITEM_PPPOE:
                this.mPppoeItem = SettingItem.createItem(group);
                this.mPppoeItem.setTitle(R.string.network_pppoe);
                this.mPppoeItem.setRightArrowVisibility(VISIBLE);
                this.mPppoeItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // startView(new PppoeView(mApp));
                        mClickedItem = mPppoeItem;
                    }
                });
                break;
            case TvItemList.TvNetworkItem.ITEM_SIM_4G:
                this.mSimItem = SwitcherItem.createItem(group);
                this.mSimItem.setTitle(R.string.network_sim_4g);
                this.mSimItem.setOptions(this.mSimOption);
                this.mSimItem.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
                    @Override
                    public boolean onSwitchTo(int i) {

                        return true;
                    }
                });
                break;
            default:
                return null;
        }
        return null;
    }

    @Override
    protected void initData() {
        this.mSimOption = mResources.getStringArray(R.array.setting_normal_list);
    }

    @Override
    public void initFocus() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mWiredConnectItem != null){
                    mWiredConnectItem.requestFocus();
                }
            }
        }, 10);
    }

    @Override
    public void onResume() {
        this.mClickedItem.requestFocus();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }
}
