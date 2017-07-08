package com.android.hefei.mvvm.basemodle;

/**
 * Created by hefei on 17/5/6.
 * 获取异步数据的回调
 * 1.加载开始
 * 2.加载结果(成功,失败)
 * 3.加载完成
 */

public interface CallBack<D extends Object> {

    /**
     * 开始加载
     */
    void loadStart();

    /**
     * 加载的结果
     * @param isSuccess
     * @param d
     * @param errorData
     */
    void loadResult(boolean isSuccess, D d, ErrorData errorData);

    /**
     * 加载结束
     */
    void loadOver();
}
