package com.edgardeng.service;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;

import com.edgardeng.baseandroid.MyApplication;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/8/17
 */
public class KeepLiveManage {


    public void  startJobScheduler(){
        try{
            int jobId = 1;
//            JobInfo.Builder builder = new JobInfo().Builder(jobId,new ComponentName(null,KeepLiveService.class))
//            builder.setPeriodic(10);
//            builder.setPersisted(true);
//            JobScheduler.getInstance(null).schedule(builder.build());
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
