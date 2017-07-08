package viewmodel;

import android.content.Context;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.hefei.androidmvvm.R;
import com.android.hefei.mvvm.basemodle.BaseModel;
import com.android.hefei.mvvm.basemodle.BaseViewModel;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import model.BannerModel;

/**
 * Created by hefei on 17/7/7.
 */

public class BannerViewModel extends BaseViewModel<BannerModel, List<Integer>, Object, Object, Object> {
    private static final String TAG = "BannerViewModel";

    @IntDef({BannerConfig.LEFT
            , BannerConfig.CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BannerType {
    }


    private Banner mBanner; //banner的控件

    public BannerViewModel(Context context, @BannerType int userType) {
        super(context, userType);
    }

    @Override
    public BannerModel injectModel(Context context, int userType, Object params) {
        return new BannerModel(context,userType);
    }

    @Override
    public int injectView(int userType) {
         return R.layout.view_banner;
//        return 0;
    }

    @Override
    public View injectSubView(int userType) {
        return new Banner(getContext());
    }

    @Override
    public String injectName() {
        return null;
    }

    @Override
    public void bindView(List<Integer> data, Object o) {
        if (data != null) {
            Log.d(TAG, "bindView: --》" + data);
            mBanner.setImages(data);
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();
        }

    }

    @Override
    public void initView(View view) {
          mBanner = (Banner) view.findViewById(R.id.banner);
//        mBanner = (Banner) view;
        mBanner.setImageLoader(new BannerImageLoad());

        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
       // //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        Log.d(TAG,"getUserType()--》"+getUserType());
        mBanner.setIndicatorGravity(getUserType());
//        //banner设置方法全部调用完毕时最后调用
//        mBanner.start();
    }

    class BannerImageLoad extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            int resId = (int) path;
            imageView.setImageDrawable(context.getApplicationContext().getResources().getDrawable(resId));
        }
    }
}
