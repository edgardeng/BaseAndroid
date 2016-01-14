package com.edgardeng.baseandroid;/**
 * Created by dengxixi on 16/1/9.
 */

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.edgardeng.util.ILog;

import cn.jpush.android.api.JPushInterface;
import im.fir.sdk.FIR;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/1/9
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        FIR.init(this);//fir.im 初始化

        ApplicationInfo appinfo = null;
        try {
            appinfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String cannel   = appinfo.metaData.getString("JPUSH_CHANNEL");
            String pakage   = appinfo.metaData.getString("PACKAGE_NAME");
            ILog.e(cannel+" - "+pakage);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        JPushInterface.setDebugMode(true);//BuildConfig.DEBUG
        JPushInterface.init(getApplicationContext()); //jpush init

    }


}
