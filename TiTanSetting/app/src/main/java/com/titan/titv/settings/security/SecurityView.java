package com.titan.titv.settings.security;

import android.app.Application;
import android.view.ViewGroup;
import android.widget.TextView;

import com.titan.platformadapter.TvItemList;
import com.titan.titv.settings.R;
import com.titan.titv.settings.base.BaseView;
import com.titan.titv.settings.widgets.SettingCategory;
import com.titan.titv.settings.widgets.SettingItem;
import com.titan.titv.settings.widgets.SwitcherItem;

public class SecurityView extends BaseView {


    private final SecurityPresenter mPresenter;
    private TextView mTitleFirst;
    private String[] mDenyAllowOptioin;
    private SettingCategory mSecurityCategory;
    private ViewGroup mContentHolder;
    private SwitcherItem mAdbDebugItem;
    private SwitcherItem mAllowInstallItem;

    public SecurityView(Application app) {
        super(app);
        mPresenter = new SecurityPresenter(app);
    }


    @Override
    protected int getViewResId() {
        return R.layout.view_common_categoty;
    }

    @Override
    public void onCreate() {
        this.mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.main_security);
        initData();
        loadViews();
        initFocus();
        setUpSecurityUI();
    }

    private void setUpSecurityUI() {
        this.mAllowInstallItem.setCurrentIndex(mPresenter.getUnknowSource());
        this.mAdbDebugItem.setCurrentIndex(mPresenter.getADB());
    }

    private void loadViews() {
        this.mContentHolder = (ViewGroup) findViewById(R.id.scrollContentHolder);
        this.mSecurityCategory = SettingCategory.createNewCategory(this.mContentHolder);
        this.mSecurityCategory.setTitle(getResources().getString(R.string.main_security));
        getViews(this.mSecurityCategory, TvItemList.TvSecurityItem.securityCategoryList);
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
            case TvItemList.TvSecurityItem.ITEM_INSTALL_UNKNOW_SOURCE:
                this.mAllowInstallItem = SwitcherItem.createItem(group);
                this.mAllowInstallItem.setTitle(R.string.security_unknown_sources);
                this.mAllowInstallItem.setOptions(this.mDenyAllowOptioin);
                this.mAllowInstallItem.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
                    @Override
                    public boolean onSwitchTo(int i) {
                        mPresenter.setUnknowSource(i + "");
                        return true;
                    }
                });
                break;
            case TvItemList.TvSecurityItem.ITEM_ADB_DEBUG:
                this.mAdbDebugItem = SwitcherItem.createItem(group);
                this.mAdbDebugItem.setTitle(R.string.security_adb_debug);
                this.mAdbDebugItem.setOptions(this.mDenyAllowOptioin);
                this.mAdbDebugItem.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
                    @Override
                    public boolean onSwitchTo(int i) {
                        mPresenter.setADB(i);
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
        this.mDenyAllowOptioin = getResources().getStringArray(R.array.setting_deny_allow_list);
    }

    @Override
    public void initFocus() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAllowInstallItem != null){
                    mAllowInstallItem.requestFocus();
                }
            }
        }, 10);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }
}
