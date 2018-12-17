package com.titan.settings.network.wireless;

import android.app.Application;
import android.os.HandlerThread;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.titan.settings.R;
import com.titan.settings.base.BaseView;
import com.titan.settings.utils.LogUtils;

// TODO 2018.12.17 临时注释
//import android.net.wifi.WifiManager;
//import android.net.wifi.WifiManager.ActionListener;

public class WirelessView  extends BaseView {
    private static final String TAG = "WirelessView";

    private TextView mTitleFirst;
    private LinearLayout mWifiConLayout;
    private HandlerThread mBgThread;
    // TODO 2018.12.17 临时注释
//    private ActionListener mConnectListener;

    public WirelessView(Application app) {
        super(app);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_wireless;
    }

    @Override
    public void onCreate() {
        mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        mTitleFirst.setText(R.string.wireless_title);
        mWifiConLayout = (LinearLayout)mContentView.findViewById(R.id.wifi_con);

        mBgThread = new HandlerThread(TAG, android.os.Process.THREAD_PRIORITY_BACKGROUND);
        mBgThread.start();
// TODO 2018.12.17 临时注释
//        mConnectListener = new ActionListener() {
//            @Override
//            public void onSuccess() {
//                LogUtils.d("Function Start");
//                LogUtils.d("Function End");
//            }
//
//            @Override
//            public void onFailure(int reason) {
//                LogUtils.d("Function Start");
//                LogUtils.d("Function End");
//            }
//        };

        initView();

        //TODO 临时注释
//        mWifiTracker = new WifiTracker(mContext, this, mBgThread.getLooper(), true, true, false);
//        mWifiManager = mWifiTracker.getManager();
//        mWifiTracker.startTracking();

    }

    private void initView() {

    }

    @Override
    protected void initData() {

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
