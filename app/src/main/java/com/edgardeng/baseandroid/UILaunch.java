package com.edgardeng.baseandroid;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.edgardeng.data.model.Repo;
import com.edgardeng.data.model.Update;
import com.edgardeng.net.remote.GitHubService;
import com.edgardeng.ui.base.BaseActivity;
import com.edgardeng.util.ILog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UILaunch extends BaseActivity {


    @Bind(R.id.imageView_user)
    ImageView imageViewUser;
    @Bind(R.id.editText_user)
    EditText editTextUser;
    @Bind(R.id.imageView_password)
    ImageView imageViewPassword;
    @Bind(R.id.editText_password)
    EditText editTextPassword;
    @Bind(R.id.button_forget)
    Button buttonForget;
    @Bind(R.id.button_login)
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_launch);
        ButterKnife.bind(this);

        new Update().check(this);
        if (BuildConfig.DEBUG)
            toast("debug");
        else
            toast("release");
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }





    @OnClick(R.id.button_login)
    void onLogin(){
        toast("onLogin");
        doNetwork();
    }

    private void  doNetwork(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GitHubService gitHubService =  retrofit.create(GitHubService.class);

        String username = "edgardeng";
        Call<List<Repo>> call =  gitHubService.listRepos(username);

            call.enqueue(new Callback<List<Repo>>(){
                @Override
                public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                    List<Repo> repos = response.body();
                    ILog.w("Retrofit Return",repos.toString());
                }

                @Override
                public void onFailure(Call<List<Repo>> call, Throwable t) {

                }


            });


//            Response<List<Repo>> reponse = call.execute();
//            List<Repo> repos = reponse.body();
//            ILog.w("Retrofit Return",repos.toString());


        // Unable to create converter for java.util.List<com.edgardeng.data.model.Repo>
        Call<String>  userinfo = gitHubService.userInformation(username);
        //java.lang.IllegalArgumentException: Unable to create call adapter for class  String
        ILog.w("Retrofit Return",userinfo.toString());

    }



}
