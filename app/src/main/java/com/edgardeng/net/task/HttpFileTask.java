package com.edgardeng.net.task;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by dengxixi on 2018/7/16.
 */

public class HttpFileTask extends HttpTask {

    public HttpFileTask(int id, String url, HashMap<String, String> para) {
        super(id, url, para);
    }

    private String fileField;
    private String fileName;
    private Bitmap bitmap;

    public String getFileField() {
        return fileField;
    }

    public void setFileField(String fileField) {
        this.fileField = fileField;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
