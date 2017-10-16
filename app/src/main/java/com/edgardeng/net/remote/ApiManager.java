package com.edgardeng.net.remote;

import android.widget.Toast;

import com.edgardeng.baseandroid.R;
import com.edgardeng.data.model.User;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/6/28
 */
public class ApiManager {

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory ;//= RxJavaCallAdapterFactory.create();

    private static OkHttpClient okHttpClient;
    private static  RetroApi retroApi;
    public static RetroApi getRetroApi() {
        if (retroApi == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(RetroApi.END_POINT)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            retroApi = retrofit.create(RetroApi.class);
        }
        return retroApi;
    }


    protected Subscription subscription;
    private void getRepos(String username) {
        subscription = getRetroApi()
                .search(username)
                .subscribeOn(Schedulers.io())
//                .observeOn(An)
                .subscribe(getObserver());
    }
    Observer<List<User>> observer;
    private Observer<? super List<User>> getObserver() {

        if (null == observer) {
            observer = new Observer<List<User>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                 }

                @Override
                public void onNext(List<User> users) {

                }
            };
        }

        return observer;
    }



}
