package com.titan.settings.network.ethernet;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.titan.settings.R;
import com.titan.settings.base.BaseView;
import com.titan.settings.utils.LogUtils;
import com.titan.settings.utils.ToastUtils;
import com.titan.settings.widgets.SwitcherItem;
import com.titan.settings.widgets.TvButton;

import android.net.EthernetManager;
import android.net.IpConfiguration;
import android.net.IpConfiguration.IpAssignment;
import android.net.ConnectivityManager;
import android.net.EthernetManager;
import android.net.IpConfiguration;
import android.net.IpConfiguration.IpAssignment;
import android.net.IpConfiguration.ProxySettings;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.NetworkUtils;
import android.net.ProxyInfo;
import android.net.RouteInfo;
import android.net.StaticIpConfiguration;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.net.InetAddress;
import java.util.regex.Pattern;

public class EthernetView extends BaseView implements View.OnClickListener, EthernetManager.Listener {

    private TextView mTitleFirst;
    private LinearLayout mManualConLayout;
    private SwitcherItem mManualItem;

    private TextView mIPtv;
    private TextView mMasktv;
    private TextView mGatewaytv;
    private TextView mDnstv;
    private EditText mIP;
    private EditText mMask;
    private EditText mGateway;
    private EditText mDns;
    private ConnectivityManager mConnManager;
    private EthernetManager mEthernetManager;
    private TvButton mBtnSave;
    private TvButton mBtnCancel;
    private boolean isEthernetAvailable;
    private static final int DHCP_INDEX = 0;
    private static final int MANUAL_INDEX = 1;
    private int mManualItemValue;

    private IpAssignment mIpAssignment = IpAssignment.DHCP;
    private ProxySettings mProxySettings = ProxySettings.NONE;

    public EthernetView(Application app) {
        super(app);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_ethernet;
    }

    @Override
    public void onCreate() {
        mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        mTitleFirst.setText(R.string.network_ethernet_title);
        mManualConLayout = (LinearLayout) mContentView.findViewById(R.id.manual_con_select);
        mManualItem = SwitcherItem.createItem(mContext, this.mManualConLayout);
        mManualItem.setTitle(R.string.wired_conn_setting);
        mManualItem.setOptions(getResources().getStringArray(R.array.setting_normal_list));
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.mManualItem.getLayoutParams();
        lp.height = (int) (getResources().getDimension(R.dimen.wifi_option_height));
        mManualItem.setLayoutParams(lp);
        mManualItem.setOnSwitchListener(new SwitcherItem.OnSwitchListener() {
            public boolean onSwitchTo(int index) {
                if (index == 0) {
                    mIpAssignment = IpAssignment.DHCP;
                    mManualItemValue = DHCP_INDEX;
                    handle_saveconf();
                    setUpNetInfoUI(false);
                } else {
                    mIpAssignment = IpAssignment.STATIC;
                    mManualItemValue = MANUAL_INDEX;
                    setUpNetInfoUI(true);
                }
                return true;
            }
        });
        this.mManualItem.setStateListAnimator(null);

        mIP = (EditText) mContentView.findViewById(R.id.net_ip_cnt);
        mMask = (EditText) mContentView.findViewById(R.id.net_subnet_cnt);
        mGateway = (EditText) mContentView.findViewById(R.id.net_gateway_cnt);
        mDns = (EditText) mContentView.findViewById(R.id.net_dns_cnt);

        mBtnSave = (TvButton) mContentView.findViewById(R.id.wired_conn);
        mBtnCancel = (TvButton) mContentView.findViewById(R.id.wired_cancel);

        mBtnSave.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        initData();
        updateUI();
        initFocus();

    }

