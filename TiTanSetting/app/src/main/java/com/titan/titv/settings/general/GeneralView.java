package com.titan.titv.settings.general;

import android.app.Application;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.titan.platformadapter.TvItemList;
import com.titan.titv.settings.R;
import com.titan.titv.settings.base.BaseView;
import com.titan.titv.settings.widgets.SettingCategory;
import com.titan.titv.settings.widgets.SettingItem;
import com.titan.titv.settings.widgets.SwitcherItem;

import java.util.ArrayList;
import java.util.List;

public class GeneralView extends BaseView {
    private TextView mTitleFirst;
    private ArrayList<String> mIMElistId;
    private String[] mIMEOptioin;
    private String[] mLanguageOption;
    private String[] mNoSignalPowerOffOption;
    private String[] mScreenSaverOption;
    private ViewGroup mContentHolder;
    private SettingCategory mGeneralCategory;
    private SettingCategory mSaveEnergyCategory;
    private SwitcherItem mLanguageItem;
    private IGeneralContract.Presenter mPresenter;
    private SwitcherItem mImeItem;


    public GeneralView(Application app) {
        super(app);
        mPresenter = new GeneralPresenter(mContext);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_common_categoty;
    }

    @Override
    public void onCreate() {
        this.mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.main_general);
        initData();
        loadView();
        initFocus();
        setUpGeneralUI();
    }

    private void setUpGeneralUI() {
        if("zh".equals(mPresenter.getLauanges())){
            this.mLanguageItem.setCurrentIndex(0);
        }else{
            this.mLanguageItem.setCurrentIndex(1);
        }

        String defIMEStr = mPresenter.getDefaultIME();
        int defIMEIndex = this.mIMElistId.indexOf(defIMEStr);
        this.mImeItem.setCurrentIndex(defIMEIndex);

    }

    private void loadView() {
        this.mContentHolder = (ViewGroup) findViewById(R.id.scrollContentHolder);
        if (mContentHolder.getChildCount() > 0)
            mContentHolder.removeAllViews();
        this.mGeneralCategory = SettingCategory.createNewCategory(this.mContentHolder);
        this.mGeneralCategory.setTitle(getResources().getString(R.string.main_general));
        getViews(this.mGeneralCategory, TvItemList.TvGeneralItem.generalCategoryList);

        this.mSaveEnergyCategory = SettingCategory.createNewCategory(this.mContentHolder);
        this.mSaveEnergyCategory.setTitle(getResources().getString(R.string.general_save_energy));
        getViews(this.mSaveEnergyCategory, TvItemList.TvGeneralItem.saveEnergyCategoryList);
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
            case TvItemList.TvGeneralItem.ITEM_LANGUAGE:
                this.mLanguageItem = SwitcherItem.createItem(group);
                this.mLanguageItem.setTitle(R.string.general_language_setting);
                this.mLanguageItem.setOptions(this.mLanguageOption);
                this.mLanguageItem.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
                    @Override
                    public boolean onSwitchTo(int i) {
                        if(i == 0){
                            mPresenter.setLauanges("zh");
                        }else  {
                            mPresenter.setLauanges("en");
                        }
                        postChangeLanguage();
                        return true;
                    }
                });
                break;
            case TvItemList.TvGeneralItem.ITEM_IME:
                this.mImeItem = SwitcherItem.createItem(group);
                this.mImeItem.setTitle(R.string.general_input);
                this.mImeItem.setOptions(this.mIMEOptioin);
                this.mImeItem.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
                    @Override
                    public boolean onSwitchTo(int i) {
                        mPresenter.setDefaultIME(mIMElistId.get(i));
                        return true;
                    }
                });
                break;
        }
        return null;
    }

    private void postChangeLanguage() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeLanguage();
            }
        }, 10);
    }

    @Override
    protected void initData() {
        this.mLanguageOption = new String[]{mResources.getString(R.string.general_language_zh),
                mResources.getString(R.string.general_language_en)};
        InputMethodManager imm = (InputMethodManager) mApp.getSystemService(mApp.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> methodList = imm.getInputMethodList();
        List<String> IMElist = new ArrayList<String>();
        mIMElistId = new ArrayList<String>();
        for (InputMethodInfo mi : methodList) {
            CharSequence name = mi.loadLabel(mContext.getPackageManager());
            mIMElistId.add(mi.getId());
            if ("Android 键盘 (AOSP)".equals(name + "")) {
                IMElist.add("AOSP" + "");
            } else {
                IMElist.add(name + "");
            }
        }
        this.mIMEOptioin = IMElist.toArray(new String[IMElist.size()]);
        this.mNoSignalPowerOffOption = getResources().getStringArray(R.array.setting_normal_list);
        this.mScreenSaverOption = getResources().getStringArray(R.array.general_screensaver_timeout_list);
    }

    @Override
    public void initFocus() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mLanguageItem != null){
                    mLanguageItem.requestFocus();
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
