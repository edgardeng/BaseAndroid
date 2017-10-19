package com.edgardeng.baseandroid;

import android.os.Bundle;
import android.widget.Button;

import com.edgardeng.data.model.Repo;
import com.edgardeng.net.TaskManager;
import com.edgardeng.net.api.BaseApi;
import com.edgardeng.net.remote.GitHubService;
import com.edgardeng.util.ILog;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
        toast("button_start");
//        forward(UILogin.class);
//        doNetwork();
        doOkhttp();
    }

    private void doNetwork() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GitHubService gitHubService = retrofit.create(GitHubService.class);

        String username = "edgardeng";
        Call<List<Repo>> call = gitHubService.listRepos(username);

        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repos = response.body();
                ILog.w("Retrofit Return", repos.toString());
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
            }
        });
        Call<String> userinfo = gitHubService.userInformation(username);
        ILog.w("Retrofit Return INFO 》》 ", userinfo.toString());

    }

    private void doOkhttp() {
        HashMap<String,String> hash = new HashMap<>();
        hash.put("c","activity");
        hash.put("a","getRedPacket4ActivityTest");
        hash.put("userid","361");
        hash.put("aid","1");
        doTaskAsync(1,null,hash);
        doTaskAsync(1,null,hash);
        doTaskAsync(1,null,hash);
        doTaskAsync(1,null,hash);
        doTaskAsync(1,null,hash);
        doTaskAsync(1,null,hash);
        doTaskAsync(1,null,hash);
        doTaskAsync(1,null,hash);
        doTaskAsync(1,null,hash);

//        TaskManager taskManager = new TaskManager(this);
//        taskManager.setHostIP(BaseApi.HOST_IP);
//        taskManager.setHostSegment(BaseApi.getHttpPathSegment());
//
//        TaskManager taskManager1 = new TaskManager(this);
//        taskManager1.setHostIP(BaseApi.HOST_IP);
//        taskManager1.setHostSegment(BaseApi.getHttpPathSegment());
//
//        TaskManager taskManager2 = new TaskManager(this);
//        taskManager2.setHostIP(BaseApi.HOST_IP);
//        taskManager2.setHostSegment(BaseApi.getHttpPathSegment());
//        taskManager.get(1,null,hash);
//        taskManager1.get(1,null,hash);
//        taskManager2.get(1,null,hash);
    }

    /** doTaskAsync task with taskid,url,param */
    public void doTaskAsync (int taskId, String taskUrl, HashMap<String, String> taskArgs) {
//		doTaskAsync(taskId, taskUrl, taskArgs, 0) ;
        TaskManager taskManager = getTaskManager();
        taskManager.get(taskId,taskUrl,taskArgs);

    }

    @Override
    public void onJsonSuccess(int taskId, String json) {
        ILog.i(json);
    }

    @Override
    public void onJsonFail(int taskId, String json) {
        ILog.i(json);
    }

    private void testRxJava() {
        /** */
        String[] names = {"edgar", "deng", "edgar deng"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {
                        ILog.w("RxJava", name);
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
                        ILog.d("RxJava Subscriber", "onNext:" + s);
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
                        ILog.d("RxJava Subscriber", "onNext" + s);
                    }
                });


    }

}