    private void handle_saveconf() {
        ProxyInfo proxyInfo = null;
        StaticIpConfiguration staticIpConfiguration = null;

        if (mManualItemValue == DHCP_INDEX) {
            LogUtils.d("mode dhcp");
        } else {
            LogUtils.d("mode static");
            if (TextUtils.isEmpty(mIP.getText().toString())
                    || TextUtils.isEmpty(mMask.getText().toString())
                    || TextUtils.isEmpty(mGateway.getText().toString())
                    || TextUtils.isEmpty(mDns.getText().toString())) {
                LogUtils.e("static settings is empty");
                ToastUtils.show(mContext, mContext.getString(R.string.ethernet_static_null));
                return;
            }
            if (!isIpAddress(mIP.getText().toString())
                    && !isIpAddress(mGateway.getText().toString())
                    && !isIpAddress(mDns.getText().toString())) {
                LogUtils.e("static setting format error");
                ToastUtils.show(mContext, mContext.getString(R.string.ethernet_static_error));
                return;
            }
            staticIpConfiguration = new StaticIpConfiguration();
            int result = validateIpConfigFields(staticIpConfiguration);
            if (result != 0) {
                LogUtils.e("validateIp error, id is " + result);
                ToastUtils.show(mContext, mContext.getString(R.string.ethernet_static_error));
                return;
            }
        }
        LogUtils.d("setConfiguration mIpAssignment = " + mIpAssignment);
        LogUtils.d("setConfiguration mProxySettings = " + mProxySettings);
        LogUtils.d("setConfiguration staticIpConfiguration = " + staticIpConfiguration);
        mEthernetManager.setConfiguration(new IpConfiguration(mIpAssignment, mProxySettings,
                staticIpConfiguration, proxyInfo));
        ToastUtils.show(mContext, mContext.getString(R.string.ethernet_save_success));
        updateUI();

    }

    private int validateIpConfigFields(StaticIpConfiguration staticIpConfiguration) {
        String ipAddr = mIP.getText().toString();
        Inet4Address inetAddr = getIPv4Address(ipAddr);
        if (inetAddr == null) {
            return 2;
        }

        String netmask = mMask.getText().toString();
        if (TextUtils.isEmpty(netmask))
            return 11;
        Inet4Address netmas = getIPv4Address(netmask);
        if (netmas == null) {
            return 12;
        }
        int nmask = NetworkUtils.inetAddressToInt(netmas);
        int networkPrefixLength = -1;
        try {
            networkPrefixLength = NetworkUtils.netmaskIntToPrefixLength(nmask);
            if (networkPrefixLength < 0 || networkPrefixLength > 32) {
                return 3;
            }
            /// TODO 2018.12.17 临时注释
//            staticIpConfiguration.ipAddress = new LinkAddress(inetAddr, networkPrefixLength);
        } catch (NumberFormatException e) {
            // Set the hint as default after user types in ip address
        }

        String gateway = mGateway.getText().toString();
        InetAddress gatewayAddr = getIPv4Address(gateway);
        if (gatewayAddr == null) {
            return 4;
        }
        staticIpConfiguration.gateway = gatewayAddr;

        String dns = mDns.getText().toString();
        InetAddress dnsAddr = null;
        dnsAddr = getIPv4Address(dns);
        if (dnsAddr == null) {
            return 5;
        }
        staticIpConfiguration.dnsServers.add(dnsAddr);
        return 0;
    }

