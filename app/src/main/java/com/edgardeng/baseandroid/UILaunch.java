package com.edgardeng.baseandroid;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.edgardeng.baseandroid.ui.UIApi;
import com.edgardeng.util.ILog;
import com.edgardeng.widget.BezierAnimationView.ViewPath;
import com.edgardeng.widget.BezierAnimationView.ViewPathEvaluator;
import com.edgardeng.widget.BezierAnimationView.ViewPoint;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UILaunch extends BaseActivity {

    @BindView(R.id.button_start)
    Button buttonStart;
    @BindView(R.id.image_ariplane)
    ImageView imageAir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_launch);
        ButterKnife.bind(this);
        ILog.i(BuildConfig.DEBUG ? "debug onCreate" : "release onCreate");
//        new Update().check(this); // fir.im 停止免费服务
//        if (BuildConfig.DEBUG)
//            toast("debug version");
//        else
//            toast("release version");

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
                forward(UIApi.class);
           }
       },500);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ILog.i("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick(R.id.button_start)
    void onLogin() {
        ILog.i("button_start");
        fly();
    }


    /* 贝叶斯 曲线运动*/
    private void  fly () {
        ViewPath path = new ViewPath(); //偏移坐标
            path.moveTo(0,0);
//            path.lineTo(0,500);
            path.curveTo(100,200,150,300,200,400);
            path.curveTo(200,400,350,300,400,200);

//            ObjectAnimator anim = ObjectAnimator.ofObject(this,"fabLoc",new ViewPathEvaluator(),path.getPoints().toArray());
//            anim.setInterpolator(new AccelerateDecelerateInterpolator());
//            anim.setDuration(3000);
//            anim.start();

    }
    public void setFabLoc(ViewPoint newLoc){
//        imageAir.setTranslationX(newLoc.x);
//        imageAir.setTranslationY(newLoc.y);
    }


}
