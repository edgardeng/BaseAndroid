package com.edgardeng.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import static com.edgardeng.net.HttpTask.*;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/8/4
 */
public class TaskHandler extends Handler{


    protected OnTaskListener mListener;

    public TaskHandler(OnTaskListener listener) {
        this.mListener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        try {
            Bundle b = msg.getData();
            int taskid = 0;
            String result = null;
            if (b != null) {
                taskid = b.getInt(HttpTask.TASK_ID);
                result = b.getString(HttpTask.TASK_DATA);
            }

            switch (msg.what) {
                case JSON_SUCCESS:
                    mListener.onJsonSuccess(taskid, result);
                    break;
                case JSON_FAIL:
                    mListener.onJsonFail(taskid, result);
                    break;
                case NETWORK_ERROR:
                    mListener.onNetworkError(taskid);
                case NETWORK_COMPELTE:
                    mListener.onTaskComplete(taskid,result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** 网络任务 结果监听 */
    public interface OnTaskListener{
        //        public void onTaskComplete 	(int taskId, String result);
        public void onJsonSuccess(int taskId, String json);

        public void onJsonFail(int taskId, String json);

        public void onNetworkError(int taskId);

        public void onTaskComplete(int taskId,String json);
    }
}