    private void setUpNetInfoUI(boolean isStatic) {
        mIP.setEnabled(isStatic);
        mMask.setEnabled(isStatic);
        mGateway.setEnabled(isStatic);
        mDns.setEnabled(isStatic);

        mIP.setFocusable(isStatic);
        mMask.setFocusable(isStatic);
        mGateway.setFocusable(isStatic);
        mDns.setFocusable(isStatic);

        if (isStatic) {
            mBtnSave.setVisibility(VISIBLE);
            mBtnCancel.setVisibility(VISIBLE);
            int enableColor = mContext.getResources().getColor(R.color.common_text_color);
            mIPtv.setTextColor(enableColor);
            mMasktv.setTextColor(enableColor);
            mGatewaytv.setTextColor(enableColor);
            mDnstv.setTextColor(enableColor);

            mIP.setTextColor(enableColor);
            mMask.setTextColor(enableColor);
            mGateway.setTextColor(enableColor);
            mDns.setTextColor(enableColor);

        } else {
            mBtnSave.setVisibility(GONE);
            mBtnCancel.setVisibility(GONE);
            int disableColor = mContext.getResources().getColor(R.color.common_text_color_15);
            mIPtv.setTextColor(disableColor);
            mMasktv.setTextColor(disableColor);
            mGatewaytv.setTextColor(disableColor);
            mDnstv.setTextColor(disableColor);

            mIP.setTextColor(disableColor);
            mMask.setTextColor(disableColor);
            mGateway.setTextColor(disableColor);
            mDns.setTextColor(disableColor);
        }
    }

