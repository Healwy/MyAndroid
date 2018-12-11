package com.titan.settings.picture;

import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.titan.platformadapter.TvItemList;
import com.titan.settings.R;
import com.titan.settings.base.BaseView;
import com.titan.settings.widgets.SettingCategory;
import com.titan.settings.widgets.SettingItem;
import com.titan.settings.widgets.SwitcherItem;

public class PictureView extends BaseView {


    private IPictureContract.Presenter mPresenter;
    private TextView mTitleFirst;
    private String[] mSystemSoundOption;
    private String[] mNormalOption;
    private ViewGroup mContentHolder;
    private SettingCategory mPictureCategory;
    private SwitcherItem mBackLightItem;
    private SettingItem mPictureParameterItem;
    private SettingItem mClickedItem;

    public PictureView(Application app) {
        super(app);
        mPresenter = new PicturePresenter(mContext);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_common_categoty;
    }

    @Override
    public void onCreate() {
        this.mTitleFirst = (TextView) findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.main_picture);
        initData();
        loadViews();
        refreshViewValue();
        initFocus();
    }

    private void refreshViewValue() {
        this.mBackLightItem.setCurrentIndex(mPresenter.getBacklight());
    }

    private void loadViews() {
        this.mContentHolder = (ViewGroup) mContentView.findViewById(R.id.scrollContentHolder);
        this.mPictureCategory = SettingCategory.createNewCategory(this.mContentHolder);
        this.mPictureCategory.setTitle(getResources().getString(R.string.main_picture));
        getViews(this.mPictureCategory, TvItemList.TvPictureItem.pictureCategoryList);
    }

    private void getViews(SettingCategory category, int[] list) {
        for (int id : list) {
            if (id >= 0) {
                SettingItem item = getItem(id, category);
            }
        }
    }


    private SettingItem getItem(int index, SettingCategory group) {
        switch (index) {
            case TvItemList.TvPictureItem.ITEM_BACKLIGHT:
                this.mBackLightItem = SwitcherItem.createItem(group);
                this.mBackLightItem.setTitle(R.string.picture_backlight);
                this.mBackLightItem.setOptions(this.mNormalOption);
                this.mBackLightItem.setRecycle(false);
                this.mBackLightItem.setRepeated(true);
                this.mBackLightItem.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
                    @Override
                    public boolean onSwitchTo(int i) {
                        mPresenter.setBacklight(i);
                        return true;
                    }
                });
                break;
            case TvItemList.TvPictureItem.ITEM_PICTURE_PARAMETER:
                this.mPictureParameterItem = SettingItem.createItem(group);
                this.mPictureParameterItem.setTitle(R.string.picture_param);
                this.mPictureParameterItem.setRightArrowVisibility(View.VISIBLE);
                this.mPictureParameterItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startView(new PictureParamView(mApp));
                        mClickedItem = mPictureParameterItem;
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
        this.mNormalOption = new String[101];
        for (int i = 0; i < 101; i++) {
            this.mNormalOption[i] = String.valueOf(i);
        }
        this.mSystemSoundOption = getResources().getStringArray(R.array.setting_normal_list);
    }

    @Override
    public void initFocus() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mBackLightItem != null){
                    mBackLightItem.requestFocus();
                }
            }
        }, 10);
    }

    @Override
    public void onResume() {
        mClickedItem.requestFocus();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }
}
