package viewmodel;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.hefei.androidmvvm.R;
import com.android.hefei.mvvm.basemodle.BaseModel;
import com.android.hefei.mvvm.basemodle.BaseViewModel;
import com.android.hefei.mvvm.basemodle.ViewModelInputObjectInterface;
import com.android.hefei.mvvm.basemodle.ViewModelListener;

import viewpresent.LoginControl;
import viewpresent.LoginPresentImp;

/**
 * Created by hefei on 17/6/17.
 * <p>
 * 登录的组件
 */

public class LoginViewModel extends BaseViewModel<BaseModel, Object, LoginViewModel.LoginBean, Object, Object> implements LoginControl.LoginView {

    private static final String TAG_NAME = "com.android.hefei.androidmvvm.LoginViewModel";
    public static final String Event_LOGIN_RESULT = "Event_LOGIN_RESULT";

    private LoginPresentImp loginPresentImp;
    private LoginViewModel.LoginBean loginBean;

    public LoginViewModel(Context context, ViewModelInputObjectInterface<LoginViewModel.LoginBean> viewModelInputObjectInterface) {
        super(context, viewModelInputObjectInterface);
        loginPresentImp = new LoginPresentImp(this, context);
    }

    @Override
    public BaseModel injectModel(Context context, int userType, Object params) {
        return null;
    }

    @Override
    public int injectView(int userType) {
        return R.layout.view_login_button;
    }

    @Override
    public View injectSubView(int userType) {
        return null;
    }

    @Override
    public String injectName() {
        return TAG_NAME;
    }

    @Override
    public void bindView(Object data, LoginViewModel.LoginBean o) {
        this.loginBean = o;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginBean != null) {
                    ViewModelListener viewModelListener = getViewModelListener();
                    if (viewModelListener != null) {
                        viewModelListener.outOnclickEvent(LoginViewModel.this, view, null);
                    }
                    if (!TextUtils.isEmpty(loginBean.userName) && !TextUtils.isEmpty(loginBean.passWord)) {
                        loginPresentImp.login(loginBean.userName, loginBean.passWord);
                    } else {
                        Toast.makeText(getContext(), "请确认您的用户名密码正确", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    public void loginStart() {

    }

    @Override
    public void loginResult(boolean isSuccess) {
        if (isSuccess) {//登录成功
            Toast.makeText(getContext(), "成功", Toast.LENGTH_SHORT).show();
            if (getViewModelListener() != null) {
                getViewModelListener().outEvent(this, Event_LOGIN_RESULT, isSuccess);
            }
        }
    }

    @Override
    public void loginOver() {

    }

    /**
     * 刷新外部传入的数据
     */
    public void refresh() {
        refreshView(false);
    }

    public static class LoginBean {
        public String userName;
        public String passWord;
    }
}
