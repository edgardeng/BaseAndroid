package com.edgardeng.baseandroid.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.edgardeng.baseandroid.BaseActivity;
import com.edgardeng.baseandroid.BuildConfig;
import com.edgardeng.baseandroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UILogin extends BaseActivity {


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
        setContentView(R.layout.page_login);
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


    @OnClick(R.id.button_login)
    void onLogin(){
        toast("onLogin");
    }





}
