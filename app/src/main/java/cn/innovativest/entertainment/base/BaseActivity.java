package cn.innovativest.entertainment.base;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import cn.innovativest.entertainment.utils.EventBusUtils;
import cn.innovativest.entertainment.utils.StringUtils;

/**
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initBasePresenter();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (isRegisterEventBus()) {
            EventBusUtils.register(this);
        }
//        setStatusBar(getStatusBarBackground(), isLightMode());
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
        initCreate(savedInstanceState);

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 子类是否需要透明状态栏
     *
     * @return true 需要透明状态栏，反之不透明
     */
    protected abstract int getStatusBarBackground();

    protected abstract boolean isLightMode();

    /**
     * 设置透明状态栏
     */
    public void setStatusBar(int color, boolean isLightMode) {
        //大于5.0才设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //大于6.0 并且是亮色模式
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isLightMode) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            getWindow().setStatusBarColor(color);
        }
    }

    public void initCreate(Bundle savedInstanceState) {

    }

    public void initBasePresenter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtils.unregister(this);
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void initView();

    public void initData() {

    }

    public void initListener() {

    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(BaseEvent event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(BaseEvent event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(BaseEvent event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(BaseEvent event) {

    }

    public void showProgressDialog(String msg) {

        if (mProgressDialog == null) {
            if (StringUtils.isEmpty(msg)) {
                mProgressDialog = ProgressDialog.show(this, "", "加载中...", true, true);
                return;
            }
            mProgressDialog = ProgressDialog.show(this, "", msg, true, true);
        } else {
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}

