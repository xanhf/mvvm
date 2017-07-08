package com.android.hefei.mvvm.basemodle;

/**
 * Created by hefei on 17/6/4.
 * 需要监听的数据类型
 * 数据发生改变的监听
 */

public interface DataChangeLister<D, E> {
    /**
     * 监听数据的变化
     *
     * @param data         自身获取的数据发生变化
     * @param externalData 外面注入的数据发生变化
     */
    void dataChanged(D data, E externalData);
}
