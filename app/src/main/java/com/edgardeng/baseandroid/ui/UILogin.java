package com.edgardeng.baseandroid.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.edgardeng.baseandroid.BaseActivity;
import com.edgardeng.baseandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UILogin extends BaseActivity {


    @BindView(R.id.imageView_user)
    ImageView imageViewUser;
    @BindView(R.id.editText_user)
    EditText editTextUser;
    @BindView(R.id.imageView_password)
    ImageView imageViewPassword;
    @BindView(R.id.editText_password)
    EditText editTextPassword;
    @BindView(R.id.button_forget)
    Button buttonForget;
    @BindView(R.id.button_login)
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
