package com.edgardeng.net.remote;

import com.edgardeng.data.model.Repo;
import com.edgardeng.data.model.User;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import com.edgardeng.net.api.GithubApi;
import com.edgardeng.util.ILog;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/6/28
 */
public class ApiManager {

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory ;//= RxJavaCallAdapterFactory.create();

    private static OkHttpClient okHttpClient;
    private static  GithubApi githubApi;

    public static GithubApi getGithubApi() {
        if (githubApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(githubApi.END_POINT)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            githubApi = retrofit.create(GithubApi.class);
        }
        return githubApi;
    }


    protected Subscription subscription;
    private void getRepos(String username) {
        subscription = getGithubApi()
                .repos(username)
                .subscribeOn(Schedulers.io())
//                .observeOn(An)
                .subscribe(getObserver()); //  前提是返回 Observable<>
    }

    Observer<List<Repo>> observer;
    private Observer<? super List<Repo>> getObserver() {

        if (null == observer) {
            observer = new Observer<List<Repo>>() {
                @Override
                public void onCompleted() {
                    ILog.w(" - onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    ILog.w(" - onError");
                 }

                @Override
                public void onNext(List<Repo> users) {
                    ILog.w(" - onNext");
                }
            };
        }

        return observer;
    }


    public static GithubApi github() {
        if (githubApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(githubApi.END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            githubApi = retrofit.create(GithubApi.class);
        }
        return githubApi;
    }




    private void doNetwork() {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(GitHubService.END_POINT)
//                .addConverterFactory(GsonConverterFactory.create())
////                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        GitHubService gitHubService = retrofit.create(GitHubService.class);
//
//        String username = "edgardeng";
//        Call<List<Repo>> call = gitHubService.listRepos(username);
//
//        call.enqueue(new Callback<List<Repo>>() {
//            @Override
//            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
//                List<Repo> repos = response.body();
//                ILog.w("Retrofit Return", repos.toString());
//            }
//
//            @Override
//            public void onFailure(Call<List<Repo>> call, Throwable t) {
//            }
//        });
//        Call<String> userinfo = gitHubService.userInformation(username);
//        ILog.w("Retrofit Return INFO 》》 ", userinfo.toString());

    }


}
