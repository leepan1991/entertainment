package cn.innovativest.entertainment.ui.act;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.base.BaseMvpActivity;
import cn.innovativest.entertainment.bean.LoginBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.presenter.LoginPresenter;
import cn.innovativest.entertainment.utils.SPUtils;
import cn.innovativest.entertainment.view.LoginView;
import cn.innovativest.entertainment.widget.ClearEditText;

public class LoginActivity extends BaseMvpActivity<LoginView, LoginPresenter> implements LoginView {
    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CODE = 2;
    public static final String IS_LOGINED = "isLogined";

    @BindView(R.id.userphone_cet)
    ClearEditText userphoneCet;
    @BindView(R.id.password_cet)
    ClearEditText passwordCet;
    @BindView(R.id.resetpwd_tv)
    TextView resetpwdTv;
    @BindView(R.id.register_tv)
    TextView registerTv;
    @BindView(R.id.login_btn)
    Button loginBtn;

    @Override
    protected int getStatusBarBackground() {
        return Color.TRANSPARENT;
    }

    @Override
    protected boolean isLightMode() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }


    @OnClick(R.id.login_btn)
    public void onLoginClicked() {
        if (TextUtils.isEmpty(userphoneCet.getText()) || userphoneCet.getText().length() < 11) {
            toast("请输入正确的手机号");
        } else if (TextUtils.isEmpty(passwordCet.getText())) {
            toast("请输入正确的密码");
        } else {
            showProgressDialog("正在登录");
            mPresenter.doLogin(userphoneCet.getText().toString(),
                    passwordCet.getText().toString());
        }
    }

    public static boolean isLogin(Context context) {
        return (boolean) SPUtils.get(context, "is_login", false);
    }

    public static void jumpToLogin(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    public void loginSuccess(HttpRespond<LoginBean> respond) {
        dismissProgressDialog();
        int itemId = getIntent().getIntExtra("item_id", 0);
        toast(respond.message);
        if (respond.states == 0)
            return; // 登录信息错误
        // 登录成功后修改本地登录标记，并存储用户名(手机号)、密码、token、加解密参数
        SPUtils.put(this, "is_login", true);
        SPUtils.put(this, "user_phone", userphoneCet.getText().toString());
        SPUtils.put(this, "user_cookie_pre", respond.data.getCookie_ico());
        Intent intent = new Intent();
        intent.putExtra("item_id", itemId);
        intent.putExtra(LoginActivity.IS_LOGINED, true);
        setResult(LoginActivity.RESULT_CODE, intent);
        finish();
    }

//    @OnClick(R.id.resetpwd_tv)
//    public void onResetpwdTvClicked() {
//        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
//        if (!TextUtils.isEmpty(userphoneCet.getText())) {
//            intent.putExtra("phone_num", userphoneCet.getText().toString());
//        }
//        startActivity(intent);
//    }

    @OnClick(R.id.register_tv)
    public void onRegisterTvClicked() {
        startActivity(new Intent(LoginActivity.this, ATHAuthActivity.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(LoginActivity.IS_LOGINED, false);
        setResult(LoginActivity.RESULT_CODE, intent);
        finish();
    }
}
