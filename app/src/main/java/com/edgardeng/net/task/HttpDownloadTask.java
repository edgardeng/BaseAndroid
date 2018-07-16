package com.edgardeng.net.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dengxixi on 2018/7/16.
 */

public class HttpDownloadTask extends HttpTask {

    public HttpDownloadTask(int id, String url, HashMap<String, String> para) {
        super(id, url, para);
    }

    private int progress;
    private String destFileDir;
    private String destFileName;

    public String getDestFileDir() {
        return destFileDir;
    }

    public void setDestFileDir(String destFileDir) {
        this.destFileDir = destFileDir;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public void setDestFileName(String destFileName) {
        this.destFileName = destFileName;
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        // 储存下载文件的目录
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, destFileName);
        try {
            is = response.body().byteStream();
            long total = response.body().contentLength();
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                int p = (int) (sum * 1.0f / total * 100);
                if (p > progress) {
                    progress = p;
                    // ILog.w(TAG, "/*------------------ " + p + "% -------------------- */");
                    onTaskResponse(TASK_DOING, getId(),"" + p);
                }
            }
            fos.flush();
            progress = 0;
            onTaskResponse(TASK_COMPELTE, getId(), file.getPath());
        } catch (Exception e) {
            onTaskResponse(NETWORK_ERROR, getId(), "");
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }
        }
    }


}
