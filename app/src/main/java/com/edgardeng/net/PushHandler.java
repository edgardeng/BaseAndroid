package com.edgardeng.net;
import android.os.Handler;
import android.os.Message;
import org.json.JSONObject;


import static com.edgardeng.net.HttpTask.GET_PUSH_DATA;



/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/8/8
 */
public class PushHandler extends Handler{


    protected OnPushReceiveListener mListener;

    public PushHandler(OnPushReceiveListener listener) {
        this.mListener = listener;
    }

    @Override
    public void handleMessage(Message msg) {


        if(msg ==null || msg.obj ==null)
            return;
        try{
            if(msg.what == GET_PUSH_DATA){
                String json = msg.obj.toString();
                JSONObject jo = new JSONObject(json);
//                if(jo != null && jo.has(PUSHTYPE)){
//                    mListener.onPushDataReceive(jo.optInt(PUSHTYPE,-1),jo.optString(CONTENT));
//                }else{
//                    mListener.onPushDataReceive(-1,json);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** 推送到达  结果监听 */
    public interface OnPushReceiveListener {
        void onPushDataReceive(int type, String content);
    }
}
