package com.titan.titv.settings.manager;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.titan.titv.settings.base.BaseView;
import com.titan.titv.settings.main.MainView;
import com.titan.titv.settings.utils.LogUtils;

public class SettingUiManager {
    private final Application mApp;
    public static SettingUiManager mUiManager;
    private final WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;
    private FrameLayout mDecorView;
    private boolean isShow;

    public static SettingUiManager getInstance(Application app) {
        LogUtils.d("Function Start");
        if (mUiManager == null) {
            mUiManager = new SettingUiManager(app);
        }
        LogUtils.d("Function End");
        return mUiManager;
    }

    private SettingUiManager(Application app) {
        LogUtils.d("Function Start");
        mApp = app;
        mWindowManager = (WindowManager) app.getSystemService(Context.WINDOW_SERVICE);
        initParams();
        LogUtils.d("Function End");
    }

    private void initParams() {
        LogUtils.d("Function Start");
        mParams = new WindowManager.LayoutParams();
            mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        mParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        LogUtils.d("Function End");
    }

    public void showSetting() {
        LogUtils.d("Function Start");
        LogUtils.d("isShow: " + isShow);
        if (isShow) return;
        isShow = true;
        mDecorView = new FrameLayout(mApp);
        MainView view = new MainView(mApp);
        mDecorView.addView(view);
        view.onCreate();
        mWindowManager.addView(mDecorView, mParams);
        LogUtils.d("Function End");
    }


    public void hideSetting() {
        LogUtils.d("Function Start");
        isShow = false;
        mDecorView.removeAllViews();
        mWindowManager.removeViewImmediate(mDecorView);
        mDecorView = null;
        System.gc();
        LogUtils.d("Function End");
    }


    public void addView(BaseView view) {
        LogUtils.d("Function Start");
        if (mDecorView.getChildCount() > 0) {
            (mDecorView.getChildAt(mDecorView.getChildCount() - 1)).setVisibility(View.INVISIBLE);
            ((BaseView) mDecorView.getChildAt(mDecorView.getChildCount() - 1)).onPause();
        }
        mDecorView.addView(view);
        view.onCreate();
        LogUtils.d("Function End");
    }

    public void removeView(BaseView view) {
        LogUtils.d("Function Start");
        view.onPause();
        view.onDestroy();
        view.removeAllViews();
        mDecorView.removeView(view);
        System.gc();
        if (mDecorView != null && mDecorView.getChildCount() > 0) {
            (mDecorView.getChildAt(mDecorView.getChildCount() - 1)).setVisibility(View.VISIBLE);
            ((BaseView) mDecorView.getChildAt(mDecorView.getChildCount() - 1)).onResume();
        }
        LogUtils.d("Function End");
    }


    public void changeLanguage() {
        LogUtils.d("Function Start");
        if (mDecorView == null) return;
        for (int i = 0; i < mDecorView.getChildCount(); i++) {
            ((BaseView) mDecorView.getChildAt(i)).onCreate();
        }
        LogUtils.d("Function End");
    }

    public void showPicturesParam() {
        LogUtils.d("Function Start");
        LogUtils.d("isShow: " + isShow);
        if (isShow) return;
        isShow = true;
        mDecorView = new FrameLayout(mApp);
      //  PictureParamView view = new PictureParamView(mApp);
       // mDecorView.addView(view);
       // view.onCreate();
        mWindowManager.addView(mDecorView, mParams);
        LogUtils.d("Function End");
    }

    public void showSoundsParam() {
        LogUtils.d("Function Start");
        LogUtils.d("isShow: " + isShow);
        if (isShow) return;
        isShow = true;
        mDecorView = new FrameLayout(mApp);
//        SoundEffectView view = new SoundEffectView(mApp);
////        mDecorView.addView(view);
////        view.onCreate();
        mWindowManager.addView(mDecorView, mParams);
        LogUtils.d("Function End");
    }

}
