package com.edgardeng.baseandroid;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import com.edgardeng.data.model.Repo;
import com.edgardeng.data.model.User;
import com.edgardeng.net.api.GithubApi;
import com.edgardeng.net.remote.ApiManager;
import com.edgardeng.util.ILog;
import com.edgardeng.widget.ViewPath;
import com.edgardeng.widget.ViewPathEvaluator;
import com.edgardeng.widget.ViewPoint;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class UILaunch extends BaseActivity {

    @Bind(R.id.button_start)
    Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_launch);
        ButterKnife.bind(this);
        ILog.i("onCreate");
//        new Update().check(this); // fir.im 停止免费服务
        if (BuildConfig.DEBUG)
            toast("debug version");
        else
            toast("release version");

//        Window window = getWindow();
//        window.setGravity(Gravity.LEFT | Gravity.RIGHT);
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.x = 0;
//        params.y = 0;
//        params.height = 1;
//        params.width = 1;
//        window.setAttributes(params);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ILog.i("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick(R.id.button_start)
    void onLogin() {
        ILog.i("button_start");
        fly();
//        forward(UILogin.class);
//        doNetwork();
//        doNoRxJava();
//        doOkhttp();
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

    private void  fly () {
        ViewPath path = new ViewPath(); //偏移坐标
            path.moveTo(0,0);
            path.lineTo(0,500);
            path.curveTo(-300,200,-600,800,-800,400);
            path.lineTo(-800,100);

            ObjectAnimator anim = ObjectAnimator.ofObject(this,"fabLoc",new ViewPathEvaluator(),path.getPoints().toArray());
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(3000);
            anim.start();

    }
    public void setFabLoc(ViewPoint newLoc){
        buttonStart.setTranslationX(newLoc.x);
        buttonStart.setTranslationY(newLoc.y);
    }


}
