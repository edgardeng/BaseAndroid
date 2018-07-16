package com.edgardeng.net.task;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/8/8
 */
public class PushHandler extends Handler{

    private String TAG = "PushHandler";
    protected OnPushReceiveListener mListener;

    public PushHandler(OnPushReceiveListener listener) {
        this.mListener = listener;
    }

    @Override
    public void handleMessage(Message msg) {

        if(msg == null || msg.obj == null) {
            Log.e(TAG,"PushHandler msg null ");
            return;
        }

        try{

            if(mListener != null ){
                mListener.onPushDataReceive(msg.obj.toString()); // send data to handle
            }else{
                Log.e(TAG,"OnPushReceiveListener   null ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 推送的数据格式
    // {type:"task/message/..",data:{}}

    /** 推送到达  结果监听 */
    public interface OnPushReceiveListener {
        void onPushDataReceive(String content);
    }
}