    @Override
    protected void initData() {
        // TODO 2018.12.17 临时注释
//        mEthernetManager = (EthernetManager) mContext.getSystemService(Context.ETHERNET_SERVICE);
        mConnManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mEthernetManager != null) {
            isEthernetAvailable = mEthernetManager.isAvailable();
        }

    }

    @Override
    public void initFocus() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mManualItem != null) {
                    mManualItem.requestFocus();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wired_conn:
                handle_saveconf();
                break;
            case R.id.wired_cancel:
                finish();
                break;
        }
    }

    @Override
    public void onAvailabilityChanged(boolean isAvailable) {
        isEthernetAvailable = isAvailable;
        LogUtils.d("isAvailable: " + isAvailable);
        updateUI();
    }

    private void updateUI() {
        // TODO 2018.12.17 临时注释
//        IpConfiguration ipinfo = mEthernetManager.getConfiguration();
//        if (isEthernetAvailable && ipinfo != null) {
//            mIpAssignment = ipinfo.getIpAssignment();
//            if (mIpAssignment == IpAssignment.DHCP) {
//                LogUtils.d("IpAssignment.DHCP");
//                mManualItem.setCurrentIndex(DHCP_INDEX);
//                mManualItemValue = DHCP_INDEX;
//                setUpNetInfoUI(false);
//                mMask.setText(formatMask());
//                if (mConnManager != null) {
//                    LinkProperties lp = mConnManager.getLinkProperties(ConnectivityManager.TYPE_ETHERNET);
//                    if (lp != null) {
//                        LogUtils.d(formatIpAddresses(lp));
//                        mIP.setText(formatIpAddresses(lp));
//                        mDns.setText(formatDns(lp));
//                        mGateway.setText(formatGateway(lp));
//                    }
//                }
//            } else if (mIpAssignment == IpAssignment.STATIC) {
//                LogUtils.d("IpAssignment.STATIC");
//                mManualItem.setCurrentIndex(MANUAL_INDEX);
//                mManualItemValue = MANUAL_INDEX;
//                setUpNetInfoUI(true);
//                StaticIpConfiguration staticConfig = ipinfo.getStaticIpConfiguration();
//                if (staticConfig != null) {
//                    if (staticConfig.ipAddress != null) {
//                        mIP.setText(staticConfig.ipAddress.getAddress().getHostAddress());
////                        mMask.setText(calcMaskByPrefixLength(staticConfig.ipAddress.getNetworkPrefixLength()));
//                    }
//                    if (staticConfig.gateway != null) {
//                        mGateway.setText(staticConfig.gateway.getHostAddress());
//                    }
//                    Iterator<InetAddress> dnsIterator = staticConfig.dnsServers.iterator();
//                    if (dnsIterator.hasNext()) {
//                        mDns.setText(dnsIterator.next().getHostAddress());
//                    }
//                }
//            }
//            mProxySettings = ipinfo.getProxySettings();
//        } else {
//            setUpNetInfoUI(false);
//            mManualItem.setEnabled(false);
//        }
    }

    private Inet4Address getIPv4Address(String text) {
        try {
            return (Inet4Address) NetworkUtils.numericToInetAddress(text);
        } catch (IllegalArgumentException | ClassCastException e) {
            return null;
        }
    }

    private boolean isIpAddress(String value) {
        int start = 0;
        int end = value.indexOf('.');
        int numBlocks = 0;

        while (start < value.length()) {
            if (end == -1) {
                end = value.length();
            }

            try {
                int block = Integer.parseInt(value.substring(start, end));
                if ((block > 255) || (block < 0)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            numBlocks++;

            start = end + 1;
            end = value.indexOf('.', start);
        }
        return numBlocks == 4;
    }

    private String calcMaskByPrefixLength(int length) {
        int mask = -1 << (32 - length);
        int partsNum = 4;
        int bitsOfPart = 8;
        int maskParts[] = new int[partsNum];
        int selector = 0x000000ff;

        for (int i = 0; i < maskParts.length; i++) {
            int pos = maskParts.length - 1 - i;
            maskParts[pos] = (mask >> (i * bitsOfPart)) & selector;
        }

        String result = "";
        result = result + maskParts[0];
        for (int i = 1; i < maskParts.length; i++) {
            result = result + "." + maskParts[i];
        }
        return result;
    }

    private String formatIpAddresses(LinkProperties prop) {
        if (prop == null) return null;
        Iterator<InetAddress> iter = null;
        // TODO 2018.12.17 临时注释
//        iter = prop.getAllAddresses().iterator();

        if (!iter.hasNext()) return null;
        String address4 = "";
        String address6 = "";
        while (iter.hasNext()) {
            InetAddress inetAddress = iter.next();
            if (inetAddress instanceof Inet4Address) {
                address4 = inetAddress.getHostAddress();
                LogUtils.d("formatIpAddresses address4 = " + address4);
            } else if (inetAddress instanceof Inet6Address) {
                address6 = inetAddress.getHostAddress();
                LogUtils.d("formatIpAddresses address6 = " + address6);
            }
        }
        if (address4 != null) {
            LogUtils.d("formatIpAddresses return address4 ");
            return address4;
        }
        LogUtils.d("formatIpAddresses return address6 ");
        return address6;
    }

    private String formatMask() {
        String maskAddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                List<InterfaceAddress> mList = intf.getInterfaceAddresses();
                for (InterfaceAddress l : mList) {
                    InetAddress inetAddress = l.getAddress();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        if (hostAddress.indexOf(":") > 0) {
                            continue;
                        } else {
                            maskAddress = calcMaskByPrefixLength(l
                                    .getNetworkPrefixLength());
                            if (maskAddress == null) {
                                maskAddress = "";
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        LogUtils.d("formatMask maskAddress = " + maskAddress);
        return maskAddress;
    }

    private String formatDns(LinkProperties prop) {
        if (prop == null) return null;
        Iterator<InetAddress> iter = prop.getDnsServers().iterator();
        if (!iter.hasNext()) return null;
        String ds = "";
        while (iter.hasNext()) {
            InetAddress inetAddress = iter.next();
            if (inetAddress instanceof Inet4Address) {
                ds = inetAddress.getHostAddress();
                break;
            }
        }
        LogUtils.d("formatDns ds = " + ds);
        return ds;
    }

    private String formatGateway(LinkProperties prop) {
        String gateway = "";
        // TODO 2018.12.17 临时注释
//        if (prop == null) return null;
//        Iterator<RouteInfo> iter = prop.getAllRoutes().iterator();
//        if (!iter.hasNext()) return null;
//        while (iter.hasNext()) {
//            RouteInfo routeInfo = iter.next();
//            if(routeInfo.isIPv4Default()){
//                gateway = routeInfo.getGateway().getHostAddress();
//                break;
//            }
//        }
        LogUtils.d("formatGateway gateway = " + gateway);
        return gateway;
    }

}
