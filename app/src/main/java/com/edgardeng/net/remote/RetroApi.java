package com.edgardeng.net.remote;

import com.edgardeng.data.model.Repo;
import com.edgardeng.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/6/28
 */
public interface RetroApi {
    public static String END_POINT= "https://api.github.com/";

    @GET("users/{user}/repos")
    Observable<List<User>> search(@Query("user") String user);

}
