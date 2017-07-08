package com.android.hefei.mvvm.basemodle;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by hefei on 17/6/4.
 * base
 * 处理view的数据绑定和逻辑操作的类
 * 组件的基础类
 * <p>
 * 对外提供的方法有
 * 第一个泛型标识传入的数据控制model
 * 第二个标识数据刷新的data
 * 第三个标识外部输入的data
 * 第四个标识提供给外部使用的data
 * 第五个标识传入的参数提供给访问网络使用的
 * <p>
 * 主要功能将试图和view绑定。并提供完整的逻辑的组件。
 * 外部只需要获取布局添加
 * 输入对应的事件
 * 监听输出的事件
 * 就可以完成组件所拥有的功能
 * 在界面数据刷新的时候可以通知model层进行数据的更新
 */

public abstract class BaseViewModel<M extends BaseModel, D extends Object, E extends Object, o extends Object, P> implements ViewModelInputEventAndOutputDataInterface<o, D, E> {

    private static final String TAG = "BaseViewModel";

    public static final String EVEN_IS_REFRESH_DATA_START = "EVEN_IS_REFRESH_DATA_START";//刷新开始
    public static final String EVEN_IS_REFRESH_DATA_SUCCESS = "EVEN_IS_REFRESH_DATA_SUCCESS";//刷新成功
    public static final String EVEN_IS_REFRESH_DATA_OVER = "EVEN_IS_REFRESH_DATA_OVER";//刷新结束
    public static final String EVEN_IS_FORCE_REFRESH_DATA_ERROR = "EVEN_IS_FORCE_REFRESH_DATA_ERROR";//强制刷新的时候出现错误
    public static final String EVEN_NOT_IS_FORCE_REFRESH_DATA_ERROR_AND_HAS_CACHE = "EVEN_NOT_IS_FORCE_REFRESH_DATA_ERROR_AND_HAS_CACHE";//有缓存的时候非强制刷新的时候出现错误
    public static final String EVEN_NOT_IS_FORCE_REFRESH_DATA_ERROR_AND_NO_CACHE = "EVEN_NOT_IS_FORCE_REFRESH_DATA_ERROR_AND_NO_CACHE";//没有缓存的时候非强制刷新错误
    public static final String EVEN_NOT_IS_FORCE_REFRESH_AND_HAS_CACHE = "EVEN_NOT_IS_FORCE_REFRESH_AND_HAS_CACHE";//非强制刷新并且用缓存的事件回调

    private static final int DEF_USER_TYPE = 0;//默认的用户类型

    private Context mContext; //上下文环境
    private M mModel;   //数据的控制类
    private View mView; //当前注入的view视图
    private String mBaseViewModelName; //当前组件的名称或者标示
    private D mData;   //当前组件自身获取的数据
    private E mExternalData;//当前组件外部注入的数据
    private o mOutData; //输出的外部数据
    private P mParams;  // 输入的参数
    private int mUserType; //使用当前组件的用户类型
    private ViewModelListener mViewModelListener;//注入的监听
    private ViewModelInputObjectInterface mViewModelInputInterface;//注入的数据

    private boolean mIsForceRefresh;//匿名内部类使用必须是final,所以导致使用不能重新复制提升到成员位置。

    public BaseViewModel(Context context) {
        this(context, DEF_USER_TYPE);
    }

    public BaseViewModel(Context context, int userType) {
        this(context, null, userType, null);
    }

    public BaseViewModel(Context context, P params) {
        this(context, null, DEF_USER_TYPE, params);
    }

    public BaseViewModel(Context context, ViewModelInputObjectInterface<E> viewModelInputInterface) {
        this(context, viewModelInputInterface, DEF_USER_TYPE, null);
    }

    public BaseViewModel(Context context, ViewModelInputObjectInterface<E> viewModelInputInterface, int userType, P params) {
        this.mContext = context;
        this.mViewModelInputInterface = viewModelInputInterface;
        this.mUserType = userType;
        this.mParams = params;
        mBaseViewModelName = injectName();//获取viewmodel的标示
        mModel = injectModel(context, userType, params);//获取注入的model
        getInjectView();
        refreshView(false);//刷新数据
    }

