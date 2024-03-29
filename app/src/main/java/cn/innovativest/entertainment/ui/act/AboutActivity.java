package cn.innovativest.entertainment.ui.act;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.ath.AthService;
import cn.innovativest.entertainment.ath.BaseAct;
import cn.innovativest.entertainment.ath.CommonResponse;
import cn.innovativest.entertainment.ath.RetrofitClient;
import cn.innovativest.entertainment.utils.StringUtils;
import cn.innovativest.entertainment.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by leepan on 21/03/2018.
 */

public class AboutActivity extends BaseAct {

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.tvwTitle)
    TextView tvwTitle;

    @BindView(R.id.wvDesc)
    WebView wvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initView();
        getCommonData();
    }

    @Override
    public void onClick(View v) {

    }

    private void initView() {
        btnBack.setImageResource(R.mipmap.login_arrow_left);
        tvwTitle.setText("关于我们");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        wvDesc.getSettings().setJavaScriptEnabled(true);
        wvDesc.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvDesc.getSettings().setSupportZoom(true);
        wvDesc.getSettings().setLoadWithOverviewMode(true);
        wvDesc.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wvDesc.getSettings().setDefaultTextEncodingName("UTF-8");
        wvDesc.getSettings().setDomStorageEnabled(true);
        wvDesc.getSettings().setUseWideViewPort(true);
        wvDesc.getSettings().setLoadWithOverviewMode(true);

        wvDesc.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        wvDesc.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
                // view.loadUrl(url);
                return false;
            }
        });
    }

    private void getCommonData() {

        AthService service = RetrofitClient.getService(this);
        service.commonInfo(15).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<CommonResponse>() {
            @Override
            public void accept(CommonResponse commonResponse) throws Exception {
                if (commonResponse != null) {
                    if (commonResponse.commonItem != null) {
//                        initDataToView(tradeResponse.tradeItems);
                        if (commonResponse.commonItem.title.equals("关于我们")) {
                            if (!StringUtils.isEmpty(commonResponse.commonItem.exchange)) {
                                wvDesc.setVisibility(View.VISIBLE);
                                wvDesc.loadDataWithBaseURL(null, commonResponse.commonItem.exchange.toString(), "text/html", "utf-8", null);
                            } else {
                                wvDesc.setVisibility(View.INVISIBLE);
                            }
                        }
                    } else {
                        ToastUtils.showShort(AboutActivity.this, commonResponse.message);
                    }
                } else {
                    ToastUtils.showShort(AboutActivity.this, "数据获取失败");
                }
            }
        });
//        service.commonInfo(15).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Action1<CommonResponse>() {
//            @Override
//            public void call(CommonResponse commonResponse) {
//                if (commonResponse != null) {
//                    if (commonResponse.commonItem != null) {
////                        initDataToView(tradeResponse.tradeItems);
//                        if (commonResponse.commonItem.title.equals("关于我们")) {
//                            if (!StringUtils.isEmpty(commonResponse.commonItem.exchange)) {
//                                wvDesc.setVisibility(View.VISIBLE);
//                                wvDesc.loadDataWithBaseURL(null, commonResponse.commonItem.exchange.toString(), "text/html", "utf-8", null);
//                            } else {
//                                wvDesc.setVisibility(View.INVISIBLE);
//                            }
//                        }
//                    } else {
//                        ToastUtils.showShort(AboutActivity.this, commonResponse.message);
//                    }
//                } else {
//                    ToastUtils.showShort(AboutActivity.this, "数据获取失败");
//                }
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                LogUtils.e(throwable.getMessage());
//                ToastUtils.showShort(AboutActivity.this, "数据获取失败");
//            }
//        });

    }
}
