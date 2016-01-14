package com.edgardeng.baseandroid;

import android.os.Bundle;

import com.edgardeng.data.model.Update;
import com.edgardeng.ui.base.BaseActivity;

public class UILaunch extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_launch);

        new Update().check(this);


        if(BuildConfig.DEBUG)
            toast("debug");
        else
            toast("release");

    }



}
