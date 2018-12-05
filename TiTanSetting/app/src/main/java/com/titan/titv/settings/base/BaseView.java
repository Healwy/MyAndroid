package com.titan.titv.settings.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.titan.titv.settings.R;
import com.titan.titv.settings.manager.SettingUiManager;
import com.titan.titv.settings.utils.LogUtils;

/**
 * base view
 */
abstract public class BaseView extends RelativeLayout {
    protected Application mApp;
    protected Context mContext;
    protected View mContentView;
    protected Resources mResources;

    public BaseView(Application app) {
        this(app, null);
    }

    public BaseView(Application app, AttributeSet attrs) {
        this(app, attrs, 0);

    }

    public BaseView(Application app, AttributeSet attrs, int defStyleAttr) {
        super(app, attrs, defStyleAttr);
        mApp = app;
        mContext = app;
        mResources = app.getResources();
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(getViewResId(), this, true);
        this.setBackgroundColor(getResources().getColor(R.color.settings_bg_color));
    }

    abstract protected int getViewResId();

    abstract public void onCreate();

    abstract protected void initData();

    abstract public void onResume();

    abstract public void onPause();

    abstract public void onDestroy();


    protected void startView(BaseView view) {
        LogUtils.d("Function Start");
        SettingUiManager.getInstance(mApp).addView(view);
        LogUtils.d("Function End");
    }

    protected void finish() {
        LogUtils.d("Function Start");
        SettingUiManager.getInstance(mApp).removeView(this);
        LogUtils.d("Function End");
    }

    protected void hideSetting() {
        LogUtils.d("Function Start");
        SettingUiManager.getInstance(mApp).hideSetting();
        LogUtils.d("Function End");
    }

    protected void changeLanguage() {
        LogUtils.d("Function Start");
        SettingUiManager.getInstance(mApp).changeLanguage();
        LogUtils.d("Function End");
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
