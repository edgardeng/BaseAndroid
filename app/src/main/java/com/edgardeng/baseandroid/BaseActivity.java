package com.edgardeng.baseandroid;/**
 * Created by dengxixi on 16/1/9.
 */

import android.app.Activity;
import android.widget.Toast;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/1/9
 */
public class BaseActivity extends Activity {


    public void toast (String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void toast (int resourceId) {
        Toast.makeText(this, getString(resourceId), Toast.LENGTH_SHORT).show();
    }


}
