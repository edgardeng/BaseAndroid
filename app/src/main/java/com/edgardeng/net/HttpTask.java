package com.edgardeng.net;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Http Task with OkHttp
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/8/4
 */
public class HttpTask {


    public static final int NETWORK_COMPELTE    = 1;
    public static final int JSON_SUCCESS        = 2;
    public static final int JSON_FAIL           = 3;
    public static final int NETWORK_ERROR       = 4;

    // TODO - 怎么通过 PushReceiver 传给当前 activity . 需要优化
    public static final int GET_PUSH_DATA       = 5; //
    public static final int GET_ITEM_POSITION 	= 6;

    public static final int MESSAGE_EMPTY 		= 7;

    public static final String TASK_ID 		= "task_id";
    public static final String TASK_DATA 	= "task_data";

    public static final String INFO 		= "info"; // api 中字段 不适合所有的api
    public static final String STATUS 	    = "status";



    /** task id */
    private int id = 0;
    /** task tag */
    private int tag =0;

    private String url ;
    private HashMap<String, String> parameter;

    /**   */
    private Bitmap image;
    private String name;

    public HttpTask() {}
    public HttpTask(int id) { setId(id);}
    public HttpTask(int id, String url, HashMap<String,String> para) {
        setId(id);
        setUrl(url);
        setPrameter(para);
    }



    public int getId () {
        return this.id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public int getTag () {
        return this.tag;
    }

    public void setTag(int t ) {
        this.tag = t;
    }

    /** HashMap 参数*/
    public HashMap<String, String> getPrameter() {
        return parameter;
    }

    public void setPrameter(HashMap<String, String> p) {
        this.parameter =p;
    }

    /** Image 参数*/
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** URL 参数*/
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public void onStart () {
//		Log.w("HttpTask", "onStart");
    }

    public void onComplete () {
//		Log.w("HttpTask", "onComplete");
    }

    public void onComplete (String httpResult) {
//		Log.w("HttpTask", "onComplete");
    }

    public void onError (String error) {
//		Log.w("HttpTask", "onError");
    }

    public void onStop () {
//		Log.w("HttpTask", "onStop");
    }


    public String urlWithParameter(){
        if(url!=null){
            StringBuilder sb = new StringBuilder(url);
            if(parameter!=null && url!=null){
                Set<Map.Entry<String, String>> entries = parameter.entrySet();
                int i = 0;
                for (Map.Entry<String, String> entry : entries) {
                    if(i==0){
                        sb.append("?");
                        i++;
                    }else
                        sb.append("&");
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(entry.getValue());
                }
            }
            return  sb.toString();
        }else{
            return null;
        }

    }


    private void putParameterInUrl(){
        if(parameter!=null && url!=null){
            StringBuilder sb = new StringBuilder(url);
            Set<Map.Entry<String, String>> entries = parameter.entrySet();
            int i = 0;
            for (Map.Entry<String, String> entry : entries) {
                if(i==0){
                    sb.append("?");
                    i++;
                }else
                    sb.append("&");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
            setUrl(sb.toString());
            setPrameter(null);
        }
    }

    public void putParameterInUrl(HashMap<String, String> para){
        setPrameter(para);
        putParameterInUrl();
    }


}
