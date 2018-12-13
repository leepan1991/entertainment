package cn.innovativest.entertainment.ui.frag;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.innovativest.entertainment.GlideApp;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.base.BaseMvpFragment;
import cn.innovativest.entertainment.bean.UserInfoBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.presenter.MyCenterPresenter;
import cn.innovativest.entertainment.view.MyCenterView;

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
    TextView rltMineCollect;

    @BindView(R.id.rltMinePhone)
    RelativeLayout rltMinePhone;

    @BindView(R.id.tvMinePhone)
    TextView tvMinePhone;

    @BindView(R.id.rltLogout)
    RelativeLayout rltLogout;

    @BindView(R.id.rltRecharge)
    RelativeLayout rltRecharge;

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

        mPresenter.getUserInfo();
    }

    @Override
    public void getUserInfo(HttpRespond<UserInfoBean> respond) {

        if (respond != null && respond.data != null) {
            GlideApp.with(getActivity()).load(respond.data.getHead_img()).optionalCircleCrop().into(ivAvatar);
            tvName.setText(respond.data.getNickname());
            tvMineScore.setText(respond.data.getJifen() == 0.0 ? "" : respond.data.getJifen() + "");
            tvMinePhone.setText(respond.data.getPhone());
        }
    }
}
