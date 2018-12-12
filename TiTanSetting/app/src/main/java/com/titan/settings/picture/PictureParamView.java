package com.titan.settings.picture;

import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.titan.settings.R;
import com.titan.settings.base.BaseView;
import com.titan.settings.widgets.SettingCategory;
import com.titan.settings.widgets.SmallSettingItem;
import com.titan.settings.widgets.SmallSwitcherItem;
import com.titan.settings.widgets.SwitcherItem;

import static com.titan.platformadapter.TvPictureManagerAdapter.PICTURE_MODE_USER;

public class PictureParamView extends BaseView {

    private final PicturePresenter mPresenter;
    private TextView mTitleFirst;
    private String[] mModeOption;
    private String[] mNormalOption;
    private String[] mColorTemperatureOption;
    private SettingCategory mModeCategory;
    private SettingCategory mCustomizeCategory;
    private ViewGroup mCustomizeCategoryHolder;
    private SmallSwitcherItem mModeSwitcher;
    private SmallSwitcherItem mBrightnessSwitcher;
    private SmallSwitcherItem mContrastSwitcher;
    private SmallSwitcherItem mSaturationSwitcher;
    private SmallSwitcherItem mHueSwitcher;
    private SmallSwitcherItem mSharpnessSwitcher;
    private SmallSwitcherItem mColorTemperatureSwitcher;
    private SmallSettingItem mResetItem;

    private int mCurrentMode;
    private int mBrightnessValue;
    private int mContrastValue;
    private int mSaturationValue;
    private int mHueValue;
    private int mSharpnessValue;
    private int mColorTemperatureValue;

    public PictureParamView(Application app) {
        super(app);
        this.mPresenter = new PicturePresenter(mContext);
    }


    @Override
    protected int getViewResId() {
        return R.layout.view_common_second_category;
    }

