package cn.innovativest.entertainment.ui.act;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.innovativest.entertainment.GlideApp;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.adapter.VipAdapter;
import cn.innovativest.entertainment.base.BaseMvpActivity;
import cn.innovativest.entertainment.bean.BuyBean;
import cn.innovativest.entertainment.bean.EBuyBean;
import cn.innovativest.entertainment.bean.UserInfoBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.presenter.VipPresenter;
import cn.innovativest.entertainment.utils.SPUtils;
import cn.innovativest.entertainment.utils.StringUtils;
import cn.innovativest.entertainment.utils.ToastUtils;
import cn.innovativest.entertainment.view.VipView;
import cn.innovativest.entertainment.widget.XListView;

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

//    @BindView(R.id.rltMonthVIP)
//    RelativeLayout rltMonthVIP;
//
//    @BindView(R.id.tvMonthNotice)
//    TextView tvMonthNotice;
//
//    @BindView(R.id.ivMonth)
//    ImageView ivMonth;
//
//    @BindView(R.id.rltYearVIP)
//    RelativeLayout rltYearVIP;
//
//    @BindView(R.id.tvYearNotice)
//    TextView tvYearNotice;
//
//    @BindView(R.id.ivYear)
//    ImageView ivYear;

    @BindView(R.id.tvwMoneyDesc)
    TextView tvwMoneyDesc;

    @BindView(R.id.btnCoin)
    Button btnCoin;

    @BindView(R.id.btnScore)
    Button btnScore;

    @BindView(R.id.wvDesc)
    WebView wvDesc;

    @BindView(R.id.xListview)
    XListView xListView;

    private VipAdapter vipAdapter;
    private List<BuyBean> lstBuyBeans;

//    HttpRespond<EBuyBean> respond = null;

    private String name;
    private String headImg;
    private String time;
    private float score;

    private int item_id;

//    private String cardDay;
//    private String cardCredit;
//    private String cardFee;


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
        return R.layout.activity_vip_new;
    }

    @Override
    public void initView() {

        SPUtils.put(VipActivity.this, "vipselect", "");
        btnBack.setImageResource(R.mipmap.login_arrow_left);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item_id != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("item_id", item_id);
                    intent.putExtra(LoginActivity.IS_LOGINED, true);
                    setResult(LoginActivity.RESULT_CODE, intent);
                    finish();
                } else {
                    finish();
                }
            }
        });

        lstBuyBeans = new ArrayList<>();
        vipAdapter = new VipAdapter(this, lstBuyBeans);
        xListView.setAdapter(vipAdapter);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BuyBean buyBean = lstBuyBeans.get(position);
                SPUtils.put(VipActivity.this, "vipselect", new Gson().toJson(buyBean));
                vipAdapter.notifyDataSetChanged();
                tvwMoneyDesc.setText("支付金额：" + lstBuyBeans.get(position).getCard_fee() + "元 或 " + lstBuyBeans.get(position).getCard_credit() + "积分");
            }
        });

        item_id = getIntent().getIntExtra("item_id", -1);

        mPresenter.getVipInfo((String) SPUtils.get(this, "user_phone", ""));
    }

