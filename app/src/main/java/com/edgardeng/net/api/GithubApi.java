package com.edgardeng.net.api;

import com.edgardeng.data.model.Repo;
import com.edgardeng.data.model.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable; // @ 标注
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * api for Github Retrofit Type
 * Retrofit turns your HTTP API into a Java interface.
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/6/28
 */
public interface GithubApi {
    public static String END_POINT= "https://api.github.com/";

    @GET("users/{user}/repos")
    Observable<List<Repo>> repos(@Path("user") String user);
    // https://api.github.com/users/edgardeng/repos
    // *retrofit2.0后：BaseUrl要以/结尾，@GET 等请求不要以/开头。

    @GET("users/{user}/followers")
    Observable<List<User>> followerL(@Path("user") String user);

    @GET("users/{user}/followers")
    Call<List<User>> followers(@Path("user") String user);
    // https://api.github.com/users/edgardeng/followers

    @GET("users/{user}")
    Call<String>  userInfo(@Path("user") String user);
    // https://api.github.com/users/edgardeng

    /** Query parameters */
    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);
    // https://api.github.com/users/edgardeng/repos?sort=name/desc

    /** Query parameters  Map*/
    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);

    /** REQUEST BODY
     * An object can be specified for use as an HTTP request body with the @Body annotation.
     * The object will also be converted using a converter specified on the Retrofit instance. If no converter is added, only RequestBody can be used.
     */

    @POST("users/new")
    Call<User> createUser(@Body User user);
}
