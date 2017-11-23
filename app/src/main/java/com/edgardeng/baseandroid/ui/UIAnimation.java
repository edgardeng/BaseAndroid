package com.edgardeng.baseandroid.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edgardeng.baseandroid.BaseActivity;
import com.edgardeng.baseandroid.R;
import com.edgardeng.data.model.Repo;
import com.edgardeng.data.model.User;
import com.edgardeng.net.api.GithubApi;
import com.edgardeng.net.api.UserApi;
import com.edgardeng.net.remote.ApiManager;
import com.edgardeng.util.FileUtils;
import com.edgardeng.util.ILog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API 及 Retrofit 测试
 *
 * Created by dengxixi on 2017/11/3.
 */

public class UIAnimation extends BaseActivity {


    @BindView(R.id.image_ariplane)
    ImageView airplane;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_view_animation);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick(R.id.button_rotate_anti)
    void rotateAnti() {
        ObjectAnimator anim = ObjectAnimator .ofFloat(airplane, "rotation", 0, 360f);
        anim.setDuration(3000);
        anim.start();
    }

    @OnClick(R.id.button_rotate_clock)
    void rotateClock() {
        ObjectAnimator anim = ObjectAnimator .ofFloat(airplane, "rotation", 0, -360f);
        anim.setDuration(3000);
        anim.start();
    }

    @OnClick(R.id.button_alpha_anim)
    void alphaAnim() {
        ObjectAnimator anim = ObjectAnimator .ofFloat(airplane, "alpha", 1.0f, 0f);
        anim.setDuration(3000);
        anim.start();
    }

    @OnClick(R.id.button_alpha_rotate_anim)
    void alphaRotateAnim() {
        AnimatorSet set = new AnimatorSet() ;

        ObjectAnimator rotate = ObjectAnimator .ofFloat(airplane, "rotation", 0, -360f);
        rotate.setDuration(3000);

        ObjectAnimator alpha = ObjectAnimator .ofFloat(airplane, "alpha", 1.0f, 0f);
        alpha.setDuration(3000);

        set.play(rotate).before(alpha); //先执行anim动画之后在执行anim2
        set.start();

//        set.playTogether(rotate,alpha);



    }

    // move






}
