package com.edgardeng.baseandroid;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.edgardeng.baseandroid.ui.UIAnimation;
import com.edgardeng.baseandroid.ui.UILogin;
import com.edgardeng.baseandroid.ui.UIViewProperty;
import com.edgardeng.util.ILog;
import com.edgardeng.widget.BezierAnimationView.ViewPoint;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UILaunch extends BaseActivity {

    @BindView(R.id.button_start)
    Button buttonStart;
    @BindView(R.id.image_ariplane)
    ImageView imageAir;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.imageViewIndicator0)
    ImageView indicator0;
    @BindView(R.id.imageViewIndicator1)
    ImageView indicator1;
    @BindView(R.id.imageViewIndicator2)
    ImageView indicator2;
    private ImageView indicators[];


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

//        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//        if (sp.getBoolean(SettingsUtil.KEY_FIRST_LAUNCH, true)) {
//
//            setContentView(R.layout.activity_onboarding);
//
//            // Do other things here.
//        } else {
//
//            navigateToMainActivity();
//initViewPager
//            finish();
//        }


        imageAir.setRotation(200); // 150 + 3
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                fly();
                initViewPager();
            }
        }, 100);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.button_start)
    void onLogin() {
        ILog.i("button_start");
//        forward(UILogin.class);

        forward(UIAnimation.class);

    }

    /* 贝叶斯 曲线运动*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void  fly () {
        float origin_x = imageAir.getX();
        float origin_y = imageAir.getY();

        Path path = new Path();
        path.moveTo(origin_x,origin_y);
        path.quadTo(origin_x-100, origin_y-100, origin_x-300, origin_y);
        path.quadTo(origin_x-400, origin_y+100, origin_x-360, origin_y+200);
        path.quadTo(origin_x-220, origin_y+450, origin_x-30, origin_y+302);

        ObjectAnimator anim_move = ObjectAnimator.ofFloat(imageAir, View.X, View.Y, path);
        anim_move.setDuration(3000);
        ObjectAnimator anim_rotate = ObjectAnimator .ofFloat(imageAir, "rotation", 200f, -65f);
        anim_rotate.setDuration(3000);

        anim_move.start();
        anim_rotate.start();


    }

    private void initViewPager() {

        indicators = new ImageView[]{indicator0, indicator1, indicator2};
        int imgs[] = {R.mipmap.im_launcher_a,R.mipmap.im_launcher_b,R.mipmap.im_launcher_c};
        SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(imgs);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                int colorUpdate = (Integer) new ArgbEvaluator().evaluate(positionOffset, bgColors[position], bgColors[position == 2 ? position : position + 1]);
//                viewPager.setBackgroundColor(colorUpdate);
                ILog.w("onPageScrolled " + position); // 滑动变化
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /** 控制指示器*/
    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.im_indicator_selected : R.drawable.im_indicator_unselected
            );
        }
    }

    class SimpleViewPagerAdapter extends PagerAdapter {
        private int[] imgs;
        private List<ImageView> imageViews;

        public SimpleViewPagerAdapter(int[] imgs) {
            this.imgs = imgs;
            imageViews = new ArrayList<>();
        }

        // item的个数
        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        // 初始化item布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(UILaunch.this);
            view.setImageResource(imgs[position]);
            container.addView(view);
            imageViews.add(view);
            return view;
        }

        // 销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
            imageViews.remove(position);
        }

    }


}
