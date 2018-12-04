package com.titan.titv.settings.general;

import android.app.Application;
import android.content.Context;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.TextView;

import com.titan.titv.settings.R;
import com.titan.titv.settings.base.BaseView;

public class GeneralView extends BaseView {

    private int[] mGeneralItemList;
    private int[] mSaveEnergyItemList;
    private TextView mTitleFirst;

    private static final int LANGUAGE_INDEX = 0;
    private static final int IME_INDEX = 1;
    private static final int NO_SIGNAL_POWEROFF_INDEX = 2;
    private static final int NOFIFY_MANAGEMENT_INDEX = 3;
    private static final int SCREENSAVER_INDEX = 4;
    private static final int SCREENSAVER_STYLE_INDEX = 5;

    public GeneralView(Application app) {
        super(app);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_common_categoty;
    }

    @Override
    public void onCreate() {
        this.mGeneralItemList = new int[]{LANGUAGE_INDEX, IME_INDEX,};
        this.mSaveEnergyItemList = new int[]{NO_SIGNAL_POWEROFF_INDEX, NOFIFY_MANAGEMENT_INDEX, SCREENSAVER_INDEX, SCREENSAVER_STYLE_INDEX};
        this.mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.main_general);
        this.setBackgroundColor(getResources().getColor(R.color.settings_bg_color));

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void initData() {

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
