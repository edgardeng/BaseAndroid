package com.edgardeng.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/8/17
 */
@SuppressLint("NewApi")
public class KeepLiveService extends JobService {

    private final static String TAG = "keep live service";
    private boolean isServiceExit = false;  //是否需要退出

    private volatile static Service mLiveService;

    public static boolean isServiceLive(){
        return mLiveService!=null;
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
