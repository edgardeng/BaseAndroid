package com.edgardeng.net.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import static com.edgardeng.net.task.HttpTask.*;

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
                case TASK_COMPELTE:
                    mListener.onTaskComplete(taskid, result);
                    break;
                case TASK_FAIL:
                    mListener.onTaskFail(taskid, result);
                    break;
                case TASK_DOING:
                    int percent = 0;
                    try{
                        percent = Integer.valueOf(result).intValue();
                    }catch (NumberFormatException e){
                    }
                    mListener.onTaskDoing(taskid, percent);
                    break;
                case NETWORK_ERROR:
                    mListener.onNetworkError(taskid);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}