package viewpresent;

import com.android.hefei.mvvm.basemodle.BasePresent;
import com.android.hefei.mvvm.basemodle.CallBack;

/**
 * Created by hefei on 17/6/17.
 * <p>
 * 登录的控制器
 */

public interface LoginControl {

    interface LoginModel {
        void login(String userName, String passWord, CallBack<Boolean> callBack);
    }

    interface LoginView {

        void loginStart();

        void loginResult(boolean isSuccess);

        void loginOver();

    }

    abstract class LoginPresent extends BasePresent<LoginModel, LoginView> {
       public abstract void login(String userName, String passWord);
    }


}
