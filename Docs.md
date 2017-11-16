# Usage of Library



* [Retrofit](https://github.com/square/retrofit)


``` java
// api 接口
public interface GithubApi {
    public static String END_POINT= "https://api.github.com/";

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

    /** Query parameters  Map */
    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);

    /** REQUEST BODY */

    @POST("users/new")
    Call<User> createUser(@Body User user);
}



// api 执行

// api 返回

// RxJava的使用
public interface GithubApi {
    public static String END_POINT= "https://api.github.com/";

    @GET("users/{user}/repos")
    Observable<List<Repo>> repos(@Path("user") String user);
    // https://api.github.com/users/edgardeng/repos
    // *retrofit2.0后：BaseUrl要以/结尾，@GET 等请求不要以/开头。
}

```  


* [Google Gson](https://github.com/google/gson)


```
Gson gson = new Gson();

List<HorizontalSection> sections = gson.fromJson(json,
                        new TypeToken<List<HorizontalSection>>() { }.getType()
             
                
```
* [RxJava](https://github.com/ReactiveX/RxJava)
      
   Java VM implementation of Reactive Extensions.
   
   
## UI 
   
## Security
    Base64 DES MD5 RSA for code
    
    
   