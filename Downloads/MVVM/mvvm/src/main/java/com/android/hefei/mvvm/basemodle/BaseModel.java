package com.android.hefei.mvvm.basemodle;

import android.content.Context;

/**
 * Created by hefei on 17/6/4.
 * model的基础类(控制数据的获取保存和监控)
 * <p>
 * 控制数据获取数据的module顶层类
 * <p>
 * 主要的范性
 * 1.D 通过网络获取的数据的数据类型
 * 2.E 外部传入的数据的数据类型(在对应的数据模块数据不能通过自身获取的时候用)
 * 3.P 表示外面传入的参数类型,model从网上获取数据所需要依赖的一些外部参数
 * <p>
 * 主要的方法
 * 1.异步服务器获取数据
 * 2.同步缓存获取数据
 * 3.保存数据
 * 4.监听数据变化
 * <p>
 * 这里面的数据是关于view样式展示的数据可以是一些标记位等组成的一个数据格式
 * <p>
 * 两个泛型一个是从网上获取的data
 * 一个是从外部输入的data
 */

public abstract class BaseModel<D, E, P> {

    private Context mContext;
    private DataChangeLister mDataChangeLister;
    private P mParams;
    private int mUserType;

    public BaseModel(Context context, P params, int userType) {
        this.mContext = context;
        this.mParams = params;
        this.mUserType = userType;
    }

    public BaseModel(Context context,int userType) {
        this(context, null, userType);
    }

    public BaseModel(Context context) {
        this(context, null, 0);
    }

    /**
     * 从网上获取数据
     *
     * @return
     */
    public abstract void getDataFromService(CallBack<D> callBack);

    /**
     * 从本地获取数据
     *
     * @return d
     */
    public abstract D getDataFromCache();

    /**
     * 保存数据
     *
     * @return d
     */
    public abstract void saveDataToCache(D d);

    /**
     * 保存外部输入的data
     *
     * @param externalData data
     */
    public void saveExternalData(E externalData) {

    }

    /**
     * 获取外部保存的数据
     */
    public E getExternalData() {
        return null;
    }

    /**
     * 数据发生改变的监听
     *
     * @return c监听变化的数据
     */
    public void registerDataChange(DataChangeLister<D, E> dataChangeLister) {
        this.mDataChangeLister = dataChangeLister;
    }

    public Context getContext() {
        return mContext;
    }

    public DataChangeLister getDataChangeLister() {
        return mDataChangeLister;
    }

    public P getParams() {
        return mParams;
    }

    public void setParams(P mParams) {
        this.mParams = mParams;
    }

    public int getUserType() {
        return mUserType;
    }

    public void setUserType(int userType) {
        this.mUserType = userType;
    }
}
