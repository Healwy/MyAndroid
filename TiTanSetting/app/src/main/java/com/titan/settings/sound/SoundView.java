package com.titan.settings.sound;

import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.titan.platform.adapter.TvItemList;
import com.titan.settings.R;
import com.titan.settings.base.BaseView;
import com.titan.settings.widgets.SettingCategory;
import com.titan.settings.widgets.SettingItem;
import com.titan.settings.widgets.SwitcherItem;

public class SoundView extends BaseView {

    private final SoundPresenter mPresenter;
    private TextView mTitleFirst;
    private ViewGroup mContentHolder;
    private SettingCategory mSoundCategory;
    private SwitcherItem mSystemSoundItem;
    private String[] mOpenOptioin;
    private SettingItem mSoundEffectViewItem;
    private SettingItem mClickedItem;

    public SoundView(Application app) {
        super(app);
        mPresenter = new SoundPresenter(mContext);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_common_categoty;
    }

    @Override
    public void onCreate() {
        this.mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.main_sound);
        initData();
        loadViews();
        initFocus();
        setUpSoundUI();

    }

    private void setUpSoundUI() {
        this.mSystemSoundItem.setCurrentIndex(mPresenter.getSystemSound());
    }

    private void loadViews() {
        this.mContentHolder = (ViewGroup) findViewById(R.id.scrollContentHolder);
        this.mSoundCategory = SettingCategory.createNewCategory(this.mContentHolder);
        this.mSoundCategory.setTitle(getResources().getString(R.string.sound_system_sound));
        getViews(this.mSoundCategory, TvItemList.TvSoundItem.getSoundCategoryList());
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
            case TvItemList.TvSoundItem.ITEM_SYSTEM_SOUND:
                this.mSystemSoundItem = SwitcherItem.createItem(group);
                this.mSystemSoundItem.setTitle(R.string.sound_system_sound);
                this.mSystemSoundItem.setOptions(this.mOpenOptioin);
                this.mSystemSoundItem.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
                    @Override
                    public boolean onSwitchTo(int i) {
                        mPresenter.setSystemSound(i + "");
                        return true;
                    }
                });
                break;
            case TvItemList.TvSoundItem.ITEM_SOUND_PARAM:
                this.mSoundEffectViewItem = SettingItem.createItem(group);
                this.mSoundEffectViewItem.setTitle(R.string.sound_effect);
                this.mSoundEffectViewItem.setRightArrowVisibility(View.VISIBLE);
                this.mSoundEffectViewItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickedItem = mSoundEffectViewItem;
                        startView(new SoundEffectView(mApp));
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
        this.mOpenOptioin = mResources.getStringArray(R.array.setting_normal_list);
    }

    @Override
    public void initFocus() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSystemSoundItem != null) {
                    mSystemSoundItem.requestFocus();
                }
            }
        }, 10);
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
