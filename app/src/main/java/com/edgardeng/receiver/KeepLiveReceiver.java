package com.edgardeng.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/8/17
 */
public class KeepLiveReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context c, Intent i){
        String action = i.getAction();
        if(action.equals(Intent.ACTION_SCREEN_OFF)){
//            KeepLiveManager.getInstance().startKeepLiveActivity();
        }else if(action.equals(Intent.ACTION_USER_PRESENT)){
//            KeepLiveManager.getInstance().finishKeepLiveActivity();
        }

//        super.onReceive(c,i);
    }
}
