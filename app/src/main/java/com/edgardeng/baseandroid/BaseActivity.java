package com.edgardeng.baseandroid;/**
 * Created by dengxixi on 16/1/9.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.edgardeng.net.TaskHandler;
import com.edgardeng.net.TaskManager;
import com.edgardeng.net.api.BaseApi;

import java.util.HashMap;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/1/9
 */
// TODO - less better !!!
public class BaseActivity extends AppCompatActivity implements TaskHandler.OnTaskListener{


    public void toast (String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void toast (int resourceId) {
        Toast.makeText(this, getString(resourceId), Toast.LENGTH_SHORT).show();
    }

    public void forward (Class<?> classObj ) {
        forward(classObj, null);
    }

    public void forward (Class<?> classObj, Bundle params) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        if(params!=null)
            intent.putExtras(params);
        startActivity(intent);
    }

    /** -----**----------**----- Network -----**----------**----- */

    private TaskManager taskManager;
    public TaskManager getTaskManager() {
        if(taskManager == null)
            taskManager = new TaskManager(this);
        taskManager.setHostIP(BaseApi.HOST_IP);
        taskManager.setHostSegment(BaseApi.getHttpPathSegment());
        return taskManager;
    }

    /** doTaskAsync task with taskid,url,param */
    public void doTaskAsync (int taskId, String taskUrl, HashMap<String, String> taskArgs) {
//		doTaskAsync(taskId, taskUrl, taskArgs, 0) ;
        TaskManager taskManager = getTaskManager();
        taskManager.get(taskId,taskUrl,taskArgs);
    }

    /** doTaskAsync task with taskid,param */
    public void doTaskAsync (int taskId,String url) {
        TaskManager taskManager = getTaskManager();
        doTaskAsync(taskId, url, null) ;
    }


    @Override
    public void onJsonSuccess(int taskId, String json) {

    }

    @Override
    public void onJsonFail(int taskId, String json) {

    }

    @Override
    public void onNetworkError(int taskId) {

    }

    @Override
    public void onTaskComplete(int taskId, String json) {

    }
}