    private void getInjectView() {
        int resId = injectView(mUserType);
        if (resId > 0) {
            mView = LayoutInflater.from(mContext).inflate(injectView(mUserType), null);//获取注入的view

        } else {
            mView = injectSubView(mUserType);
        }
        if (mView == null) {//异常处理
            throw new RuntimeException("you must has a view-----》injectSubView or injectView ");
        }
        initView(mView);//初始化view
    }

    /**
     * 设置viewModel的监听
     *
     * @param viewModelListener
     */
    public void setViewModelListener(ViewModelListener viewModelListener) {
        this.mViewModelListener = viewModelListener;
    }

    /**
     * 注入获取数据的model
     *
     * @param context  上下文
     * @param userType 用户类型
     * @param params   注入的参数
     * @return
     */
    public abstract M injectModel(Context context, int userType, P params);

    /**
     * 注入当前的布局view
     *
     * @param userType 用户类型
     * @return 当前的布局
     */
    public abstract
    @LayoutRes
    int injectView(int userType);

    /**
     * 创建一个view子类可以实现
     *
     * @param userType
     * @return
     */
    public abstract View injectSubView(int userType);

    /**
     * 为当前的ViewModel起名字
     *
     * @return 标示
     */
    public abstract String injectName();

    /**
     * 绑定数据
     *
     * @param data 当前viewModel自身产生的数据
     * @param e    外面注入的data
     */
    public abstract void bindView(D data, E e);

    /**
     * 初始化布局
     *
     * @param view 注入的布局
     */
    public abstract void initView(View view);

    /**
     * 刷新当前界面显示
     */
    public void refreshView(final boolean isForceRefresh) {
        this.mIsForceRefresh = isForceRefresh;
        Log.d(TAG, "refreshView: -isForceRefresh->" + isForceRefresh);
        if (mViewModelInputInterface != null) {
            mExternalData = (E) mViewModelInputInterface.injectInputData();
        }
        if (mModel != null) {
            if (!isForceRefresh) {
                mData = (D) mModel.getDataFromCache();
            }
            mModel.getDataFromService(new CallBack<D>() {

                @Override
                public void loadStart() {
                    refreshStart(mIsForceRefresh);
                    if (mViewModelListener != null) {
                        mViewModelListener.outEvent(BaseViewModel.this, EVEN_IS_REFRESH_DATA_START, null);
                    }
                }

                @Override
                public void loadResult(boolean isSuccess, D d, ErrorData errorData) {
                    refreshResult(mIsForceRefresh, isSuccess, d, errorData);
                    Log.d(TAG, "loadResult: -isSuccess->" + isSuccess + "data-->" + d + "errorData-->" + errorData);
                    if (isSuccess) {
                        mData = d;
                        if (mData != null) {
                            bindView(d, mExternalData);
                            mOutData = outData(BaseViewModel.this, mData, mExternalData);
                        }
                        if (mModel != null) {
                            mModel.saveDataToCache(d);
                        }
                        if (mViewModelListener != null) {
                            mViewModelListener.outEvent(BaseViewModel.this, EVEN_IS_REFRESH_DATA_SUCCESS, d);
                        }
                    } else {//错误信息
                        if (mIsForceRefresh) {//强制刷新但是出现了错误没有获取到数据
                            if (mViewModelListener != null) {
                                mViewModelListener.outEvent(BaseViewModel.this, EVEN_IS_FORCE_REFRESH_DATA_ERROR, errorData);
                            }
                        }
                        if (mData != null && !mIsForceRefresh) {//有缓存但是没有拿到网络上的数据在非强制刷新时候的回调
                            if (mViewModelListener != null) {
                                mViewModelListener.outEvent(BaseViewModel.this, EVEN_NOT_IS_FORCE_REFRESH_DATA_ERROR_AND_HAS_CACHE, errorData);
                            }
                        }
                        if (mData == null && !mIsForceRefresh) {//沒有緩存也沒有拿到數據在非强制刷新的时候
                            if (mViewModelListener != null) {
                                mViewModelListener.outEvent(BaseViewModel.this, EVEN_NOT_IS_FORCE_REFRESH_DATA_ERROR_AND_NO_CACHE, errorData);
                            }
                        }
                    }
                }

                @Override
                public void loadOver() {
                    refreshDataOver(mIsForceRefresh);
                    if (mViewModelListener != null) {
                        mViewModelListener.outEvent(BaseViewModel.this, EVEN_IS_REFRESH_DATA_OVER, null);
                    }
                }
            });
        }
        if (!mIsForceRefresh) {//非强制刷新并且有数据的时候调用绑定数据
            if (mData != null || mExternalData != null) {
                if (mViewModelListener != null) {
                    mViewModelListener.outEvent(BaseViewModel.this, EVEN_NOT_IS_FORCE_REFRESH_AND_HAS_CACHE, null);
                }
            }
            bindView(mData, mExternalData);
            mOutData = outData(BaseViewModel.this, mData, mExternalData);
        }
    }

