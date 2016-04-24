package com.edgardeng.net.remote;

import com.edgardeng.data.model.Repo;
import com.edgardeng.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dengxixi on 16/4/9.
 */
public interface GitHubService {
    public static String END_POINT= "https://api.github.com/";
//    https://api.github.com/users/edgardeng/repos


    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    //https://api.github.com/users/edgardeng/followers


    //https://api.github.com/users/edgardeng
    @GET("users/{user}")
    Call<String>  userInformation(@Path("user") String user);

}

/**

 Call<Repo> call = service.loadRepo();
 call.enqueue(new Callback<Repo>(){
        @Override
        public void onResponse(Response<Repo> response){
            //从response.body()中获取结果
    }
        @Override
    public void onFailure(Throwable t){

    }
});


 Call<Repo> call = service.loadRepo();
 Repo repo = call.excute();


 */