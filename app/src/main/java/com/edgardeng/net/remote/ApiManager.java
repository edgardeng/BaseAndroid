package com.edgardeng.net.remote;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.edgardeng.net.api.GithubApi;


/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/6/28
 */
public class ApiManager {

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static  GithubApi githubApi;

    /**
     * no  RxJava
     * */
    public static GithubApi getGithub() {
        if (githubApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
//                    .client(okHttpClient)
                    .baseUrl(GithubApi.END_POINT)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            githubApi = retrofit.create(GithubApi.class);
        }
        return githubApi;
    }

    /**
     * api 接口的实现
     * 使用了 RxJava，GsonConverter，RxJavaCallAdapter
     *
     * */
    public static GithubApi github() {
        if (githubApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(githubApi.END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            githubApi = retrofit.create(GithubApi.class);
        }
        return githubApi;
    }


}