    /**
     * 刷新开始
     *
     * @param isForceRefresh 是否强制
     */
    public void refreshStart(boolean isForceRefresh) {
        Log.d(TAG, "refreshStart:-->isForceRefresh " + isForceRefresh);
    }

    /**
     * 刷新结果
     *
     * @param isForceRefresh 是否强制
     * @param isSuccess      是否成功
     * @param d              当前刷新的数据
     * @param errorData      刷新错误信息
     */
    public void refreshResult(boolean isForceRefresh, boolean isSuccess, D d, ErrorData errorData) {
        Log.d(TAG, "refreshResult:-->isForceRefresh " + isForceRefresh);
    }

    /**
     * 刷新结束
     *
     * @param isForceRefresh 是否强制刷新
     */
    public void refreshDataOver(boolean isForceRefresh) {
        Log.d(TAG, "refreshDataOver:-->isForceRefresh " + isForceRefresh);
    }

    /**
     * 输入事件
     *
     * @param baseViewModel
     * @param event
     * @param data
     */
    @Override
    public void intPutEvent(BaseViewModel baseViewModel, String event, Object data) {

    }

    /**
     * 向外部提供的数据
     *
     * @param baseViewModel
     * @return
     */
    @Override
    public o outData(BaseViewModel baseViewModel, D mData, E externalData) {
        return null;
    }

    /**
     * 保存外部输入的数据
     *
     * @param externalData
     * @return
     */
    public void saveExternalData(E externalData) {
        if (mModel != null) {
            mModel.saveExternalData(externalData);
        }
    }

    /**
     * 获取外部输入的数据
     *
     * @return
     */
    public E getExternalFromCacheData() {
        if (mModel != null) {
            return (E) mModel.getExternalData();
        }
        return null;
    }

    /**
     * 通知model刷新存储的数据
     *
     * @param data
     * @param externalData
     */
    public void notifyDataChange(D data, E externalData) {
        if (mModel == null || data == null || externalData == null) {
            return;
        }
        if (data != null) {
            mModel.saveDataToCache(data);
        }
        if (externalData != null) {
            mModel.saveExternalData(externalData);
        }
    }


    /**
     * 提供get方法获取当前ViewModel中注入的一些变量
     */

    public Context getContext() {
        return mContext;
    }

    /**
     * 获取注入的model 这个一般不用对外暴露。外面减少对model层的控制
     *
     * @return
     */
    public M getModel() {
        return mModel;
    }

    public View getView() {
        return mView;
    }

    public String getBaseViewModelName() {
        return mBaseViewModelName;
    }

    public D getData() {
        return mData;
    }

    public E getExternalData() {
        return mExternalData;
    }

    public o getOutData() {
        return mOutData;
    }

    public P getParams() {
        return mParams;
    }

    /**
     * 刷新参数的方法
     *
     * @return
     */
    public void refreshParams(P params) {
        if (params != null) {
            this.mParams = params;
            if (mModel != null) {
                mModel.setParams(params);
            }
        }
    }

    /**
     * 刷新类型
     *
     * @param userType
     */
    public void refreshUserType(int userType) {
        this.mUserType = userType;
        getInjectView();//重新生成view
        if (mModel != null) {
            mModel.setUserType(userType);
        }
    }

    public int getUserType() {
        return mUserType;
    }

    public ViewModelListener getViewModelListener() {
        return mViewModelListener;
    }

}
