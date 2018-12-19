package cn.innovativest.entertainment.ui.act;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.innovativest.entertainment.BuildConfig;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.adapter.AppAdapter;
import cn.innovativest.entertainment.base.BaseMvpActivity;
import cn.innovativest.entertainment.bean.AppBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.presenter.AppPresenter;
import cn.innovativest.entertainment.utils.ToastUtils;
import cn.innovativest.entertainment.view.AppView;


/**
 * Created by leepan on 21/03/2018.
 */

public class AppActivity extends BaseMvpActivity<AppView, AppPresenter> implements AppView, AdapterView.OnItemClickListener, OnRefreshListener {

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.tvwTitle)
    TextView tvwTitle;

    @BindView(R.id.app_refresh)
    SmartRefreshLayout app_refresh;

    @BindView(R.id.appGird)
    GridView appGird;

    private List<AppBean> lstAppItems;
    private AppAdapter appAdapter;


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AppBean appItem = lstAppItems.get(position);
        openApp(appItem);
    }

    @Override
    public AppPresenter initPresenter() {
        return new AppPresenter();
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
        return R.layout.activity_app;
    }

    @Override
    public void initView() {
        btnBack.setImageResource(R.mipmap.login_arrow_left);
        tvwTitle.setText("生态圈子应用");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lstAppItems = new ArrayList<>();
        appAdapter = new AppAdapter(this, lstAppItems);
        appGird.setAdapter(appAdapter);
        appGird.setOnItemClickListener(this);

        app_refresh.setOnRefreshListener(this);
        mPresenter.requestAppList(BuildConfig.APPLICATION_ID);
//        app_refresh.setNoMoreData(true);
//        app_refresh.setEnableLoadMore(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        mPresenter.requestAppList(BuildConfig.APPLICATION_ID);
    }

    private void initDataToView(List<AppBean> appItems) {
        lstAppItems.clear();
        lstAppItems.addAll(appItems);
        appAdapter.notifyDataSetChanged();
    }

    private void openApp(AppBean appItem) {
        PackageManager packageManager = getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(appItem.schemes);
        if (intent == null) {
            Uri uri = Uri.parse(appItem.url);
            if (uri != null) {
                try {
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent1);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(this, "未知应用");
                }

            } else {
                ToastUtils.showShort(this, "app下载地址有误");
            }
        } else {
            startActivity(intent);
        }

    }

    @Override
    public void showAppList(HttpRespond<List<AppBean>> respond) {
        if (respond != null) {
            if (respond.data != null && respond.data.size() > 0) {
                initDataToView(respond.data);
            } else {
                appAdapter.notifyDataSetChanged();
            }
        } else {
            ToastUtils.showShort(AppActivity.this, "数据获取失败");
        }
    }

}
