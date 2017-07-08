package model;

import android.content.Context;

import com.android.hefei.mvvm.basemodle.BaseModel;
import com.android.hefei.mvvm.basemodle.CallBack;

/**
 * Created by hefei on 17/6/18.
 */

public class RedPackageModel extends BaseModel<RedPackageModel.bean, Object, Object> {

    public RedPackageModel(Context context) {
        super(context);
    }

    @Override
    public void getDataFromService(CallBack<bean> callBack) {
        bean bean = new bean();
        bean.isShow = true;
        bean.clickUrl = "www.baidu.com";
        callBack.loadResult(true, bean, null);
    }

    @Override
    public bean getDataFromCache() {
        return null;
    }

    @Override
    public void saveDataToCache(bean bean) {

    }

    public class bean {

        public boolean isShow;
        public String clickUrl;

        @Override
        public String toString() {
            return "bean{" +
                    "isShow=" + isShow +
                    ", clickUrl='" + clickUrl + '\'' +
                    '}';
        }
    }
}
