package com.edgardeng.net.task;
import java.util.HashMap;

/**
 * Created by dengxixi on 2018/7/16.
 */

/** 基本的网络人物 */
public class BaseTask {


    public static final int NETWORK_ERROR       = 1;
    public static final int TASK_COMPELTE       = 2;
    public static final int TASK_FAIL           = 3;
    public static final int TASK_DOING          = 4; //　任务进度　

    /** task id */
    private int id = 0;

    private String url ;
    private HashMap<String, String> parameter; // 任务的参数
    private String raw; // raw data

    public BaseTask() {}
    public BaseTask(int id) { setId(id);}

    public BaseTask(int id,String url,HashMap<String,String> para) {
        setId(id);
        setUrl(url);
        setPrameter(para);
    }


    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public int getId () {
        return this.id;
    }

    public void setId (int id) {
        this.id = id;
    }

    /** HashMap 参数*/
    public HashMap<String, String> getPrameter() {
        return parameter;
    }

    public void setPrameter(HashMap<String, String> p) {
        this.parameter =p;
    }

    /** URL 参数*/
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) { this.url = url; }

    public void onStart () {
//		Log.w("BaseTask", "onStart");
    }

    public void onComplete () {
//		Log.w("BaseTask", "onComplete");
    }

    public void onComplete (String httpResult) {
//		Log.w("BaseTask", "onComplete");
    }

    public void onError (String error) {
//		Log.w("BaseTask", "onError");
    }

    public void onStop () {
//		Log.w("BaseTask", "onStop");
    }
}
