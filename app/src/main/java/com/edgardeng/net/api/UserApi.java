package com.edgardeng.net.api;

import com.edgardeng.data.model.User;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 本地测试API
 * Created by dengxixi on 2017/11/3.
 */


public interface UserApi {
    public static String END_POINT = "http://192.168.199.146:8888/FirstLaravel/public/";

    @GET("user/{id}")
    Call<User> info(@Path("id") int id);

    @GET("user/{id}")
    Call<User> change(@Path("id") int id,@Query("name") String name);

    @GET("user/{id}")
    Call<User> change2(@Path("id") int id,@QueryMap Map<String, String> options);

//    @Headers({
//            "Accept: application/vnd.github.v3.full+json",
//            "X-CSRF-TOKEN: Qg9ZZpicVA8JQQs/jBIygEhD96l"
//    })
    @POST("user/create")
    Call<User> create(@QueryMap Map<String, String> options);
    // 直接 post  TokenMismatchException异常处理

    // REQUEST BODY
    @POST("users/create")
    Call<User> addUser(@Body User user);


    @POST("user/photo")
    Call<String> addPhoto(RequestBody photo);
//    <input id="file" type="file" class="form-control" name="source">

    @Multipart
    @POST("user/photo")
    Call<ResponseBody> uploadPhoto(@Part("description") RequestBody description,
                               @Part MultipartBody.Part file);





}