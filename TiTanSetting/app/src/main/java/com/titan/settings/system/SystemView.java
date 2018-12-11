package com.titan.settings.system;

import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.titan.platformadapter.TvItemList;
import com.titan.platformadapter.TvViewManagerAdapter;
import com.titan.settings.R;
import com.titan.settings.base.BaseView;
import com.titan.settings.system.informatoin.InfoView;
import com.titan.settings.system.reset.ResetView;
import com.titan.settings.widgets.SettingCategory;
import com.titan.settings.widgets.SettingItem;

public class SystemView extends BaseView {

    private final TvViewManagerAdapter mTvViewManagerAdapternew;
    private TextView mTitleFirst;
    private ViewGroup mContentHolder;
    private SettingCategory mSystemCategory;
    private SettingItem mSystemInformationItem;
    private SettingItem mClickedItem;
    private SettingItem mSourceLicenceItem;
    private SettingItem mUserAgrementItem;
    private SettingItem mLocaleUpdateItem;
    private SettingItem mNetworkUpdateItem;
    private SettingItem mSystemRestoreItem;

    public SystemView(Application context) {
        super(context);
        mTvViewManagerAdapternew = new TvViewManagerAdapter(mContext);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_common_categoty;
    }

    @Override
    public void onCreate() {
        this.mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.main_system);
        loadViews();
        initFocus();
    }

    private void loadViews() {
        this.mContentHolder = (ViewGroup) mContentView.findViewById(R.id.scrollContentHolder);
        this.mSystemCategory = SettingCategory.createNewCategory(this.mContentHolder);
        this.mSystemCategory.setTitle(getResources().getString(R.string.main_system));
        getViews(this.mSystemCategory, TvItemList.TvSystemItem.systemCategoryList);
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
            case TvItemList.TvSystemItem.ITEM_SYSTEM_INFORMATION:
                this.mSystemInformationItem = SettingItem.createItem(group);
                this.mSystemInformationItem.setTitle(R.string.system_information);
                this.mSystemInformationItem.setRightArrowVisibility(VISIBLE);
                this.mSystemInformationItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startView(new InfoView(mApp));
                        mClickedItem = mSystemInformationItem;
                    }
                });
                break;
            case TvItemList.TvSystemItem.ITEM_SOURCE_LICENCE:
                this.mSourceLicenceItem = SettingItem.createItem(group);
                this.mSourceLicenceItem.setTitle(R.string.system_legal_license);
                this.mSourceLicenceItem.setRightArrowVisibility(VISIBLE);
                this.mSourceLicenceItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mTvViewManagerAdapternew.startLegalInformationView();
                        mClickedItem = mSourceLicenceItem;
                    }
                });
                break;
            case TvItemList.TvSystemItem.ITEM_USER_AGREEMENT:
                this.mUserAgrementItem = SettingItem.createItem(group);
                this.mUserAgrementItem.setTitle(R.string.system_subscriber_agreement);
                this.mUserAgrementItem.setRightArrowVisibility(VISIBLE);
                this.mUserAgrementItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        startSubscriberAgreementView();
                        mClickedItem = mUserAgrementItem;
                    }
                });
                break;
            case TvItemList.TvSystemItem.ITEM_LOCALE_UPDATE:
                this.mLocaleUpdateItem = SettingItem.createItem(group);
                this.mLocaleUpdateItem.setTitle(R.string.system_local_update);
                this.mLocaleUpdateItem.setRightArrowVisibility(VISIBLE);
                this.mLocaleUpdateItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mClickedItem = mLocaleUpdateItem;
                        mTvViewManagerAdapternew.systemLocalUpdate();
                        hideSetting();
                    }
                });
                break;
            case TvItemList.TvSystemItem.ITEM_NETWORK_UPDATE:
                this.mNetworkUpdateItem = SettingItem.createItem(group);
                this.mNetworkUpdateItem.setTitle(R.string.network_network_update);
                this.mNetworkUpdateItem.setRightArrowVisibility(VISIBLE);
                this.mNetworkUpdateItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mClickedItem = mNetworkUpdateItem;
                        mTvViewManagerAdapternew.systemNetworkUpdate();
                        hideSetting();

                    }
                });
                break;


            case TvItemList.TvSystemItem.ITEM_SYSTEM_RESTORE:
                this.mSystemRestoreItem = SettingItem.createItem(group);
                this.mSystemRestoreItem.setTitle(R.string.system_restore);
                this.mSystemRestoreItem.setRightArrowVisibility(VISIBLE);
                this.mSystemRestoreItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startView(new ResetView(mApp));
                        mClickedItem = mSystemRestoreItem;
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

    }

    @Override
    public void initFocus() {
        if (mSystemInformationItem != null) {
            mSystemInformationItem.requestFocus();
        }
    }

    @Override
    public void onResume() {
        if (mClickedItem != null) {
            mClickedItem.requestFocus();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }
}
