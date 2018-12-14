package cn.innovativest.entertainment.ui.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.SwitchCompat;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.base.BaseMvpActivity;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.presenter.AuthPresenter;
import cn.innovativest.entertainment.utils.LogUtils;
import cn.innovativest.entertainment.view.AuthView;
import cn.innovativest.entertainment.widget.ClearEditText;

public class ATHAuthActivity extends BaseMvpActivity<AuthView, AuthPresenter> implements AuthView {

    @BindView(R.id.userphone_cet)
    ClearEditText userphoneCet;
    @BindView(R.id.password_cet)
    ClearEditText passwordCet;
    @BindView(R.id.password_again_cet)
    ClearEditText password_again_cet;
    @BindView(R.id.ath_btn)
    Button ath_btn;
    @BindView(R.id.read_agreement_sw)
    SwitchCompat readAgreementSw;
    @BindView(R.id.read_agreement_tv)
    TextView readAgreementTv;
    @BindView(R.id.goto_login_tv)
    TextView gotoLoginTv;

    private boolean isAgreed = true;

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
        return R.layout.activity_auth;
    }

    @Override
    public void initView() {

        readAgreementSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAgreed = b;
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public AuthPresenter initPresenter() {
        return new AuthPresenter();
    }

    private void popDialog(String msg) {
        final AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                .setTitle("提示")//标题
                .setMessage(msg)//内容
                .setIcon(R.mipmap.ic_launcher)//图标
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .create();
        alertDialog1.show();
    }

    @OnClick(R.id.ath_btn)
    public void onAthBtnClicked() {
        if (userphoneCet.getText().length() < 11) {
            toast("请输入正确的手机号码");
        } else if (!isAgreed) {
            toast("请同意用户注册协议");
        } else if (passwordCet.getText().equals("")) {
            toast("请输入密码");
        } else if (password_again_cet.getText().equals("")) {
            toast("请再次输入密码");
        } else if (!passwordCet.getText().toString().trim().equals(password_again_cet.getText().toString().trim())) {
            toast("密码输入不一致");
        } else {
            mPresenter.auth(userphoneCet.getText().toString(), passwordCet.getText().toString(), password_again_cet.getText().toString());
        }
    }


//    @OnClick(R.id.read_agreement_tv)
//    public void onReadAgreementTvClicked() {
//        Intent intent = new Intent(this, HtmlActivity.class);
//        intent.putExtra("id", 2);
//        startActivity(intent);
//    }

    @OnClick(R.id.goto_login_tv)
    public void onGotoLoginTvClicked() {
        finish();
    }


    @Override
    public void authComplete(HttpRespond respond) {
        LogUtils.e(respond.message);
        if (respond.states == 1) {
            popDialog(respond.message);
        } else {
            toast(respond.message);
        }
    }

}
