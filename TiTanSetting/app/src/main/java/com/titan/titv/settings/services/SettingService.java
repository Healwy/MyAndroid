package com.titan.titv.settings.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;

import com.titan.titv.settings.manager.SettingUiManager;
import com.titan.titv.settings.utils.LogUtils;

public class SettingService extends Service {
    public static final String START_SERVICE_COMMAND = "startServiceCommands";
    static final int COMMAND_NONE = -1;
    public static final int COMMAND_SHOW_SETTINGS = 0;
    public static final int COMMAND_DISMISS_SETTINGS = 1;
    public static final int COMMAND_CEC_SENDKEY = 2;
    public static final int COMMAND_SHOW_NETWORK = 5;
	public static final int COMMAND_SHOW_PC_SETTINGS = 3;
	public static final int COMMAND_SHOW_PICTURE = 4;
	public static final int COMMAND_SHOW_SOUND = 6;
	public static final int COMMAND_SHOW_CHANNEL_LIST = 7;
    public static final int COMMAND_SHOW_MANUAL_SCAN = 8;
    public static final int COMMAND_SHOW_GENERAL = 9;
    public static final int COMMAND_SHOW_DISPLAYMODE = 10;
    public static final int COMMAND_SHOW_SHIPPING_RESET = 11;
    public static final int COMMAND_SET_SLEEP_TIMER = 12;
    public static final int COMMAND_AUDIO_ONLY = 13;
    public static final int COMMAND_SHOW_PICTUREPARAM = 14;
    public static final int COMMAND_SHOW_SOUNDPARAM = 15;


    private static SettingService INSTANCE;
    /**
     * is need db mapping(has scanned)
     */

    public static SettingService getInstance() {
        if (INSTANCE == null) {
            return new SettingService();
        }
        return INSTANCE;
    }

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.d("");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d();
        INSTANCE = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("healer");
        Log.d("service", "onStartCommand: ");
        if (intent == null) {
            return START_STICKY;
        }
        int command = COMMAND_NONE;

        if (intent != null) {
            command = intent.getIntExtra(START_SERVICE_COMMAND, COMMAND_NONE);
        }
        switch (command) {
            case COMMAND_SHOW_SETTINGS:
                show();
                break;
            case COMMAND_DISMISS_SETTINGS:
                dismiss();
                break;
			case COMMAND_SHOW_PICTUREPARAM:
				showPictureParam();
				break;
            case COMMAND_SHOW_SOUNDPARAM:
                showSoundParam();
                break;
            default:
                break;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        LogUtils.d("SettingService onDestroy");
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void show() {
        LogUtils.d();
        SettingUiManager.getInstance(getApplication()).showSetting();
    }

	private void showPictureParam() {
        LogUtils.d();
        SettingUiManager.getInstance(getApplication()).showPicturesParam();
    }

    private void showSoundParam() {
        LogUtils.d();
        SettingUiManager.getInstance(getApplication()).showSoundsParam();
    }

    /**
     * dismiss setting UI
     */
    private void dismiss() {
        LogUtils.d();
        SettingUiManager.getInstance(getApplication()).hideSetting();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
