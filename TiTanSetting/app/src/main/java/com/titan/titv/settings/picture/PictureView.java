package com.titan.titv.settings.picture;

import android.app.Application;
import android.view.ViewGroup;
import android.widget.TextView;

import com.titan.platformadapter.TvItemList;
import com.titan.titv.settings.R;
import com.titan.titv.settings.base.BaseView;
import com.titan.titv.settings.widgets.SettingCategory;
import com.titan.titv.settings.widgets.SettingItem;

public class PictureView extends BaseView {


    private IPictureContract.Presenter mPresenter;
    private TextView mTitleFirst;
    private String[] mSystemSoundOption;
    private String[] mNormalOption;
    private ViewGroup mContentHolder;
    private SettingCategory mPictureCategory;

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
