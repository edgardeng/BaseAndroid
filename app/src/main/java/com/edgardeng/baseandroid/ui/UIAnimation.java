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


    @OnClick(R.id.button_move_left_right)
    void moveLeftRight() {
        ObjectAnimator anim = ObjectAnimator .ofFloat(airplane, "TranslationX", 0, 400);
        anim.setDuration(2000);
        anim.start();
        ObjectAnimator anim2 = ObjectAnimator .ofFloat(airplane, "TranslationX", 400, -400);
        anim2.setDuration(2000);
        anim2.setStartDelay(2000);
        anim2.start();  // 并没有改变 TranslationX 的值

    }

    @OnClick(R.id.button_move_up_down)
    void moveUpDown() {
        ObjectAnimator anim = ObjectAnimator .ofFloat(airplane, "TranslationY", 0, 200);
        anim.setDuration(2000);
        anim.start();
        ObjectAnimator anim2 = ObjectAnimator .ofFloat(airplane, "TranslationY", 200, -200);
        anim2.setDuration(2000);
        anim2.setStartDelay(2000);
        anim2.start();
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
        AnimatorSet set = new AnimatorSet() ;

        ObjectAnimator alphaAnim1 = ObjectAnimator .ofFloat(airplane, "alpha", 1.0f, 0f);
        alphaAnim1.setDuration(2000);
        ObjectAnimator alphaAnim2 = ObjectAnimator .ofFloat(airplane, "alpha", 0f, 1.0f);
        alphaAnim2.setDuration(2000);

        set.play(alphaAnim1).before(alphaAnim2); //先执行anim1 动画之后在执行anim2
        set.start();
    }

    @OnClick(R.id.button_alpha_rotate_anim)
    void alphaRotateAnim() {
        AnimatorSet set = new AnimatorSet() ;

        ObjectAnimator rotate = ObjectAnimator .ofFloat(airplane, "rotation", 0, -360f);
        rotate.setDuration(4000);

        ObjectAnimator alpha1 = ObjectAnimator .ofFloat(airplane, "alpha", 1.0f, 0f);
        alpha1.setDuration(2000);
        ObjectAnimator alpha2 = ObjectAnimator .ofFloat(airplane, "alpha", 0f, 1.0f);
        alpha2.setDuration(2000);

        set.play(alpha1).before(alpha2); //先执行anim动画之后在执行anim2
        set.playTogether(rotate,alpha1);
        set.start();


    }

    // move






}
