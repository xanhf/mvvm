package com.android.hefei.mvvm.basemodle;

import android.view.ViewGroup;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.Map;

/**
 * Created by hefei on 2017/6/5.
 * viewModel的管理着
 * 可以根据自己的需要进行扩充。增删改查等
 * 用来批量管理组件
 * 可以批量刷新和排序
 */

public class ViewModelManager {

    private ArrayList<BaseViewModel> mListViewModel = null;

    public ViewModelManager() {
        mListViewModel = new ArrayList<>();
    }

    /**
     * 注册一个
     *
     * @param baseViewModel
     */
    public void registerViewModel(BaseViewModel baseViewModel) {
        if (baseViewModel == null) {
            return;
        }
        mListViewModel.add(baseViewModel);
    }

    /**
     * 注册多个批量添加
     *
     * @param baseViewModel
     */
    public void registerViewModelList(BaseViewModel... baseViewModel) {
        if (baseViewModel != null) {
            for (BaseViewModel viewModel : baseViewModel) {
                if (viewModel != null) {
                    mListViewModel.add(viewModel);
                }
            }
        }
    }

    /**
     * 注册多个
     *
     * @param listViewModel
     */
    public void registerViewModelList(ArrayList<BaseViewModel> listViewModel) {
        this.mListViewModel = listViewModel;
    }

    /**
     * 添加所有的view
     *
     * @param viewGroup
     */
    public void addViewModelToViewGroup(ViewGroup viewGroup) {
        if (mListViewModel == null || mListViewModel.size() < 0 || viewGroup == null) {
            return;
        }
        for (int i = 0; i < mListViewModel.size(); i++) {
            viewGroup.addView(mListViewModel.get(i).getView(), i);
        }
    }

    /**
     * 刷新所有的view
     */
    public void refreshALLViewModel() {
        for (int i = 0; i < mListViewModel.size(); i++) {
            mListViewModel.get(i).refreshView(true);
        }
    }

    /**
     * 获取当前注册的组件数
     *
     * @return size
     */
    public int getListViewModelSize() {
        if (mListViewModel != null) {
            return mListViewModel.size();
        }
        return 0;
    }

    /**
     * 删除一个组件
     *
     * @param baseViewModel
     * @return
     */
    public boolean removerViewModel(BaseViewModel baseViewModel) {
        if (baseViewModel == null || mListViewModel == null || mListViewModel.size() < 0) {
            return false;
        }
        if (!mListViewModel.contains(baseViewModel)) {
            return false;
        }
        return mListViewModel.remove(baseViewModel);
    }
}
