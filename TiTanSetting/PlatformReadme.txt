1、修改Androidmainfest.xml文件为系统应用
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.titan.titv.settings"
    android:sharedUserId="android.uid.system">

2、com.titan.titv.settings.manager.SettingUiManager 修改initParams()函数
mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
改为
mParams.type = WindowManager.LayoutParams.TYPE_PHONE;

3、修改service 的优先级，自改Androidmainfest.xml文件，打开注释
        <service android:name=".services.SettingService"
            android:exported="true">
            <!--<intent-filter android:priority="1000" >-->
            <!--</intent-filter>-->
        </service>

4、targetSdkVersion targetSdkVersion 
targetSdkVersion 22 改为  targetSdkVersion 28