package com.android.hefei.mvvm.basemodle;

import android.view.View;

/**
 * Created by hefei on 17/6/4.
 * <p>
 * 注入到组件中的监听者
 * <p>
 * 1.outEvent 输出的事件
 * 2.outOnclickEvent 输出的点击事件 由于点击事件比较多所以单独回调,其他一般事件走上面的回调
 */

public interface ViewModelListener {

    /**
     * 输出的事件
     *
     * @param baseViewModel 输出事件的组件
     * @param event         输出的事件
     * @param object        输出事件对应的数据
     */
    void outEvent(BaseViewModel baseViewModel, String event, Object object);

    /**
     * 输出的点击事件
     *
     * @param baseViewModel 输出的组件
     * @param view          输出点击事件的View
     * @param onclickData   输出的数据
     */
    void outOnclickEvent(BaseViewModel baseViewModel, View view, Object onclickData);
}