//    @OnClick(R.id.rltMonthVIP)
//    public void clickMonth() {
//        ivMonth.setImageResource(R.mipmap.ic_share_checked);
//        ivYear.setImageResource(R.mipmap.ic_share_check);
//        if (respond != null) {
//            tvwMoneyDesc.setText("支付金额：" + respond.data.getLstBuyBeans().get(0).getCard_fee() + "元 或 " + respond.data.getLstBuyBeans().get(0).getCard_credit() + "积分");
//            cardDay = respond.data.getLstBuyBeans().get(0).getCard_day();
//            cardCredit = respond.data.getLstBuyBeans().get(0).getCard_credit();
//            cardFee = respond.data.getLstBuyBeans().get(0).getCard_fee();
//        }
//    }
//
//    @OnClick(R.id.rltYearVIP)
//    public void clickYear() {
//        ivMonth.setImageResource(R.mipmap.ic_share_check);
//        ivYear.setImageResource(R.mipmap.ic_share_checked);
//        if (respond != null) {
//            tvwMoneyDesc.setText("支付金额：" + respond.data.getLstBuyBeans().get(1).getCard_fee() + "元 或 " + respond.data.getLstBuyBeans().get(1).getCard_credit() + "积分");
//            cardDay = respond.data.getLstBuyBeans().get(1).getCard_day();
//            cardCredit = respond.data.getLstBuyBeans().get(1).getCard_credit();
//            cardFee = respond.data.getLstBuyBeans().get(1).getCard_fee();
//        }
//    }

    @OnClick(R.id.btnCoin)
    public void coinBuy() {
        BuyBean buyBean = new Gson().fromJson((String) SPUtils.get(VipActivity.this, "vipselect", ""), BuyBean.class);
        if (buyBean != null) {
            mPresenter.buySuit((String) SPUtils.get(this, "user_phone", ""), "2", buyBean.getCard_day(), buyBean.getCard_fee());
        } else {
            ToastUtils.showShort(this, "数据错误");
        }

    }

    @OnClick(R.id.btnScore)
    public void scoreBuy() {
        BuyBean buyBean = new Gson().fromJson((String) SPUtils.get(VipActivity.this, "vipselect", ""), BuyBean.class);
        if (buyBean != null) {
            mPresenter.buySuit((String) SPUtils.get(this, "user_phone", ""), "1", buyBean.getCard_day(), buyBean.getCard_credit());
        } else {
            ToastUtils.showShort(this, "数据错误");
        }
    }

    private void init(UserInfoBean userInfoBean) {
        name = userInfoBean.getNickname();
        headImg = userInfoBean.getHead_img();
        time = userInfoBean.getEnd_time();
        score = userInfoBean.getJifen();
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

//        ivMonth.setImageResource(R.mipmap.ic_share_checked);
//        ivYear.setImageResource(R.mipmap.ic_share_check);
    }

    @Override
    public void getVipInfo(HttpRespond<EBuyBean> respond) {
        if (respond != null && respond.data != null && respond.data.getLstBuyBeans().size() > 0) {
//            this.respond = respond;
//            tvMonthNotice.setText(respond.data.getLstBuyBeans().get(0).getCard_title() + "会员-" + respond.data.getLstBuyBeans().get(0).getCard_day() + "天");
//            tvYearNotice.setText(respond.data.getLstBuyBeans().get(1).getCard_title() + "会员-" + respond.data.getLstBuyBeans().get(0).getCard_day() + "天");
//            ivMonth.setImageResource(R.mipmap.ic_share_checked);
//            ivYear.setImageResource(R.mipmap.ic_share_check);
//            tvwMoneyDesc.setText("支付金额：" + respond.data.getLstBuyBeans().get(0).getCard_fee() + "元 或 " + respond.data.getLstBuyBeans().get(0).getCard_credit() + "积分");
            lstBuyBeans.clear();
            lstBuyBeans.addAll(respond.data.getLstBuyBeans());
            vipAdapter.notifyDataSetChanged();
            tvwMoneyDesc.setText("支付金额：" + lstBuyBeans.get(0).getCard_fee() + "元 或 " + lstBuyBeans.get(0).getCard_credit() + "积分");
            wvDesc.loadData(respond.data.getText(), "text/html; charset=UTF-8", null);
        }
        if (respond != null && respond.data != null && respond.data.getUserInfoBean() != null) {
            init(respond.data.getUserInfoBean());
        }

    }

    @Override
    public void payComplete(HttpRespond respond) {
        ToastUtils.showShort(VipActivity.this, respond.message);
        if (respond.states == 1) {
            if (item_id != -1) {
                Intent intent = new Intent();
                intent.putExtra("item_id", item_id);
                intent.putExtra(LoginActivity.IS_LOGINED, true);
                setResult(LoginActivity.RESULT_CODE, intent);
                finish();
            } else {
                finish();
            }
        }
    }
}
