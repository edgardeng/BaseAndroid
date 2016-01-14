package com.edgardeng.data.model;

/**
 * Created by dengxixi on 16/1/9.
 */

import android.content.Context;
import android.util.Log;


import com.edgardeng.baseandroid.BuildConfig;
import com.edgardeng.ui.widget.MQDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/1/9
 */
public class Update {

    public static String API_TOKEN = "c53fd9cb8054168a07ec07d324473ffd";
    public static String TAG = "Update";

    private String newVersion;
    private String minVersion;

    private String update_description;

    private String update_url;/** 检测更新的地址*/
    private String install_url;
    private String version_short;
    private String version_short_local;
    private float   binary;


    public Update( ) {
        version_short_local = BuildConfig.VERSION_NAME;
    }

    private  Context context;

    public   void  check(Context c){
        context = c;

        FIR.checkForUpdateInFIR(Update.API_TOKEN, new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                Log.w(TAG, "check from fir.im success! " + "\n" + versionJson);
                json2Update(versionJson);
                getUpdateVersion();
            }

            @Override
            public void onFail(Exception exception) {
                Log.e(TAG, "check fir.im fail! " + "\n" + exception.getMessage());
            }

            @Override
            public void onStart() {
                Log.w(TAG, "check fir.im onStart");
            }

            @Override
            public void onFinish() {
                Log.w(TAG, "check fir.im onFinish");
            }
        });
    }

    private Update json2Update( String json ) {
        Update update = null;
        /*
        {"name":"云传感",
                "version":"1",
                "changelog":"First release",
                "updated_at":1452343373,
                "versionShort":"0.0.1",
                "build":"1",
                "installUrl":"http://download.fir.im/v2/app/install/56910040e75e2d6eed000001?download_token=ee6ecf34ae4ca56187b2d39d10c76c07",
                "install_url":"http://download.fir.im/v2/app/install/56910040e75e2d6eed000001?download_token=ee6ecf34ae4ca56187b2d39d10c76c07",
                "direct_install_url":"http://download.fir.im/v2/app/install/56910040e75e2d6eed000001?download_token=ee6ecf34ae4ca56187b2d39d10c76c07",
                "update_url":"http://fir.im/clouds",
                "binary":{"fsize":394366}
        }  */
        if (json != null) {
            try {
                JSONObject object = new JSONObject(json);
                update = this;
                update.version_short = object.optString("versionShort");
                update.update_description = object.optString("changelog");
                update.install_url = object.optString("install_url");
                update.update_url = object.optString("update_url");
                update.binary = object.optJSONObject("binary").optInt("fsize")/1000;

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return update;
    }

    private void getUpdateVersion(){

        if(version_short==null) return;
        if(version_short.compareTo(version_short_local)>0){
            try {
                MQDialog updateDialog = new MQDialog(context);
                updateDialog.setTitle(context.getString(R.string.update_hint));
                updateDialog.setOnButtonClickListener(new MQDialog.OnButtonClickListener() {
                    @Override
                    public void OnButtonClick(MQDialog dialog, int btnPosition, List<String> edit_texts) {
                        if (btnPosition == 1)
                            UpdateReceiver.download_apk(context, "CloudSensor_V" + version_short + ".apk", install_url);
                        dialog.dismiss();
                    }
                });
                StringBuffer sb = new StringBuffer();
                sb.append("最新版本:");
                sb.append(version_short);
                sb.append("\r\n更新内容:");
                sb.append(update_description);
                sb.append("\n大小:");
                sb.append(binary>1000?  binary/1000 +"MB" :binary +"KB");

                updateDialog.setMessage(sb);
                updateDialog.show();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }else{

        }


    }

}
