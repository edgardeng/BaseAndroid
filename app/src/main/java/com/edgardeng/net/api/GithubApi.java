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

/**

 Call<Repo> call = service.loadRepo();
 call.enqueue(new Callback<Repo>(){
@Override public void onResponse(Response<Repo> response){
//从response.body()中获取结果
}
@Override public void onFailure(Throwable t){

}
});


 Call<Repo> call = service.loadRepo();
 Repo repo = call.excute();


 */



/**
{
  "current_user_url": "https://api.github.com/user",
  "current_user_authorizations_html_url": "https://github.com/settings/connections/applications{/client_id}",
  "authorizations_url": "https://api.github.com/authorizations",
  "code_search_url": "https://api.github.com/search/code?q={query}{&page,per_page,sort,order}",
  "commit_search_url": "https://api.github.com/search/commits?q={query}{&page,per_page,sort,order}",
  "emails_url": "https://api.github.com/user/emails",
  "emojis_url": "https://api.github.com/emojis",
  "events_url": "https://api.github.com/events",
  "feeds_url": "https://api.github.com/feeds",
  "followers_url": "https://api.github.com/user/followers",
  "following_url": "https://api.github.com/user/following{/target}",
  "gists_url": "https://api.github.com/gists{/gist_id}",
  "hub_url": "https://api.github.com/hub",
  "issue_search_url": "https://api.github.com/search/issues?q={query}{&page,per_page,sort,order}",
  "issues_url": "https://api.github.com/issues",
  "keys_url": "https://api.github.com/user/keys",
  "notifications_url": "https://api.github.com/notifications",
  "organization_repositories_url": "https://api.github.com/orgs/{org}/repos{?type,page,per_page,sort}",
  "organization_url": "https://api.github.com/orgs/{org}",
  "public_gists_url": "https://api.github.com/gists/public",
  "rate_limit_url": "https://api.github.com/rate_limit",
  "repository_url": "https://api.github.com/repos/{owner}/{repo}",
  "repository_search_url": "https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}",
  "current_user_repositories_url": "https://api.github.com/user/repos{?type,page,per_page,sort}",
  "starred_url": "https://api.github.com/user/starred{/owner}{/repo}",
  "starred_gists_url": "https://api.github.com/gists/starred",
  "team_url": "https://api.github.com/teams",
  "user_url": "https://api.github.com/users/{user}",
  "user_organizations_url": "https://api.github.com/user/orgs",
  "user_repositories_url": "https://api.github.com/users/{user}/repos{?type,page,per_page,sort}",
  "user_search_url": "https://api.github.com/search/users?q={query}{&page,per_page,sort,order}"
}

 */
