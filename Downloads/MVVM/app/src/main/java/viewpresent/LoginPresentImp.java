package viewpresent;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.hefei.mvvm.basemodle.CallBack;
import com.android.hefei.mvvm.basemodle.ErrorData;

/**
 * Created by hefei on 17/6/17.
 * 登录的实际控制类
 */

public class LoginPresentImp extends LoginControl.LoginPresent {

    public LoginPresentImp(@NonNull LoginControl.LoginView view, Context context) {
        setViewAndModelAPI(new LoginModelImp(), view);
    }

    @Override
    public void login(String userName, String passWord) {
        mModelAPI.login(userName, passWord, new CallBack<Boolean>() {
            @Override
            public void loadStart() {
                mViewShow.loginStart();
            }

            @Override
            public void loadResult(boolean isSuccess, Boolean aBoolean, ErrorData errorData) {
                if (isSuccess) {
                    mViewShow.loginResult(aBoolean);
                }
            }

            @Override
            public void loadOver() {
                mViewShow.loginOver();
            }
        });
    }
}
