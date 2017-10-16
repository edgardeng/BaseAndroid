package com.edgardeng.baseandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.edgardeng.data.model.Repo;
import com.edgardeng.data.model.Update;
import com.edgardeng.net.remote.GitHubService;
import com.edgardeng.util.ILog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class UILaunch extends BaseActivity {


    @Bind(R.id.imageView_user)
    ImageView imageViewUser;
    @Bind(R.id.editText_user)
    EditText editTextUser;
    @Bind(R.id.imageView_password)
    ImageView imageViewPassword;
    @Bind(R.id.editText_password)
    EditText editTextPassword;
    @Bind(R.id.button_forget)
    Button buttonForget;
    @Bind(R.id.button_login)
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_launch);
        ButterKnife.bind(this);

        new Update().check(this);
        if (BuildConfig.DEBUG)
            toast("debug version");
        else
            toast("release version");
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.RIGHT);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @OnClick(R.id.button_login)
    void onLogin(){
        toast("onLogin");
        selectPictures();
//        doNetwork();
//        testRxJava();
    }

    private void  doNetwork(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GitHubService gitHubService =  retrofit.create(GitHubService.class);

        String username = "edgardeng";
        Call<List<Repo>> call =  gitHubService.listRepos(username);

            call.enqueue(new Callback<List<Repo>>(){
                @Override
                public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                    List<Repo> repos = response.body();
                    ILog.w("Retrofit Return",repos.toString());
                }

                @Override
                public void onFailure(Call<List<Repo>> call, Throwable t) {
                }
            });
        Call<String>  userinfo = gitHubService.userInformation(username);
        ILog.w("Retrofit Return",userinfo.toString());

    }

    private void testRxJava(){
        /** */
        String[] names = {"edgar","deng","edgar deng"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {
                        ILog.w("RxJava",name);
                    }
                });

        // 1创建 被观察者
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("hello");
            }
        })
                //订阅 观察者
                .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                ILog.d("RxJava Subscriber", "onNext:"+s);
            }
        });



        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
            }
        })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "word";
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        ILog.d("RxJava Subscriber","onNext" +s);
                    }
                });




    }


    /** RxJava 的学习 */


    /** ѡȡ ͼƬ*/
    private void selectPictures(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent,1);
    }
    private Bitmap bitmap_edit;
    /**  */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case 1:
                    if (data != null){
                        startPhotoZoom(data.getData(), 360);// 360*360
                    }
                    break;
                case 2:
                    if (data != null)
                        setPicToView(data);
                    break;
            }
        }
    }

    /**  */
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);		//1:1
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }

    /**  */
    private void setPicToView(Intent picdata) {
        try {
            Bundle bundle = picdata.getExtras();
            if (bundle != null) {
                if(bitmap_edit!=null)
                    bitmap_edit.recycle();
                bitmap_edit = bundle.getParcelable("data");
                ILog.w("width->"+bitmap_edit.getWidth());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
