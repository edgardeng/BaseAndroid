package com.edgardeng.net;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;


import com.edgardeng.net.api.BaseApi;
import com.edgardeng.util.ILog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.edgardeng.net.HttpTask.*;

/**
 *  网络任务管理器
 *
 *  1 个 task 参数  1 个 onHandlerListener 回执；
 *
 *
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/8/4
 */
public class TaskManager {

    private OkHttpClient client;
    private String TAG = "TaskManager";
    private TaskHandler handler;
    private TaskHandler.OnTaskListener onHandlerListener;
    private String hostIP = "192.168.1.10";
    private String hostSegment = "index.php";

    /** 需要重构 */
    public TaskManager(TaskHandler.OnTaskListener listener){
        if(listener!=null){
            this.onHandlerListener = listener;
            handler  =  new TaskHandler(onHandlerListener);
        }
        if(client == null)
            client = new OkHttpClient();
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public String getHostSegment() {
        return hostSegment;
    }

    public void setHostSegment(String hostSegment) {
        this.hostSegment = hostSegment;
    }

    public void get(int taskid, String url, HashMap<String,String>paras) {
//        if(url == null) url = BaseApi.getHttpBase();
        get(new HttpTask(taskid,url,paras));
    }

    public void get(final HttpTask task) {
        if (task == null) return;
        try {

            HashMap<String, String> params = task.getPrameter();
            //  -> set parameter in httpurl
            Request request = null;
            if(params!=null){

                Set<Map.Entry<String, String>> entries = params.entrySet();

                HttpUrl.Builder builder = new HttpUrl.Builder()
                        .host(hostIP)
                        .scheme("http");
                if (hostSegment != null) {
                    builder.addPathSegment(hostSegment);
                }// 可以有两个 pathsegment;
//                builder.addPathSegment("index.php");
                for (Map.Entry<String, String> entry : entries) {
                    builder.addQueryParameter(entry.getKey(), entry.getValue());
                }
                HttpUrl httpUrl = builder.build();
                request = new Request.Builder()
                        .url(httpUrl)
                        .get()
                        .build();
                ILog.i(TAG,httpUrl.toString());
            }else{
                request = new Request.Builder()
                        .url(task.getUrl())
                        .get()
                        .build();
                ILog.i(TAG,task.getUrl());
            }
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ILog.e(TAG, call.toString());
                    e.printStackTrace();
                    task.onError(""); //没有 handler 的时候 走这条
                    onTaskFail(task.getId()); // 网络错误
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    int taskid = task.getId();
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        task.onComplete(result); //没有 handler 的时候 走这条

                        ILog.w(TAG, "/*--------------------------------------");
                        ILog.i(TAG, result);
                        ILog.w(TAG, "--------------------------------------*/");
                        onTaskResponse(taskid, result); // 有handler
                    } else {
                        onTaskFail(taskid);
                        task.onComplete();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void post(final int taskid, HashMap<String,String> paras) {

//        TODO - remove base api
        String url = BaseApi.getHttpBase();
        ILog.i(paras.toString());
        FormBody.Builder builder = new FormBody.Builder();

        for (String key : paras.keySet()) {
            builder.add(key,paras.get(key));
        }
        Request request =  new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .build();
        if(client == null) client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
                ILog.e(TAG, call.toString()) ;
                onTaskFail(taskid);
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    String result  = response.body().string();
                    ILog.w(TAG, "/*--------------------------------------");
                    ILog.i(TAG, result);
                    ILog.w(TAG, "--------------------------------------*/");

                    onTaskResponse(taskid, result);
                }else{
                    onTaskFail(taskid);
                }
            }
        });


    }


    public void excute(final HttpTask task) {

        if (task == null) return;

        String url = task.getUrl();
        ILog.i(TAG, url);

        HashMap<String,String > params = task.getPrameter();
        Request request = null;
        if(params != null){
            ILog.i(TAG, params.toString());
            Set<Map.Entry<String, String>> entries = params.entrySet();
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : entries) {
                builder.add(entry.getKey(), entry.getValue());
            }
            RequestBody requestBody = builder.build();
            request = new Request.Builder()
                    .url(task.getUrl())
                    .post(requestBody)
                    .build();
        }else{
            request = new Request.Builder()
                    .url(task.getUrl())
                    .get()
                    .build();
        }
        if(client == null) client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
                ILog.e(TAG, call.toString()) ;
                int taskid = task.getId();
                onTaskFail(taskid);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                int taskid = task.getId();
                if (response.isSuccessful()) {
                    String result  = response.body().string();
                    ILog.w(TAG, "/*--------------------------------------");
//                    ILog.i(TAG, "response tag:" + call.);
//                    ILog.i(TAG, "response code:" + response.code());
                    ILog.i(TAG, result);
                    ILog.w(TAG, "--------------------------------------*/");

                    onTaskResponse(taskid, result);
                }else{
                    onTaskFail(taskid);
                }
            }
        });
    }

    public void postImage(final int taskid, String url, String name, Bitmap image){
        try {
//            Bitmap image = task.getImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG,80,baos);
            RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("uploadfile",name,body);

            MultipartBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)//地址
                    .post(requestBody)//添加请求体
                    .build();  //构建请求
            client.newCall(request).enqueue(new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    onTaskFail(taskid);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result  = response.body().string();
                        ILog.w(TAG, "/*--------------------------------------");
                        ILog.i(TAG, result);
                        try{
                            JSONObject json = new JSONObject(result);
                            int code = json.optInt("code",0);
                            if(handler!=null){
                                Message msg  = handler.obtainMessage();
                                if(code==0){
                                    msg.what = JSON_FAIL;
                                }else if(code ==1){
                                    String image_ip = "./"+json.optString("location");
                                    msg.what = JSON_SUCCESS;
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(HttpTask.TASK_ID, taskid);
                                    bundle.putString(HttpTask.TASK_DATA, image_ip);
                                    msg.setData(bundle);
                                    // 将图片地址 以 JSON_SUCCESS 的方法返回
                                }
                                handler.sendMessage(msg);
                            }

                        }catch (JSONException e){

                        }
                        ILog.w(TAG, "--------------------------------------*/");
                    }
                }
            });
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void onTaskResponse(int taskid,String result){

        if(handler == null) {
            ILog.e(TAG, "on handler");
            return;
        }

        Message msg  = handler.obtainMessage(); //  ready for message
        msg.what = NETWORK_COMPELTE;
        Bundle bundle = new Bundle();
        bundle.putInt(HttpTask.TASK_ID, taskid);
        try{

            JSONObject json = new JSONObject(result);
            String status = json.optString(HttpTask.STATUS,null);

            if (status == null){    //  network success not for normal json
                msg.what = NETWORK_COMPELTE;
                bundle.putString(HttpTask.TASK_DATA,result);
            }else{
                String content = json.optString(HttpTask.INFO);

                if(status.equals("success")){
                    msg.what = JSON_SUCCESS;
                    bundle.putString(HttpTask.TASK_DATA, content);
                }else if(status.equals("fail")){
                    msg.what = JSON_FAIL;
                    bundle.putString(HttpTask.TASK_DATA, content);
                }else{
                    bundle.putString(HttpTask.TASK_DATA,result);
                }
            }

        }catch (JSONException e){
            bundle.putString(HttpTask.TASK_DATA,result);
            e.printStackTrace();
        }catch (Exception e){
            bundle.putString(HttpTask.TASK_DATA,result);
            e.printStackTrace();
        }
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    private void onTaskFail(int taskid){
        if(handler == null) {
            ILog.e(TAG, "on handler");
            return;
        }
        Message msg  = handler.obtainMessage();
        msg.what =   NETWORK_ERROR;
        Bundle bundle = new Bundle();
        bundle.putInt(HttpTask.TASK_ID, taskid);
        msg.setData(bundle);
        handler.sendMessage(msg);

    }


}