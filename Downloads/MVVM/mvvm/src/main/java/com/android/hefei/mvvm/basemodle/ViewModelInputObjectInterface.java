package com.android.hefei.mvvm.basemodle;

/**
 * Created by hefei on 17/6/4.
 * <p>
 * 给组件注入外部数据的接口
 * 范型输入的数据类型
 */

public interface ViewModelInputObjectInterface<E> {

    /**
     * 输入的数据
     *
     * @return 注入的数据
     */
    E injectInputData();
}
