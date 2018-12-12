package com.titan.settings.network.hotspot;

import android.app.Application;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.titan.settings.R;
import com.titan.settings.base.BaseView;
import com.titan.settings.widgets.SwitcherItem;

public class HotspotView extends BaseView implements View.OnClickListener {

    private TextView mTitleFirst;
    private RelativeLayout mOpenLayout;
    private RelativeLayout mKeyMgmtLayout;
    private LinearLayout mFrequencyLayout;
    private LinearLayout mSettingsLayout;
    private RelativeLayout mPasswdLayout;

    private SwitcherItem mOpenItem;
    private SwitcherItem mkeyMgmtItem;
    private SwitcherItem mFrequencyItem;

    private EditText mSsid;
    private EditText mPasswd;
    private Button mSaveBtn;
    private Button mCancelBtn;

    private int mOpenValue;
    private String mSSIDValue;
    private String mPasswdValue;
    private int mKeyMgmtValue;
    private int mFrequencyValue;

    private static final int  HOTSPOT_CLOSE_INDEX = 0;
    private static final int  HOTSPOT_OPEN_INDEX = 1;
    private static final int  KEYMGMT_NONE_INDEX = 0;
    private static final int  KEYMGMT_WPA2_INDEX = 1;
    private static final int  FREQUENCY_2_4_INDEX = 0;
    private static final int  FREQUENCY_5_INDEX = 1;

    private static final String DEFAULT_SSID_NAME = "DEFAULT";
    private static final String DEFAULT_PASSWD_NAME = "12345678";

    public HotspotView(Application app) {
        super(app);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hotspot_save:
                finish();
                break;
            case R.id.hotspot_cancel:
                finish();
                break;
        }
    }

    @Override
    protected int getViewResId() {
        return  R.layout.view_hotspot;
    }

    @Override
    public void onCreate() {
        this.mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.network_wireless_hotspot);


        this.mOpenLayout = (RelativeLayout)mContentView.findViewById(R.id.hotspot_open);
        this.mOpenItem = SwitcherItem.createItem(mContext,mOpenLayout);
        this.mOpenItem.setTitle(R.string.network_wireless_hotspot);
        this.mOpenItem.setOptions(getResources().getStringArray(R.array.setting_normal_list));

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mOpenItem.getLayoutParams();
        lp.height = (int)(getResources().getDimension(R.dimen.wifi_option_height));
        this.mOpenItem.setLayoutParams(lp);
        this.mOpenItem.setStateListAnimator(null);
        this.mOpenItem.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                mOpenValue = index;
                setUpHotspotUI();
                return true;
            }
        });



        this.mKeyMgmtLayout = (RelativeLayout)mContentView.findViewById(R.id.hotspot_KeyMgmt);
        this.mkeyMgmtItem = SwitcherItem.createItem(mContext,mKeyMgmtLayout);
        this.mkeyMgmtItem.setTitle(R.string.network_hotspot_keymgmt);
        this.mkeyMgmtItem.setOptions(getResources().getStringArray(R.array.network_hotspot_keymgmt));
        this.mkeyMgmtItem.setLayoutParams(lp);
        this.mkeyMgmtItem.setStateListAnimator(null);
        this.mkeyMgmtItem.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                mKeyMgmtValue = index;
                setUpHotspotUI();
                return true;
            }
        });
        this.mkeyMgmtItem.setNextFocusUpId(R.id.hotspot_ssid);

        this.mSsid = (EditText)mContentView.findViewById(R.id.hotspot_ssid);
        this.mPasswd = (EditText)mContentView.findViewById(R.id.hotspot_passwd);

        this.mSettingsLayout = (LinearLayout) mContentView.findViewById(R.id.hotspot_settings);
        this.mPasswdLayout = (RelativeLayout) mContentView.findViewById(R.id.hotspot_passwd_layout);
        this.mSaveBtn = (Button)mContentView.findViewById(R.id.hotspot_save);
        this.mSaveBtn.setOnClickListener(this);
        this.mCancelBtn = (Button)mContentView.findViewById(R.id.hotspot_cancel);
        this.mCancelBtn.setOnClickListener(this);

        initViewValue();
        setUpHotspotUI();
        initFocus();
    }

    private void setUpSettingsAllUI() {

        if(this.mOpenValue == HOTSPOT_OPEN_INDEX){
            this.mSettingsLayout.setVisibility(VISIBLE);
        }else{
            this.mSettingsLayout.setVisibility(GONE);
        }
    }

    private void setUpPasswdUI() {
        // if(this.mKeyMgmtValue == KEYMGMT_NONE_INDEX){
        //     this.mPasswdLayout.setVisibility(GONE);
        // }else{
        //     this.mPasswdLayout.setVisibility(VISIBLE);
        // }
    }


    private void initViewValue(){
        this.mOpenValue = HOTSPOT_CLOSE_INDEX;
        this.mSSIDValue = DEFAULT_SSID_NAME;
        this.mPasswdValue = DEFAULT_PASSWD_NAME;
        this.mKeyMgmtValue = KEYMGMT_NONE_INDEX;
        this.mFrequencyValue = FREQUENCY_2_4_INDEX;
    }

    private void setUpHotspotUI(){
        this.mOpenItem.setCurrentIndex(this.mOpenValue);
        this.mSsid.setText(this.mSSIDValue);
        this.mkeyMgmtItem.setCurrentIndex(this.mKeyMgmtValue);
        this.mPasswd.setText(this.mPasswdValue);
       // this.mFrequencyItem.setCurrentIndex(this.mFrequencyValue);
        setUpSettingsAllUI();
        setUpPasswdUI();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initFocus() {
        this.mOpenItem.requestFocus();
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
