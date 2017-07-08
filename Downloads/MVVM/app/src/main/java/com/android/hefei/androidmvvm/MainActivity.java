package com.android.hefei.androidmvvm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.hefei.mvvm.basemodle.BaseViewModel;
import com.android.hefei.mvvm.basemodle.ViewModelInputObjectInterface;
import com.android.hefei.mvvm.basemodle.ViewModelListener;
import com.google.android.gms.common.api.GoogleApiClient;

import viewmodel.LoginViewModel;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private EditText etUserName;
    private EditText etPassWord;
    private String password;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        linearLayout = (LinearLayout) findViewById(R.id.activity_main_container);
        etUserName = (EditText) findViewById(R.id.et_username);
        etPassWord = (EditText) findViewById(R.id.et_password);
        //添加登录的组件
        final LoginViewModel loginViewModel = new LoginViewModel(this, new ViewModelInputObjectInterface<LoginViewModel.LoginBean>() {
            @Override
            public LoginViewModel.LoginBean injectInputData() {//传入所需要的数据
                LoginViewModel.LoginBean loginBean = new LoginViewModel.LoginBean();
                loginBean.passWord = password;//密码
                loginBean.userName = userName;//账号
                return loginBean;
            }
        });
        loginViewModel.setViewModelListener(new ViewModelListener() {
            @Override
            public void outEvent(BaseViewModel baseViewModel, String event, Object object) {
                if (LoginViewModel.Event_LOGIN_RESULT.equals(event)) {
                    if ((boolean) object)
                        startActivity(new Intent(MainActivity.this, BannerActivity.class));
                }
            }

            @Override
            public void outOnclickEvent(BaseViewModel baseViewModel, View view, Object onclickData) {//注入监听,拿到跑出的点击事件
                password = etPassWord.getText().toString().trim();
                userName = etUserName.getText().toString().trim();
                loginViewModel.refresh();//刷新数据。每次注入数据发生改变的时候刷新当前组件。(fixed 回删的时候也要监听)

            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(loginViewModel.getView(), 2, layoutParams);

    }

}
