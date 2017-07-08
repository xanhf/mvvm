package com.android.hefei.mvvm.basemodle;

/**
 * Created by hefei on 17/6/4.
 * <p>
 * 组件对外暴露的数据和方法的接口
 * 主要范型的意思
 * 1.O 对外输出的数据
 * 2.D 组件自身获取的数据
 * 3.E 外部对组件输入的数据
 * 主要是用来对外输出数据。和对外暴露输入的事件
 * 1.intPutEvent 主要是外部调用viewmodel的输入事件的方法。
 * 2.outdata  主要是组件提供给外部使用的数据。
 */

public interface ViewModelInputEventAndOutputDataInterface<O, D, E> {

    /**
     * 输入的事件,对外部输入的事件统一收口
     *
     * @param baseViewModel 输入事件的组件
     * @param event         输入的事件
     * @param data          对应的数据
     */
    void intPutEvent(BaseViewModel baseViewModel, String event, Object data);

    /**
     * 组件对外输出的数据
     *
     * @param baseViewModel 输出数据的组件
     * @param data          当前组件所可以获取的数据(只是网上的数据)
     * @param externalData  外部提供给当前组件的数据
     * @return 返回该数据
     */
    O outData(BaseViewModel baseViewModel, D data, E externalData);
}
