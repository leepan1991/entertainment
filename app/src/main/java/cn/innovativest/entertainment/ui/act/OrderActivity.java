package cn.innovativest.entertainment.ui.act;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.adapter.OrderAdapter;
import cn.innovativest.entertainment.adapter.RecordAdapter;
import cn.innovativest.entertainment.base.BaseMvpActivity;
import cn.innovativest.entertainment.bean.OrderBean;
import cn.innovativest.entertainment.bean.RecordBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.presenter.OrderPresenter;
import cn.innovativest.entertainment.presenter.RecordPresenter;
import cn.innovativest.entertainment.utils.SPUtils;
import cn.innovativest.entertainment.utils.ToastUtils;
import cn.innovativest.entertainment.view.OrderView;
import cn.innovativest.entertainment.view.RecordView;

public class OrderActivity extends BaseMvpActivity<OrderView, OrderPresenter> implements OrderView, OnRefreshListener {

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.tvwTitle)
    TextView tvwTitle;

    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout swipe_refresh;

    @BindView(R.id.order_listview)
    ListView order_listview;

    private List<OrderBean> lstOrderBeans;
    private OrderAdapter orderAdapter;


    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter();
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
        return R.layout.activity_order;
    }

    @Override
    public void initView() {
        btnBack.setImageResource(R.mipmap.login_arrow_left);
        tvwTitle.setText("订单管理");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lstOrderBeans = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, lstOrderBeans);
        order_listview.setAdapter(orderAdapter);

        swipe_refresh.setOnRefreshListener(this);
        swipe_refresh.setNoMoreData(true);
        swipe_refresh.setEnableLoadMore(false);
        mPresenter.getOrder((String) SPUtils.get(this, "user_phone", ""));
    }

    private void initDataToView(List<OrderBean> orderBeans) {
        lstOrderBeans.clear();
        lstOrderBeans.addAll(orderBeans);
        orderAdapter.notifyDataSetChanged();
    }

    @Override
    public void getOrder(HttpRespond<List<OrderBean>> respond) {
        if (respond != null && respond.data != null && respond.data.size() > 0) {
            initDataToView(respond.data);
        } else {
            ToastUtils.showLong(OrderActivity.this, "数据获取失败");
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        mPresenter.getOrder((String) SPUtils.get(this, "user_phone", ""));
    }
}
