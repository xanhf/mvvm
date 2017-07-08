package model;

import android.content.Context;

import com.android.hefei.androidmvvm.R;
import com.android.hefei.mvvm.basemodle.BaseModel;
import com.android.hefei.mvvm.basemodle.CallBack;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hefei on 17/6/18.
 */

public class BannerModel extends BaseModel<List<Integer>, Object, Object> {

    public BannerModel(Context context, int userType) {
        super(context, userType);
    }

    @Override
    public void getDataFromService(CallBack<List<Integer>> callBack) {
        List<Integer> list = new ArrayList<>();
        if (getUserType() == BannerConfig.CENTER) {
            list.add(R.drawable.xiaoming1);
            list.add(R.drawable.xiaoming2);
            list.add(R.drawable.xiaoming3);
            list.add(R.drawable.xiaoming4);
        } else {
            list.add(R.drawable.kaka1);
            list.add(R.drawable.kaka2);
            list.add(R.drawable.kaka3);
            list.add(R.drawable.kaka4);
        }

        callBack.loadResult(true, list, null);
    }

    @Override
    public List<Integer> getDataFromCache() {
        return null;
    }

    @Override
    public void saveDataToCache(List<Integer> bean) {

    }
}