    @Override
    public void onCreate() {
        this.setBackgroundColor(getResources().getColor(R.color.transparent));
        this.mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.picture_param);
        initData();
        loadViews();
        loadParamValue();
        setUpPictureParamUI();
        initFocus();
    }

    private void loadViews() {
        RelativeLayout contentView = (RelativeLayout) findViewById(R.id.content_view);
        RelativeLayout.LayoutParams cvLP = (RelativeLayout.LayoutParams) contentView.getLayoutParams();
        contentView.setGravity(48);
        ViewGroup contentHolder = (ViewGroup) findViewById(R.id.scrollContentHolder);

        this.mModeCategory = SettingCategory.createNewCategory(contentHolder);
        RelativeLayout.LayoutParams modeLp = (RelativeLayout.LayoutParams) this.mModeCategory.getContentHolder().getLayoutParams();
        modeLp.topMargin = getResources().getDimensionPixelSize(R.dimen.display_params_modeswitcher_top_margin);
        this.mModeCategory.getContentHolder().setLayoutParams(modeLp);

        this.mCustomizeCategory = SettingCategory.createNewCategory(contentHolder);
        this.mCustomizeCategory.findViewById(R.id.categoryTitle).setVisibility(GONE);
        this.mCustomizeCategoryHolder = this.mCustomizeCategory.getContentHolder();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.mCustomizeCategoryHolder.getLayoutParams();
        lp.topMargin = getResources().getDimensionPixelSize(R.dimen.display_params_modecategory_top_margin);
        this.mCustomizeCategoryHolder.setLayoutParams(lp);
        getViews();
    }

    private void getViews() {
        this.mModeSwitcher = SmallSwitcherItem.createItem(this.mContext, this.mModeCategory);
        this.mModeSwitcher.setTitle(R.string.picture_pictureMode);
        this.mModeSwitcher.setOptions(mModeOption);
        this.mModeSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                PictureParamView.this.mCurrentMode = index;
                mPresenter.setMode(index + "");
                loadParamValue();
                setUpPictureParamUI();
                return true;
            }
        });
        this.mBrightnessSwitcher = SmallSwitcherItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mBrightnessSwitcher.setTitle(R.string.picture_brightness);
        this.mBrightnessSwitcher.setOptions(mNormalOption);
        this.mBrightnessSwitcher.setRecycle(false);
        this.mBrightnessSwitcher.setRepeated(true);
        this.mBrightnessSwitcher.setArrowVisible(false);
        this.mBrightnessSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                PictureParamView.this.mBrightnessValue = index;
                if (PictureParamView.this.mCurrentMode != PICTURE_MODE_USER) {
                    switchToCustomizeMode();
                    return false;
                } else {
                    mPresenter.setBrightness(index);
                    return true;
                }
            }
        });

        this.mContrastSwitcher = SmallSwitcherItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mContrastSwitcher.setTitle(R.string.picture_contrast);
        this.mContrastSwitcher.setOptions(mNormalOption);
        this.mContrastSwitcher.setRecycle(false);
        this.mContrastSwitcher.setRepeated(true);
        this.mContrastSwitcher.setArrowVisible(false);
        this.mContrastSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                PictureParamView.this.mContrastValue = index;
                if (PictureParamView.this.mCurrentMode != PICTURE_MODE_USER) {
                    switchToCustomizeMode();
                    return false;
                } else {
                    mPresenter.setContrast(index);
                    return true;
                }
            }
        });

        this.mSaturationSwitcher = SmallSwitcherItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mSaturationSwitcher.setTitle(R.string.picture_saturation);
        this.mSaturationSwitcher.setOptions(mNormalOption);
        this.mSaturationSwitcher.setRecycle(false);
        this.mSaturationSwitcher.setRepeated(true);
        this.mSaturationSwitcher.setArrowVisible(false);
        this.mSaturationSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                PictureParamView.this.mSaturationValue = index;
                if (PictureParamView.this.mCurrentMode != PICTURE_MODE_USER) {
                    switchToCustomizeMode();
                    return false;
                } else {
                    mPresenter.setSaturation(index);
                    return true;
                }
            }
        });

        this.mHueSwitcher = SmallSwitcherItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mHueSwitcher.setTitle(R.string.picture_hue);
        this.mHueSwitcher.setRecycle(false);
        this.mHueSwitcher.setRepeated(true);
        this.mHueSwitcher.setArrowVisible(false);
        this.mHueSwitcher.setOptions(mNormalOption);
        this.mHueSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                PictureParamView.this.mHueValue = index;
                if (PictureParamView.this.mCurrentMode != PICTURE_MODE_USER) {
                    switchToCustomizeMode();
                    return false;
                } else {
                    mPresenter.setHue(index);
                    return true;
                }
            }
        });

        this.mSharpnessSwitcher = SmallSwitcherItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mSharpnessSwitcher.setTitle(R.string.picture_sharpness);
        this.mSharpnessSwitcher.setRecycle(false);
        this.mSharpnessSwitcher.setRepeated(true);
        this.mSharpnessSwitcher.setArrowVisible(false);
        this.mSharpnessSwitcher.setOptions(mNormalOption);
        this.mSharpnessSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                PictureParamView.this.mSharpnessValue = index;
                if (PictureParamView.this.mCurrentMode != PICTURE_MODE_USER) {
                    switchToCustomizeMode();
                    return false;
                } else {
                    mPresenter.setSharpness(index);
                    return true;
                }
            }
        });


        this.mColorTemperatureSwitcher = SmallSwitcherItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mColorTemperatureSwitcher.setTitle(R.string.picture_color_temperature);
            this.mColorTemperatureSwitcher.setArrowVisible(false);
        this.mColorTemperatureSwitcher.setOptions(mColorTemperatureOption);
        this.mColorTemperatureSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                PictureParamView.this.mColorTemperatureValue = index;
                if (PictureParamView.this.mCurrentMode != PICTURE_MODE_USER) {
                    switchToCustomizeMode();
                    return false;
                } else {
                    mPresenter.setColorTemperature(index + "");
                    return true;
                }
            }
        });

        this.mResetItem = SmallSettingItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mResetItem.setTitle(R.string.picture_reset);
        this.mResetItem.setEnabled(true, true);
        this.mResetItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.reset();
                loadParamValue();
                setUpPictureParamUI();
            }
        });
    }

    private void switchToCustomizeMode() {
        if (mPresenter != null) {
            PictureParamView.this.mCurrentMode = PICTURE_MODE_USER;
            setUpPictureParamUI();
            setUpPictureParamValue();
        }

    }

    private void setUpPictureParamUI() {
        this.mModeSwitcher.setCurrentIndex(this.mCurrentMode);
        this.mBrightnessSwitcher.setCurrentIndex(this.mBrightnessValue);
        this.mContrastSwitcher.setCurrentIndex(this.mContrastValue);
        this.mSaturationSwitcher.setCurrentIndex(this.mSaturationValue);
        this.mHueSwitcher.setCurrentIndex(this.mHueValue);
        this.mSharpnessSwitcher.setCurrentIndex(this.mSharpnessValue);
        this.mColorTemperatureSwitcher.setCurrentIndex(this.mColorTemperatureValue);

        if (this.mCurrentMode == PICTURE_MODE_USER) {
            this.mResetItem.setVisibility(VISIBLE);
        } else {
            this.mResetItem.setVisibility(GONE);
        }
    }

    private void setUpPictureParamValue() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.setMode(PictureParamView.this.mCurrentMode + "");
                mPresenter.setBrightness(PictureParamView.this.mBrightnessValue);
                mPresenter.setContrast(PictureParamView.this.mContrastValue);
                mPresenter.setSaturation(PictureParamView.this.mSaturationValue);
                mPresenter.setHue(PictureParamView.this.mHueValue);
                mPresenter.setSharpness(PictureParamView.this.mSharpnessValue);
                mPresenter.setColorTemperature(PictureParamView.this.mColorTemperatureValue + "");
            }
        });
    }

    private void loadParamValue() {
        this.mCurrentMode = mPresenter.getMode();
        this.mBrightnessValue = mPresenter.getBrightness();
        this.mContrastValue = mPresenter.getContrast();
        this.mSaturationValue = mPresenter.getSaturation();
        this.mHueValue = mPresenter.getHue();
        this.mSharpnessValue = mPresenter.getSharpness();
        this.mColorTemperatureValue = mPresenter.getColorTemperature();
    }

    @Override
    protected void initData() {
        this.mModeOption = getResources().getStringArray(R.array.setting_picture_mode);
        this.mNormalOption = new String[101];
        for (int i = 0; i < 101; i++) {
            this.mNormalOption[i] = String.valueOf(i);
        }
        this.mColorTemperatureOption = getResources().getStringArray(R.array.setting_picture_color_temperature);
    }

    @Override
    public void initFocus() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mModeSwitcher != null) {
                    mModeSwitcher.requestFocus();
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
