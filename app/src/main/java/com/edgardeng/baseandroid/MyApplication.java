package com.edgardeng.baseandroid;/**
 * Created by dengxixi on 16/1/9.
 */

import android.app.Application;

import im.fir.sdk.FIR;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/1/9
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
    }


}
