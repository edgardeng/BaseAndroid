package com.edgardeng.baseandroid;/**
 * Created by dengxixi on 16/1/9.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/1/9
 */
public class BaseActivity extends AppCompatActivity {


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


}
