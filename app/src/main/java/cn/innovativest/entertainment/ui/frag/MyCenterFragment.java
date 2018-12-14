package cn.innovativest.entertainment.ui.frag;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.innovativest.entertainment.GlideApp;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.base.BaseMvpFragment;
import cn.innovativest.entertainment.bean.UserInfoBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.presenter.MyCenterPresenter;
import cn.innovativest.entertainment.ui.act.LoginActivity;
import cn.innovativest.entertainment.ui.act.OrderActivity;
import cn.innovativest.entertainment.ui.act.RecordActivity;
import cn.innovativest.entertainment.ui.act.VipActivity;
import cn.innovativest.entertainment.utils.SPUtils;
import cn.innovativest.entertainment.utils.StringUtils;
import cn.innovativest.entertainment.utils.ToastUtils;
import cn.innovativest.entertainment.view.MyCenterView;
import cn.innovativest.entertainment.widget.ReechargeDialog;

public class MyCenterFragment extends BaseMvpFragment<MyCenterView, MyCenterPresenter> implements MyCenterView {

    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.lltHistory)
    LinearLayout lltHistory;

    @BindView(R.id.lltVIP)
    LinearLayout lltVIP;

    @BindView(R.id.tvwOpenVip)
    TextView tvwOpenVip;

    @BindView(R.id.lltOrder)
    LinearLayout lltOrder;

    @BindView(R.id.rltMineVIP)
    RelativeLayout rltMineVIP;

    @BindView(R.id.tvMineVIP)
    TextView tvMineVIP;

    @BindView(R.id.rltMineScore)
    RelativeLayout rltMineScore;

    @BindView(R.id.tvMineScore)
    TextView tvMineScore;

    @BindView(R.id.rltMineCollect)
    RelativeLayout rltMineCollect;

    @BindView(R.id.rltMinePhone)
    RelativeLayout rltMinePhone;

    @BindView(R.id.tvMinePhone)
    TextView tvMinePhone;

    @BindView(R.id.rltLogout)
    RelativeLayout rltLogout;

    @BindView(R.id.rltRecharge)
    RelativeLayout rltRecharge;

    private ReechargeDialog reechargeDialog;

    HttpRespond<UserInfoBean> respond;

    @Override
    public MyCenterPresenter initPresenter() {
        return new MyCenterPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mycenter;
    }

    @Override
    protected void initView(Bundle arguments) {
        reechargeDialog = new ReechargeDialog(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        if ((boolean) SPUtils.get(getActivity(), "is_login", false)) {
            rltLogout.setVisibility(View.VISIBLE);
            mPresenter.getUserInfo((String) SPUtils.get(getActivity(), "user_phone", ""));
        } else {
            tvName.setText("点击登录");
            GlideApp.with(getActivity()).load(R.mipmap.mine_avatar).optionalCircleCrop().into(ivAvatar);
            tvMineScore.setText("");
            tvMinePhone.setText("");
            tvMineVIP.setText("");
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!((boolean) SPUtils.get(getActivity(), "is_login", false))) {
                        startActivity(new Intent(getActivity(), LoginActivity.class).putExtra("item_id", 4));
                    }
                }
            });
            rltLogout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.lltHistory)
    public void goToRecord() {
        if ((boolean) SPUtils.get(getActivity(), "is_login", false)) {
            startActivity(new Intent(getActivity(), RecordActivity.class));
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class).putExtra("item_id", 4));
        }
    }

    @OnClick(R.id.lltVIP)
    public void goToRenew() {
        if ((boolean) SPUtils.get(getActivity(), "is_login", false)) {
            if (respond != null) {
                startActivity(new Intent(getActivity(), VipActivity.class).putExtra("name", respond.data.getNickname()).putExtra("headImg", respond.data.getHead_img()
                ).putExtra("time", respond.data.getEnd_time()).putExtra("score", respond.data.getJifen()));
            } else {
                ToastUtils.showShort(getActivity(), "数据错误");
            }

        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class).putExtra("item_id", 4));
        }


    }

    @OnClick(R.id.lltOrder)
    public void goToOrder() {
        if ((boolean) SPUtils.get(getActivity(), "is_login", false)) {
            startActivity(new Intent(getActivity(), OrderActivity.class));
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class).putExtra("item_id", 4));
        }
    }

//    @OnClick(R.id.rltMineVIP)
//    public void goToVIP() {
//
//    }
//
//    @OnClick(R.id.rltMineCollect)
//    public void goToCollect() {
//
//    }
//
//    @OnClick(R.id.rltMinePhone)
//    public void goToPhone() {
//
//    }

    private void exitLogin() {
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //    设置Title的内容
        builder.setTitle("提示");
        //    设置Content来显示一个信息
        builder.setMessage("确定退出吗？");
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exit();
            }
        });
        //    设置一个NegativeButton
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //    显示出该对话框
        builder.show();
    }

    private void exit() {
        SPUtils.put(getActivity(), "is_login", false);
        SPUtils.put(getActivity(), "user_phone", "");
        SPUtils.put(getActivity(), "user_cookie_pre", "");
        init();
    }

    @OnClick(R.id.rltLogout)
    public void goToLogout() {
        if ((boolean) SPUtils.get(getActivity(), "is_login", false)) {
            exitLogin();
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class).putExtra("item_id", 4));
        }
    }

    @OnClick(R.id.rltRecharge)
    public void goToRecharge() {
        if ((boolean) SPUtils.get(getActivity(), "is_login", false)) {
            reechargeDialog.setIsCancelable(false).setMsg("充值")
                    .setChooseListener(new ReechargeDialog.ChooseListener() {

                        @Override
                        public void onChoose(int which) {
                            if (which == WHICH_RIGHT) {
                                String userToken = (String) SPUtils.get(getContext(), "user_phone", "");
                                mPresenter.recharge(userToken, reechargeDialog.getEdtSDSDText());
                            }
                        }
                    }).show();
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class).putExtra("item_id", 4));
        }
    }

    @Override
    public void getUserInfo(HttpRespond<UserInfoBean> respond) {

        if (respond != null && respond.data != null) {
            respond = respond;
            GlideApp.with(getActivity()).load(respond.data.getHead_img()).optionalCircleCrop().into(ivAvatar);
            tvName.setText(respond.data.getNickname());
            tvMineScore.setText(respond.data.getJifen() == 0.0 ? "" : respond.data.getJifen() + "");
            tvMinePhone.setText(respond.data.getPhone());
            if (TextUtils.isEmpty(respond.data.getEnd_time())) {
                rltMineVIP.setVisibility(View.GONE);
                tvMineVIP.setVisibility(View.INVISIBLE);
                tvwOpenVip.setText("开通会员");
            } else {
                rltMineVIP.setVisibility(View.VISIBLE);
                tvMineVIP.setVisibility(View.VISIBLE);
                tvwOpenVip.setText("会员续费");
                tvMineVIP.setText(StringUtils.formatTimeToFormat("yyyy-MM-dd HH:mm:SS", Long.parseLong(respond.data.getEnd_time())));
            }

        }
    }

    @Override
    public void rechargeComplete(HttpRespond respond) {
        ToastUtils.showShort(getActivity(), respond.message);
        if (respond.states == 1) {
            String userToken = (String) SPUtils.get(getActivity(), "user_phone", "");
            boolean isLogin = (boolean) SPUtils.get(getContext(), "is_login", false);
            if (isLogin)
                mPresenter.getUserInfo(userToken);
        }
    }
}
