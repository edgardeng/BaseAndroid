package com.edgardeng.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.edgardeng.receiver.KeepLiveReceiver;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/8/17
 */

public class InnerService extends Service{
    @Override
    public IBinder onBind(Intent i){
        return null;
    }

    @Override
    public int onStartCommand(Intent i,int flag,int startid){
//        KeepLiveManage.getInstance().setForeground(mService,this);
        return  super.onStartCommand(i, flag, startid);
    }

    public void setForeground(final Service liveService,final Service innerService){
        final int pushId = 1;
        if(liveService !=null){
            if(Build.VERSION.SDK_INT <Build.VERSION_CODES.JELLY_BEAN_MR2){
                liveService.startForeground(pushId,new Notification());
            }else{
                liveService.startForeground(pushId,new Notification());
                if(innerService!=null){
                    innerService.startForeground(pushId,new Notification());
                    innerService.stopSelf();
                }
            }
        }
    }


}
