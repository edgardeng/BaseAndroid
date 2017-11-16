package com.edgardeng.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 网络返回
 * */
public class ApiResponse {
    public int code;
    public String data;
    public String msg;

    public ApiResponse() {

    }
    public String toString(){
        return "code:"+code+",data:"+data+",msg:"+msg;
    }




}
