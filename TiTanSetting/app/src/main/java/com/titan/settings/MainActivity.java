package com.titan.settings;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.titan.settings.manager.SettingUiManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingUiManager.getInstance(getApplication()).showSetting();
        finish();
    }
}
