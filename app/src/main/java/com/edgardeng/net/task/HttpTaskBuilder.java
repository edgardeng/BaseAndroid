package com.edgardeng.net.task;

import java.util.HashMap;

/**
 * Http Task with OkHttp
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/8/4
 */
public class HttpTaskBuilder {

    private String TAG = "HttpTaskBuilder";
    private HttpTask httpTask;

    public HttpTaskBuilder() {

    }

    public HttpTaskBuilder build(int id, String url, HashMap<String,String> para) {
        httpTask = new HttpTask(0,url,para);
        return this;
    }

    public void listener(OnTaskListener listener) {
        if (httpTask!=null) {
            httpTask.setOnTaskListener(listener);
        }
    }
    public void handler(TaskHandler handler) {
        if (httpTask!=null) {
            httpTask.setHandler(handler);
        }
    }
    public HttpTask task() {
        return httpTask;
    }



}
