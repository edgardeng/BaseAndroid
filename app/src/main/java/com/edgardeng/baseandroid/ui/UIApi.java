package com.edgardeng.baseandroid.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edgardeng.baseandroid.BaseActivity;
import com.edgardeng.baseandroid.R;
import com.edgardeng.data.model.Repo;
import com.edgardeng.data.model.User;
import com.edgardeng.net.api.GithubApi;
import com.edgardeng.net.api.UserApi;
import com.edgardeng.net.remote.ApiManager;
import com.edgardeng.util.FileUtils;
import com.edgardeng.util.ILog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API 及 Retrofit 测试
 *
 * Created by dengxixi on 2017/11/3.
 */

public class UIApi  extends BaseActivity {


    TextView textView;
    private String content = "";
    private Button postBtn;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout layout = new RelativeLayout(this);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
        textView = new TextView(this);
        textView.setPadding(10,10,10,10);
        textView.setText("start...");
        textView.setId(100);

        postBtn = new Button(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW,100);
        postBtn.setLayoutParams(lp);
        postBtn.setText("上传图片");
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPictures();
            }
        });

        layout.addView(textView);
        layout.addView(postBtn);
        setContentView(layout);

        ILog.i("UIApi onCreate");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void doNetwork() {

        // set Okhttp Client
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(9, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

        // set Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GithubApi.END_POINT)
                .client(client) // add http client. 可以不要 ？
                .addConverterFactory(GsonConverterFactory.create())// add json Converter.Factory
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // add rxjava adapter
                .build();

        // set Rxjava
        GithubApi api = retrofit.create(GithubApi.class);
        api.followerL("edgardeng")
                // java.lang.IllegalArgumentException: Unable to create call adapter
                // for io.reactivex.Observable<java.util.List<Repo>>
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver2()); // 将结果传给订阅着
    }

    Observer<List<User>> observer2;

    private Observer<? super List<User>> getObserver2() {

        if (null == observer2) {
            observer2 = new Observer<List<User>>() {

                @Override
                public void onError(Throwable e) {
                    ILog.w(" - onError ");
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {
                    ILog.w(" - onComplete ");
                }

                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    ILog.w(" - onSubscribe ");
                }

                @Override
                public void onNext(List<User> users) {
                    ILog.w(" - onNext ");
                    ILog.w(users.toString());
                }
            };
        }

        return observer2;
    }

    Observer<List<Repo>> observer;

    /**
     * By default all reactive types execute their requests synchronously.
     * There are multiple ways to control the threading on which a request occurs:
     * <p>
     * Call subscribeOn on the returned reactive type with a Scheduler of your choice.
     * Use createAsync() when creating the factory which will use OkHttp's internal thread pool.
     * Use createWithScheduler(Scheduler) to supply a default subscription Scheduler.
     *
     * @return
     */
    private Observer<? super List<Repo>> getObserver() {

        if (null == observer) {
            observer = new Observer<List<Repo>>() {

                @Override
                public void onError(Throwable e) {
                    ILog.w(" - onError ");
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {
                    ILog.w(" - onComplete ");
                }

                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    ILog.w(" - onSubscribe ");
                }

                @Override
                public void onNext(List<Repo> users) {
                    ILog.w(" - onNext ");
                    ILog.w(users.toString());
                }
            };
        }

        return observer;
    }

    private void doNoRxJava() {

        GithubApi api = ApiManager.getGithub(); // 没有问题
        Call<List<User>> call = api.followers("edgardeng");
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                ILog.w("Retrofit Return", users.toString());
                ILog.w("Retrofit Return Single", users.get(0).toString());
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });
    }

    private UserApi userApi;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    public UserApi getUserApi() {
        if (userApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UserApi.END_POINT)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            userApi = retrofit.create(UserApi.class);
        }
        return userApi;
    }

    private void getApi() {
        Call<User> call = getUserApi().info(1);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                content = content + "\r\n" + user.toString();
                textView.setText(content);
                ILog.w("Retrofit Return", user.toString());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ILog.w("Retrofit Return onFailure");
                t.printStackTrace();
            }
        });

        getUserApi().change(2,"name-change").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null) {
                    content = content + "\r\n" + user.toString();
                    textView.setText(content);
                    ILog.w("Retrofit Return", user.toString());
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ILog.w("Retrofit Return onFailure");
                t.printStackTrace();
            }
        });

        Map<String,String> hash = new HashMap<>();
        hash.put("name","name-change2");
        hash.put("email","email-change2");
        getUserApi().change2(3,hash).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null) {
                    content = content + "\r\n" + user.toString();
                    textView.setText(content);
                    ILog.w("Retrofit Return", user.toString());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ILog.w("Retrofit Return onFailure");
                t.printStackTrace();
            }
        });


    }

    /** post api 的测试*/
    private void postApi() {

        Map<String,String> hash = new HashMap<>();
        hash.put("name","name-change4");
        hash.put("email","email-change4");
        hash.put("url","email-change4");
        getUserApi().create(hash).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null) {
                    content = content + "\r\n" + user.toString();
                    textView.setText(content);
                    ILog.w("Retrofit Return", user.toString());
                } else {
                    ILog.w("Retrofit Return", response.toString());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ILog.w("Retrofit Return onFailure");
                t.printStackTrace();
            }
        });


    }

    /*  上传图片 */
    private void postImgApi(Bitmap bitmap,String name) {

        if (bitmap == null) {
            ILog.e("no bitmap");
            return;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("photo",name,requestFile);
//        MultipartBody requestBody = builder.build();
//        MultipartBody.Part body = requestBody.part(0);  // 方法一

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", name, requestFile); // 方法二

        try {
            String descriptionString = "This is a description";
            RequestBody description = RequestBody.create( MediaType.parse("multipart/form-data"), descriptionString);
            getUserApi().uploadPhoto(description,body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String result = null;
                    try {
                        result = response.body().string();
                        if (result != null) {
                            content = content + "\r\n" + result;
                            textView.setText(content);
                            ILog.w("Retrofit Return", result);
                        } else {
                            ILog.w("Retrofit Null Return", result);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ILog.w("Retrofit Return onFailure");
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doOkhttp() {
        HashMap<String, String> hash = new HashMap<>();
        hash.put("c", "activity");
        hash.put("a", "getRedPacket4ActivityTest");
        hash.put("userid", "361");
        hash.put("aid", "1");
        doTaskAsync(1, null, hash);
    }

    @Override
    public void onJsonSuccess(int taskId, String json) {
        ILog.i(json);
    }

    @Override
    public void onJsonFail(int taskId, String json) {
        ILog.i(json);
    }

    private Bitmap bitmap_edit;
    private Uri imageUri = null;
    void selectPictures(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        try {
            if(imageUri == null){
                FileUtils utils = new FileUtils(this);
                File outImage = utils.createCacheFile("temp.jpg");
                imageUri = Uri.fromFile(outImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivityForResult(intent,1);
    }
    /**  Result From Image Transform */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case 1://success for select a picture
                    if (data != null){
                        Uri uri = data.getData();
                        try {
                        bitmap_edit = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(uri));
                        postImgApi(bitmap_edit,"photo.jpg");
//                            Intent intent=new Intent("com.android.camera.action.CROP");
//                            intent.setDataAndType(uri, "image/*");
//                            intent.putExtra("crop", "true");
//                            intent.putExtra("aspectX", 1);
//                            intent.putExtra("aspectY", 1);
//                            intent.putExtra("outputX", 600);
//                            intent.putExtra("outputY", 600);		//1:1
//                            intent.putExtra("scale", true);				//消除黑边
//                            intent.putExtra("scaleUpIfNeeded", true);	//黑边
//
////							intent.putExtra("return-data", true);
//                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                            startActivityForResult(intent, 2);
                        } catch (Exception e) {
                            ILog.e(e.getMessage());
                        }
                    }

                    break;
                case 2://success for crop a picture
                    try {
                        bitmap_edit = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUri));
                        postImgApi(bitmap_edit,"photo");

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }



}
