package com.android.hefei.mvvm.basemodle;

import android.support.annotation.NonNull;

/**
 * Created by hefei on 17/6/11.
 * mvp
 * <p>
 * 基础的控制器
 * 1. M 范型标示注入的ModelAPI
 * 2. V 标示注入的view层调用的控制接口
 */

public abstract class BasePresent<M, V> {

   public M mModelAPI;

    public V mViewShow;

    /**
     * 子类注入对应的modelAPI和view的对应展示接口
     *
     * @param modelAPI present所需要的api获取数据的接口
     * @param viewShow present 所控制的view的方法
     */
    public void setViewAndModelAPI(@NonNull M modelAPI, @NonNull V viewShow) {
        this.mModelAPI = modelAPI;
        this.mViewShow = viewShow;
    }
}
