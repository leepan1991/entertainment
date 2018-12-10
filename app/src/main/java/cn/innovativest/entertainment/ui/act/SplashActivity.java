package cn.innovativest.entertainment.ui.act;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.base.BaseMvpActivity;
import cn.innovativest.entertainment.bean.VersionBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.presenter.SplashPresenter;
import cn.innovativest.entertainment.utils.SPUtils;
import cn.innovativest.entertainment.utils.VersionUtils;
import cn.innovativest.entertainment.view.SplashView;

/**
 * 启动页面
 * Created by victor on 2018/3/6.
 **/
public class SplashActivity extends BaseMvpActivity<SplashView, SplashPresenter> implements SplashView {

    //页面跳转延迟时长
    private static final long VALUE_DELAYED_TIME = 1400;

    @Override
    protected int getStatusBarBackground() {
        return 0;
    }

    @Override
    protected boolean isLightMode() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.checkVersionUpdate();
            }
        }, 500);
    }

    /**
     * 启动主页面
     */
    private void startMainActivity() {
        boolean hasGuide = (boolean)
                SPUtils.get(this, "has_guide", false);
//        startActivity(new Intent(this, hasGuide ?
//                TabActivity.class : GuideActivity.class));
        startActivity(new Intent(this, hasGuide ?
                TabActivity.class : TabActivity.class));
    }

    @Override
    public SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void onCheckVersionDone(HttpRespond<VersionBean> respond) {
        Log.e("zz", "onCheckVersionDone: " + respond.data.getApkUrl());
        if (respond.states == 1) {
            try {
                String currentVersion = VersionUtils.getVersionName(this);
                if (VersionUtils.isUpdate(currentVersion, respond.data.getVerName())) {
                    VersionUtils.showDialog(SplashActivity.this, respond.data);
                } else {
                    startMainActivity();
                    finish();
                }
            } catch (Exception e) {
                onNetworkError(e);
            }
        } else {
            startMainActivity();
            finish();
        }
    }

    @Override
    public void onNetworkError(Throwable t) {
        t.printStackTrace();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
                finish();
            }
        }, VALUE_DELAYED_TIME);
    }
}
