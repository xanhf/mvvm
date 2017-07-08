package viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.hefei.androidmvvm.R;
import model.RedPackageModel;
import com.android.hefei.mvvm.basemodle.BaseViewModel;

/**
 * Created by hefei on 17/6/18.
 * <p>
 * 红包的组件
 */

public class RedPackageViewModel extends BaseViewModel<RedPackageModel, RedPackageModel.bean, Object, Object, Object> {

    RedPackageModel.bean data;

    public RedPackageViewModel(Context context) {
        super(context);
    }

    @Override
    public RedPackageModel injectModel(Context context, int userType, Object params) {
        return new RedPackageModel(context);
    }

    @Override
    public int injectView(int userType) {
        return R.layout.view_red;
    }

    @Override
    public View injectSubView(int userType) {
        return null;
    }

    @Override
    public String injectName() {
        return null;
    }

    @Override
    public void bindView(RedPackageModel.bean data, Object o) {
        this.data = data;
        if (data != null && data.isShow) {//显示
            getView().setVisibility(View.VISIBLE);
        } else {//隐藏
            getView().setVisibility(View.GONE);
        }
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.red_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data != null && data.isShow) {
                    Toast.makeText(getContext(), data.clickUrl, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
