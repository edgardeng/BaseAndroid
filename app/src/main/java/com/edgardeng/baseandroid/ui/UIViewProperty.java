package com.edgardeng.baseandroid.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import com.edgardeng.baseandroid.BaseActivity;
import com.edgardeng.baseandroid.R;
import com.edgardeng.util.ILog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * View Property Test
 *
 * Created by dengxixi on 2017/11/3.
 */

public class UIViewProperty extends BaseActivity {


    @BindView(R.id.image_ariplane)
    ImageView airplane;

    /** Life Circle */
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_view_property);
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

    /** Event Response */

    @OnClick(R.id.button_move_down)
    void moveDown() {
        float y = airplane.getY();
        ILog.w("getY" + y);
        airplane.setTranslationY(20);
        airplane.setY(y + 20);
    }

    @OnClick(R.id.button_move_up)
    void moveUp() {
        float y = airplane.getY();
        ILog.w("getY" + y);
        airplane.setTranslationY(0 - 20); // 相对于 原始的Y setY
        airplane.setY(y - 20); // 改变 Y
    }

    @OnClick(R.id.button_move_left)
    void moveLeft() {
        float x = airplane.getX();
        ILog.w("getx" + x);
        airplane.setTranslationX(x - 20); // 相当于 setX
    }

    @OnClick(R.id.button_move_right)
    void moveRight() {
        float x = airplane.getX();
        ILog.w("getx" + x);
        airplane.setTranslationX(x + 20);
    }

    @OnClick(R.id.button_rotate_anti)
    void rotateAnti() {
        float z = airplane.getRotation();
        airplane.setRotation(30);
        airplane.setRotation(z + 30);
    }

    @OnClick(R.id.button_rotate_clock)
    void rotateClock() {
        float z = airplane.getRotation();
        airplane.setRotation(-30);
        airplane.setRotation(z - 30);
    }

    @OnClick(R.id.button_alpha_down)
    void alphaDown() {
        float alpha = airplane.getAlpha();
        if (alpha > 0) {
            airplane.setAlpha(alpha - 0.1f); // 相当于 setX
        }
    }

    @OnClick(R.id.button_alpha_up)
    void alphaUp() {
        float alpha = airplane.getAlpha();
        if (alpha < 1) {
            airplane.setAlpha(alpha + 0.1f); // 相当于 setX
        }
    }


    /** override method */


    /** private method */


}
