package com.titan.titv.settings.main;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import com.titan.titv.settings.R;
import com.titan.titv.settings.base.BaseModel;
import com.titan.titv.settings.base.BaseView;
import com.titan.titv.settings.base.OnSettingItemClickListener;
import com.titan.titv.settings.base.TVGirdLayoutManager;
import com.titan.titv.settings.general.GeneralView;
import com.titan.titv.settings.model.MainModel;
import com.titan.titv.settings.model.SettingType;
import com.titan.titv.settings.picture.PictureView;

import java.util.ArrayList;
import java.util.List;

public class MainView extends BaseView implements OnSettingItemClickListener {
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private List<BaseModel> mData;
    private int selectedPos = 0;
    private Handler mHandler;


    public MainView(Application app) {
        super(app);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_main;
    }

    @Override
    public void onCreate() {
        mHandler = new Handler();
        initData();
        mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.recycler_view);
        mAdapter = new MainAdapter(mContext, mData, this);
        mRecyclerView.setLayoutManager(new TVGirdLayoutManager(mContext, 3));
        mRecyclerView.setAdapter(mAdapter);
        initFocus();
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mData.add(new MainModel(mContext.getString(R.string.main_general), R.drawable.main_settings_general, R.drawable.main_settings_general_selected, SettingType.GENERAL));
        mData.add(new MainModel(mContext.getString(R.string.main_picture), R.drawable.main_settings_picture, R.drawable.main_settings_picture_selected, SettingType.PICTURE));
        mData.add(new MainModel(mContext.getString(R.string.main_sound), R.drawable.main_settings_sound, R.drawable.main_settings_sound_selected, SettingType.SOUND));
        mData.add(new MainModel(mContext.getString(R.string.main_network), R.drawable.main_settings_network, R.drawable.main_settings_network_selected, SettingType.NETWORK));
        mData.add(new MainModel(mContext.getString(R.string.main_security), R.drawable.main_settings_security, R.drawable.main_settings_security_selected, SettingType.SECURITY));
        mData.add(new MainModel(mContext.getString(R.string.main_system), R.drawable.main_settings_system, R.drawable.main_settings_system_selected, SettingType.SYSTEM));
    }

    @Override
    public void initFocus() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRecyclerView.getChildAt(selectedPos) == null) {
                    initFocus();
                } else {
                    mRecyclerView.getChildAt(selectedPos).requestFocusFromTouch();
                }
            }
        }, 10);
    }

    @Override
    public void onResume() {
        initFocus();
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(null);
    }

    @Override
    public void onDestroy() {
        mHandler = null;
    }

    @Override
    protected void finish() {
        super.finish();
        hideSetting();
    }

    @Override
    public void onSettingItemClick(SettingType settingType, String value, int pos) {
        selectedPos = pos;
        switch (settingType) {
            case PERSONAL:
//                  startView(new PersonalView(mApp));
                break;
            case GENERAL:
                 startView(new GeneralView(mApp));
                break;
            case PICTURE:
                startView(new PictureView(mApp));
                break;
            case SOUND:
//                 startView(new SoundView(mApp));
                break;
            case NETWORK:
//                 startView(new NetWorkView(mApp));
                break;
            case SOURCE:
                startSourceview();
                break;
            case SECURITY:
//                startView(new SecurityView(mApp));
                break;
            case SYSTEM:
//                startView(new SystemView(mApp));
                break;
            default:
                break;
        }
    }


    private void startSourceview() {
        try {
            Intent sendIntent = new Intent();
            ComponentName componentName = new ComponentName("com.socionext.sourceview", "com.socionext.sourceview.MainActivity");
            sendIntent.setComponent(componentName);
            mApp.startActivity(sendIntent);
            finish();
        } catch (Exception e) {
            //防止未安装source view
        }
    }
}
