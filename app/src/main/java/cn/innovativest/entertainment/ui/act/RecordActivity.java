package cn.innovativest.entertainment.ui.act;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.adapter.RecordAdapter;
import cn.innovativest.entertainment.base.BaseMvpActivity;
import cn.innovativest.entertainment.bean.RecordBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.presenter.RecordPresenter;
import cn.innovativest.entertainment.utils.SPUtils;
import cn.innovativest.entertainment.utils.ToastUtils;
import cn.innovativest.entertainment.view.RecordView;

public class RecordActivity extends BaseMvpActivity<RecordView, RecordPresenter> implements RecordView, OnRefreshListener, AdapterView.OnItemClickListener {

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.tvwTitle)
    TextView tvwTitle;

    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout swipe_refresh;

    @BindView(R.id.record_listview)
    ListView record_listview;

    private List<RecordBean> lstRecordBeans;
    private RecordAdapter recordAdapter;


    @Override
    public RecordPresenter initPresenter() {
        return new RecordPresenter();
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
        return R.layout.activity_record;
    }

    @Override
    public void initView() {
        btnBack.setImageResource(R.mipmap.login_arrow_left);
        tvwTitle.setText("观看记录");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lstRecordBeans = new ArrayList<>();
        recordAdapter = new RecordAdapter(this, lstRecordBeans);
        record_listview.setAdapter(recordAdapter);
        record_listview.setOnItemClickListener(this);

        swipe_refresh.setOnRefreshListener(this);
        swipe_refresh.setNoMoreData(true);
        swipe_refresh.setEnableLoadMore(false);
        mPresenter.getRecord((String) SPUtils.get(this, "user_phone", ""));
    }

    private void initDataToView(List<RecordBean> recordBeans) {
        lstRecordBeans.clear();
        lstRecordBeans.addAll(recordBeans);
        recordAdapter.notifyDataSetChanged();
    }

    @Override
    public void getRecord(HttpRespond<List<RecordBean>> respond) {
        if (respond != null && respond.data != null && respond.data.size() > 0) {
            initDataToView(respond.data);
        } else {
            ToastUtils.showLong(RecordActivity.this, "数据获取失败");
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        mPresenter.getRecord((String) SPUtils.get(this, "user_phone", ""));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RecordBean recordBean = lstRecordBeans.get(position);
        startActivity(new Intent(this, HtmlActivity.class).putExtra("url", recordBean.getLink()));
    }
}
