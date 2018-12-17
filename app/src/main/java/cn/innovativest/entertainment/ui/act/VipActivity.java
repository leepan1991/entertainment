package cn.innovativest.entertainment.ui.act;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.innovativest.entertainment.GlideApp;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.base.BaseMvpActivity;
import cn.innovativest.entertainment.bean.EBuyBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.presenter.VipPresenter;
import cn.innovativest.entertainment.utils.SPUtils;
import cn.innovativest.entertainment.utils.StringUtils;
import cn.innovativest.entertainment.utils.ToastUtils;
import cn.innovativest.entertainment.view.VipView;

public class VipActivity extends BaseMvpActivity<VipView, VipPresenter> implements VipView {

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.tvwTitle)
    TextView tvwTitle;

    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.ivRealed)
    ImageView ivRealed;

    @BindView(R.id.lltNameTwo)
    LinearLayout lltNameTwo;

    @BindView(R.id.tvVIPTime)
    TextView tvVIPTime;

    @BindView(R.id.tvScore)
    TextView tvScore;

    @BindView(R.id.rltMonthVIP)
    RelativeLayout rltMonthVIP;

    @BindView(R.id.tvMonthNotice)
    TextView tvMonthNotice;

    @BindView(R.id.ivMonth)
    ImageView ivMonth;

    @BindView(R.id.rltYearVIP)
    RelativeLayout rltYearVIP;

    @BindView(R.id.tvYearNotice)
    TextView tvYearNotice;

    @BindView(R.id.ivYear)
    ImageView ivYear;

    @BindView(R.id.tvwMoneyDesc)
    TextView tvwMoneyDesc;

    @BindView(R.id.btnCoin)
    Button btnCoin;

    @BindView(R.id.btnScore)
    Button btnScore;

    HttpRespond<EBuyBean> respond = null;

    private String name;
    private String headImg;
    private String time;
    private float score;

    private String cardDay;
    private String cardCredit;
    private String cardFee;


    @Override
    public VipPresenter initPresenter() {
        return new VipPresenter();
    }

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
        return R.layout.activity_vip;
    }

    @Override
    public void initView() {

        name = getIntent().getStringExtra("name");
        headImg = getIntent().getStringExtra("headImg");
        time = getIntent().getStringExtra("time");
        score = getIntent().getFloatExtra("score", 0.0f);

        btnBack.setImageResource(R.mipmap.login_arrow_left);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (TextUtils.isEmpty(time)) {
            lltNameTwo.setVisibility(View.GONE);
            tvwTitle.setText("开通会员");
        } else {
            lltNameTwo.setVisibility(View.VISIBLE);
            tvVIPTime.setText(StringUtils.formatTimeToFormat("yyyy-MM-dd HH:mm:SS", Long.parseLong(time)) + " 到期");
            tvwTitle.setText("会员续费");
        }

        tvScore.setText(score + "");

        tvName.setText(name);
        GlideApp.with(this).load(headImg).optionalCircleCrop().into(iv_avatar);

        ivMonth.setImageResource(R.mipmap.ic_share_checked);
        ivYear.setImageResource(R.mipmap.ic_share_check);
        mPresenter.getVipInfo((String) SPUtils.get(this, "user_phone", ""));
    }

    @OnClick(R.id.rltMonthVIP)
    public void clickMonth() {
        ivMonth.setImageResource(R.mipmap.ic_share_checked);
        ivYear.setImageResource(R.mipmap.ic_share_check);
        if (respond != null) {
            tvwMoneyDesc.setText("支付金额：" + respond.data.getLstBuyBeans().get(0).getCard_fee() + "元 或 " + respond.data.getLstBuyBeans().get(0).getCard_credit() + "积分");
            cardDay = respond.data.getLstBuyBeans().get(0).getCard_day();
            cardCredit = respond.data.getLstBuyBeans().get(0).getCard_credit();
            cardFee = respond.data.getLstBuyBeans().get(0).getCard_fee();
        }
    }

    @OnClick(R.id.rltYearVIP)
    public void clickYear() {
        ivMonth.setImageResource(R.mipmap.ic_share_check);
        ivYear.setImageResource(R.mipmap.ic_share_checked);
        if (respond != null) {
            tvwMoneyDesc.setText("支付金额：" + respond.data.getLstBuyBeans().get(1).getCard_fee() + "元 或 " + respond.data.getLstBuyBeans().get(1).getCard_credit() + "积分");
            cardDay = respond.data.getLstBuyBeans().get(1).getCard_day();
            cardCredit = respond.data.getLstBuyBeans().get(1).getCard_credit();
            cardFee = respond.data.getLstBuyBeans().get(1).getCard_fee();
        }
    }

    @OnClick(R.id.btnCoin)
    public void coinBuy() {
        mPresenter.buySuit((String) SPUtils.get(this, "user_phone", ""), "2", cardDay, cardFee);
    }

    @OnClick(R.id.btnScore)
    public void scoreBuy() {
        mPresenter.buySuit((String) SPUtils.get(this, "user_phone", ""), "1", cardDay, cardCredit);
    }

    @Override
    public void getVipInfo(HttpRespond<EBuyBean> respond) {
        if (respond != null && respond.data.getLstBuyBeans().size() == 2) {
            this.respond = respond;
            tvMonthNotice.setText(respond.data.getLstBuyBeans().get(0).getCard_title() + "会员-" + respond.data.getLstBuyBeans().get(0).getCard_day() + "天");
            tvYearNotice.setText(respond.data.getLstBuyBeans().get(1).getCard_title() + "会员-" + respond.data.getLstBuyBeans().get(0).getCard_day() + "天");
            ivMonth.setImageResource(R.mipmap.ic_share_checked);
            ivYear.setImageResource(R.mipmap.ic_share_check);
            tvwMoneyDesc.setText("支付金额：" + respond.data.getLstBuyBeans().get(0).getCard_fee() + "元 或 " + respond.data.getLstBuyBeans().get(0).getCard_credit() + "积分");
        }

    }

    @Override
    public void payComplete(HttpRespond respond) {
        ToastUtils.showShort(VipActivity.this, respond.message);
        if (respond.states == 1) {
            finish();
        }
    }
}
