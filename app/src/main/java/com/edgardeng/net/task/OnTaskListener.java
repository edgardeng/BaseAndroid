package com.edgardeng.net.task;

import com.edgardeng.net.api.BaseApi;

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
public interface OnTaskListener  {
    void onTaskFail(int task, String json); //　请求错误，服务器错误

    void onNetworkError(int task); // 网络错误

    void onTaskComplete(int task,String json);

    void onTaskDoing(int task, int percent);
}
