package com.android.hefei.androidmvvm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.youth.banner.BannerConfig;

import viewmodel.BannerViewModel;

public class BannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_bannerContainer);
        BannerViewModel bannerViewModel = new BannerViewModel(this, BannerConfig.CENTER);//添加banner组件一
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 100;
        linearLayout.addView(bannerViewModel.getView(), layoutParams);
        BannerViewModel bannerViewModel2 = new BannerViewModel(this, BannerConfig.LEFT);//添加banner组件二
        linearLayout.addView(bannerViewModel2.getView(), layoutParams);
    }
}
