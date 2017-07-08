package viewpresent;

import com.android.hefei.mvvm.basemodle.CallBack;

/**
 * Created by hefei on 17/6/17.
 */

public class LoginModelImp implements LoginControl.LoginModel {

    @Override
    public void login(String userName, String passWord, CallBack<Boolean> callBack) {
        callBack.loadResult(true, true, null);
    }

}
