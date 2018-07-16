package com.edgardeng.net.task;

import android.os.Bundle;
import android.os.Message;

import com.edgardeng.net.api.BaseApi;
import com.edgardeng.util.ILog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Http Task with OkHttp
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/8/4
 */
public class HttpTask extends BaseTask implements Callback {

    private String TAG = "HttpTask";
    public static final String TASK_ID 		= "task_id";
    public static final String TASK_DATA 	= "task_data";

    public static final String INFO 		= "info"; // api 中字段 不适合所有的api
    public static final String STATUS 	    = "status";

    private OnTaskListener taskListener;
    private TaskHandler handler;

    private String baseUrl = BaseApi.HOST_IP;

    public HttpTask(int id, String url, HashMap<String,String> para) {
        setId(id);
        setUrl(url);
        setPrameter(para);
    }

    @Override
    public void setUrl(String url ) {
        super.setUrl(baseUrl + url);
    }

    public OnTaskListener getTaskListener() {
        return taskListener;
    }

    public void setOnTaskListener(OnTaskListener taskListener) {
        this.taskListener = taskListener;
    }

    public TaskHandler getHandler() {
        return handler;
    }

    public void setHandler(TaskHandler handler) {
        this.handler = handler;
    }

    /** --------------------------------------
     *            Callback                  *
     -------------------------------------- */
    @Override
    public void onFailure(Call call, IOException e) {
        // ILog.e(TAG, call.toString()) ;
        onTaskResponse(NETWORK_ERROR, getId(),"");
        // e.printStackTrace();
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {

        if (response.isSuccessful()) {
            String result  = response.body().string();
            ILog.w(TAG, "/*--------------------------------------");
            ILog.i(TAG, result);
            ILog.w(TAG, "--------------------------------------*/");
            JSONObject json = null;
            try {
                json = new JSONObject(result);
                int code = json.optInt("code",0);
                int what = code == 0 ? TASK_FAIL : TASK_COMPELTE;
                onTaskResponse(what,getId(),json.optString("data",""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            onTaskResponse(NETWORK_ERROR, getId(),"");
        }
    }

    /** 通过　handle 发送给activity*/
    protected void onTaskResponse(int what, int task , String result) {
        if (handler != null) {
            Message msg = handler.obtainMessage();
            msg.what = what;
            Bundle bundle = new Bundle();
            bundle.putInt(TASK_ID, task);
            bundle.putString(TASK_DATA, result);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
        if (taskListener != null) {
            switch (what) {
                case TASK_COMPELTE:
                    taskListener.onTaskComplete(task, result);
                    break;
                case TASK_FAIL:
                    taskListener.onTaskFail(task, result);
                    break;
                case TASK_DOING:
                    int percent = 0;
                    try{
                        percent = Integer.valueOf(result).intValue();
                    }catch (NumberFormatException e){
                    }
                    taskListener.onTaskDoing(task, percent);
                    break;
                case NETWORK_ERROR:
                    taskListener.onNetworkError(task);
            }

        }
    }

}
