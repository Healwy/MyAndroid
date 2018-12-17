package com.titan.settings.sound;

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

import static com.titan.platform.adapter.TvAudioManagerAdapter.SOUND_MODE_USER;

public class SoundEffectView extends BaseView {

    private final SoundPresenter mPresenter;
    private TextView mTitleFirst;
    private String[] mModeOption;
    private String[] mOpenOptioin;
    private String[] mNormalOption;
    private SettingCategory mModeCategory;
    private SettingCategory mCustomizeCategory;
    private ViewGroup mCustomizeCategoryHolder;
    private int mCurrentSoundMode;
    private SwitcherItem mModeSwitcher;
    private SmallSwitcherItem mSoundTrebleSwitcher;
    private int mSoundTrebleValue;
    private SmallSwitcherItem mSoundBassSwitcher;
    private int mSoundBassValue;
    private SmallSwitcherItem mSurroundSoundSwitcher;
    private int mSurroundSoundValue;
    private SmallSwitcherItem mBalanceSwitcher;
    private int mBalanceValue;
    private SmallSettingItem mResetSwitcher;

    public SoundEffectView(Application app) {
        super(app);
        mPresenter = new SoundPresenter(mContext);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_common_second_category;
    }

    @Override
    public void onCreate() {
        this.setBackgroundColor(getResources().getColor(R.color.transparent));
        this.mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.sound_effect);
        initData();
        loadViews();
        loadParamValue();
        setUpSoundEffectUI();
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
        this.mModeSwitcher.setTitle(R.string.sound_mode);
        this.mModeSwitcher.setOptions(mModeOption);
        this.mModeSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                SoundEffectView.this.mCurrentSoundMode = index;
                mPresenter.setSoundMode(index + "");
                loadParamValue();
                setUpSoundEffectUI();
                return true;
            }
        });
        this.mSoundTrebleSwitcher = SmallSwitcherItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mSoundTrebleSwitcher.setTitle(R.string.sound_treble);
        this.mSoundTrebleSwitcher.setRecycle(false);
        this.mSoundTrebleSwitcher.setRepeated(true);
        this.mSoundTrebleSwitcher.setArrowVisible(false);
        this.mSoundTrebleSwitcher.setOptions(mNormalOption);
        this.mSoundTrebleSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                SoundEffectView.this.mSoundTrebleValue = index;
                if (SOUND_MODE_USER != SoundEffectView.this.mCurrentSoundMode) {
                    switchToCustomizeMode();
                    return false;
                } else {
                    mPresenter.setTreble(index);
                }
                return true;
            }
        });

        this.mSoundBassSwitcher = SmallSwitcherItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mSoundBassSwitcher.setTitle(R.string.sound_bass);
        this.mSoundBassSwitcher.setRecycle(false);
        this.mSoundBassSwitcher.setRepeated(true);
        this.mSoundBassSwitcher.setArrowVisible(false);
        this.mSoundBassSwitcher.setOptions(mNormalOption);
        this.mSoundBassSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                SoundEffectView.this.mSoundBassValue = index;
                if (SOUND_MODE_USER != SoundEffectView.this.mCurrentSoundMode) {
                    switchToCustomizeMode();
                    return false;
                } else {
                    mPresenter.setBass(index);
                }
                return true;
            }
        });

        this.mSurroundSoundSwitcher = SmallSwitcherItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mSurroundSoundSwitcher.setTitle(R.string.sound_surround);
        this.mSurroundSoundSwitcher.setArrowVisible(false);
        this.mSurroundSoundSwitcher.setOptions(mOpenOptioin);
        this.mSurroundSoundSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                SoundEffectView.this.mSurroundSoundValue = index;
                if (SOUND_MODE_USER != SoundEffectView.this.mCurrentSoundMode) {
                    switchToCustomizeMode();
                    return false;
                } else {
                    mPresenter.setSurround(index);
                }
                return true;
            }
        });

        this.mBalanceSwitcher = SmallSwitcherItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mBalanceSwitcher.setTitle(R.string.sound_balance);
        this.mBalanceSwitcher.setRecycle(false);
        this.mBalanceSwitcher.setRepeated(true);
        this.mBalanceSwitcher.setArrowVisible(false);
        this.mBalanceSwitcher.setOptions(mNormalOption);
        this.mBalanceSwitcher.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                SoundEffectView.this.mBalanceValue = index;
                if (SOUND_MODE_USER != SoundEffectView.this.mCurrentSoundMode) {
                    switchToCustomizeMode();
                    return false;
                } else {
                    mPresenter.setBalance(index);
                }
                return true;
            }
        });

        this.mResetSwitcher = SmallSettingItem.createItem(this.mContext, this.mCustomizeCategory);
        this.mResetSwitcher.setTitle(R.string.sound_reset);
        this.mResetSwitcher.setEnabled(true, true);
        this.mResetSwitcher.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.reset();
                loadParamValue();
                setUpSoundEffectUI();
            }
        });

    }

    private void switchToCustomizeMode() {
        if (mPresenter != null) {
            SoundEffectView.this.mCurrentSoundMode = SOUND_MODE_USER;
            setUpSoundEffectUI();
            setUpSoundEffectValue();
        }

    }

    private void setUpSoundEffectValue() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.setSoundMode(SoundEffectView.this.mCurrentSoundMode + "");
                mPresenter.setSurround(SoundEffectView.this.mSurroundSoundValue);
                mPresenter.setBalance(SoundEffectView.this.mBalanceValue);
                mPresenter.setTreble(SoundEffectView.this.mSoundTrebleValue);
                mPresenter.setBass(SoundEffectView.this.mSoundBassValue);
            }
        });
    }

    private void setUpSoundEffectUI() {
        this.mModeSwitcher.setCurrentIndex(this.mCurrentSoundMode);
        this.mSurroundSoundSwitcher.setCurrentIndex(this.mSurroundSoundValue);
        this.mBalanceSwitcher.setCurrentIndex(this.mBalanceValue);
        this.mSoundTrebleSwitcher.setCurrentIndex(this.mSoundTrebleValue);
        this.mSoundBassSwitcher.setCurrentIndex(this.mSoundBassValue);

        if (this.mCurrentSoundMode == SOUND_MODE_USER) {
            this.mResetSwitcher.setVisibility(VISIBLE);
        } else {
            this.mResetSwitcher.setVisibility(GONE);
        }
    }

    private void loadParamValue() {
        this.mCurrentSoundMode = mPresenter.getSoundMode();
        this.mSurroundSoundValue = mPresenter.getSurround();
        this.mBalanceValue = mPresenter.getBalance();
        this.mSoundTrebleValue = mPresenter.getTreble();
        this.mSoundBassValue = mPresenter.getBass();
    }

    @Override
    protected void initData() {
        this.mModeOption = getResources().getStringArray(R.array.setting_sound_mode);
        this.mOpenOptioin = getResources().getStringArray(R.array.setting_normal_list);
        this.mNormalOption = new String[101];
        for (int i = 0; i < 101; i++) {
            this.mNormalOption[i] = String.valueOf(i);
        }


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
