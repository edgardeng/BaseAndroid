# BaseAndroid
> 一个简易的Android框架

## Structure 结构

```

-- baseandroid 项目 目录
-- data  数据
   -- local 本地数据存储
   -- model 数据模型
-- media 媒体库 音频视频图片
-- net 网络操作
    -- api 网络接口【根据项目变化】
    -- remote 网络数据操作
-- receiver 接收器
-- security 数据安全
-- service  服务
-- util     工具集 【！需要改变，最好不要出现 util，base，common等通用构架】
-- widget   自定义视图控件

```

## [support library](./Docs.md)


* [ButterKnife Zelezny](https://github.com/JakeWharton/butterknife)

    一个注解工具
Android Studio: go to Preferences → Plugins → Browse repositories
and search for ButterKnife Zelezny

* [OkHttp](https://github.com/square/okhttp)

    An HTTP & HTTP/2 client for Android and Java applications

* [Retrofit](https://github.com/square/retrofit)
    
   Type-safe HTTP client for Android and Java

* [Google Gson](https://github.com/google/gson)
   
   convert Java Objects into their JSON representation.


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
    
    
   