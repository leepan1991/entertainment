package cn.innovativest.entertainment.ui.frag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;

import butterknife.BindView;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.base.BaseFragment;
import cn.innovativest.entertainment.utils.LogUtils;
import cn.innovativest.entertainment.utils.ToastUtils;
import im.delight.android.webview.AdvancedWebView;

public class CommonFragment extends BaseFragment implements AdvancedWebView.Listener {

    @BindView(R.id.wvDesc)
    AdvancedWebView wvDesc;

    String url;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common_web;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        if (wvDesc != null) {
            wvDesc.loadUrl(url);
        }
    }

    @Override
    protected void initView(Bundle arguments) {

        //传入
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            url = bundle.getString("url");
//            if (TextUtils.isEmpty(url)) {
//                ToastUtils.showLong(getActivity(), "数据错误");
//                return;
//            }
//            LogUtils.e("url=====" + url);
//        } else {
//            ToastUtils.showLong(getActivity(), "数据错误");
//            return;
//        }


//        wvDesc.getSettings().setJavaScriptEnabled(true);
//        wvDesc.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        wvDesc.getSettings().setSupportZoom(true);
//        wvDesc.getSettings().setLoadWithOverviewMode(true);
//        wvDesc.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        wvDesc.getSettings().setDefaultTextEncodingName("UTF-8");
//        wvDesc.getSettings().setDomStorageEnabled(true);
//        wvDesc.getSettings().setUseWideViewPort(true);
//        wvDesc.getSettings().setLoadWithOverviewMode(true);

        wvDesc.setListener(getActivity(), this);
//        wvDesc.loadUrl(url);

//        wvDesc.setWebChromeClient(new WebChromeClient() {
//
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//            }
//
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//            }
//        });
//        wvDesc.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onReceivedError(WebView view, int errorCode,
//                                        String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view,
//                                           SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
//                // view.loadUrl(url);
//                return false;
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        wvDesc.onResume();
        // ...
    }

    @Override
    public void onPause() {
        wvDesc.onPause();
        // ...
        super.onPause();
    }

    @Override
    public void onDestroy() {
        wvDesc.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        wvDesc.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }
}
