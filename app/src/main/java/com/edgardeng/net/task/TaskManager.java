package com.edgardeng.net.task;

import android.graphics.Bitmap;

import com.edgardeng.net.api.BaseApi;
import com.edgardeng.util.ILog;

import java.io.ByteArrayOutputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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
    private OnTaskListener onHandlerListener;

    /** 需要重构 */
    public TaskManager(OnTaskListener listener) {
        if(listener!=null){
             this.onHandlerListener = listener;
            handler  =  new TaskHandler(onHandlerListener);
        }
        if(client == null)
            client = new OkHttpClient();
    }

    private HttpTask httpTask;
    public TaskManager(HttpTask task) {
        this.httpTask = task;
        if(client == null)
            client = new OkHttpClient();
    }

    public void get(int taskid, String url, HashMap<String,String>paras) {
//        if(url == null) url = BaseApi.getHttpBase();
        HttpTask task = new HttpTask(taskid,url,paras);
        task.setHandler(handler);
        get(task);
    }

    public void get( HttpTask task) {
        if (task == null) return;
        try {

            HashMap<String, String> params = task.getPrameter();
            Request request = null;
            if(params!=null){
                Set<Map.Entry<String, String>> entries = params.entrySet();

                HttpUrl.Builder builder = new HttpUrl.Builder()
                        .host(task.getUrl())
                        .scheme("http");
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
            client.newCall(request).enqueue(task);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void post(HttpTask task ) {
        String url = task.getUrl();
        HashMap<String,String> paras = task.getPrameter();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paras.keySet()) {
            builder.add(key,paras.get(key));
        }
        Request request =  new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        if(client == null) client = new OkHttpClient();
        client.newCall(request).enqueue(task);
    }


    public void post(int taskid, HashMap<String,String> paras) {
        post(taskid,BaseApi.getHttpBase(),paras);
    }

    public void post(int taskid,String url, HashMap<String,String> paras) {
        HttpTask task = new HttpTask(taskid,url,paras);
        task.setHandler(handler);
        post(task);
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
        client.newCall(request).enqueue(task);
    }

    public void postImage(HttpFileTask fileTask) {
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            Bitmap image = fileTask.getBitmap();
            String field = fileTask.getFileField();
            String name = fileTask.getFileName();
            if (image != null && field != null && name != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG,80,baos);
                RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
                builder.addFormDataPart(field,name,body);
            }
            MultipartBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(fileTask.getUrl())//地址
                    .post(requestBody)//添加请求体
                    .build();  //构建请求
            client.newCall(request).enqueue(fileTask);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void postImage(int taskid, String url, String field, String name, Bitmap image) {
        HttpFileTask task = new HttpFileTask(taskid,url,null);
        task.setBitmap(image);
        task.setFileField(field);
        task.setFileName(name);
        task.setHandler(handler);
        postImage(task);
    }

    // post json data to host
    public void raw(final int taskid, String url, String json) {

        HttpFileTask task = new HttpFileTask(taskid,url,null);
        task.setRaw(json);
        task.setHandler(handler);
        raw(task);
    }

    public void raw(HttpTask task) {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, task.getRaw());
        Request request =  new Request.Builder()
                .url(task.getUrl())
                .post(body)
                .build();
        if(client == null) client = new OkHttpClient();
        client.newCall(request).enqueue(task);
    }

    /**
     * 文件下载
     * @param url          下载连接
     * @param destFileDir  下载的文件储存目录
     * @param destFileName 下载文件名称
     */
    public void download(final int taskId, String url, final String destFileDir, final String destFileName) {
        HttpDownloadTask task = new HttpDownloadTask(taskId,url,null);
        task.setDestFileDir(destFileDir);
        task.setDestFileName(destFileName);
        task.setHandler(handler);
        download(task);
    }

    public void download(HttpDownloadTask fileTask) {
        String url = fileTask.getUrl();
        Request request = new Request.Builder().url(url).build();
        if(client == null) client = new OkHttpClient();
        client.newCall(request).enqueue(fileTask);
    }


}