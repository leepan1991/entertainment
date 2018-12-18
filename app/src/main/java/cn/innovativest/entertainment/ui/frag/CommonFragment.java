package cn.innovativest.entertainment.ui.frag;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;

import butterknife.BindView;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.base.BaseFragment;
import cn.innovativest.entertainment.bean.WebJsonBean;
import cn.innovativest.entertainment.ui.act.LoginActivity;
import cn.innovativest.entertainment.ui.act.VipActivity;
import cn.innovativest.entertainment.utils.LogUtils;
import cn.innovativest.entertainment.utils.SPUtils;
import cn.innovativest.entertainment.utils.ToastUtils;
import im.delight.android.webview.AdvancedWebView;

public class CommonFragment extends BaseFragment {

    @BindView(R.id.wvDesc)
    BridgeWebView wvDesc;

    String url;

    private int item_id = 0;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

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

    public void setUrlAndId(String url, int item_id) {
        this.url = url;
        this.item_id = item_id;
        if (wvDesc != null) {
            wvDesc.registerHandler("app_page", new BridgeHandler() {
                @Override
                public void handler(String data, CallBackFunction function) {
                    LogUtils.e("data from web = " + data);
                    if (!TextUtils.isEmpty(data)) {
                        WebJsonBean webJsonBean = new Gson().fromJson(data, WebJsonBean.class);
                        if (webJsonBean != null) {
                            if (webJsonBean.getType().equals("login")) {
                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                i.putExtra("item_id", getItem_id());
                                startActivityForResult(i, LoginActivity.REQUEST_CODE);
                            } else if (webJsonBean.getType().equals("open")) {
                                Intent i = new Intent(getActivity(), VipActivity.class);
                                i.putExtra("item_id", getItem_id());
                                startActivityForResult(i, LoginActivity.REQUEST_CODE);
                            }
                        }
                    }
                    function.onCallBack("submitFromWeb exe, response data 中文 from Java");
                }
            });
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


        wvDesc.getSettings().setJavaScriptEnabled(true);
        wvDesc.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        wvDesc.setDefaultHandler(new DefaultHandler());


        wvDesc.setWebViewClient(new MyWebViewClient(wvDesc));

//        wvDesc.getSettings().setSupportZoom(true);
//        wvDesc.getSettings().setLoadWithOverviewMode(true);
//        wvDesc.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        wvDesc.getSettings().setDefaultTextEncodingName("UTF-8");
//        wvDesc.getSettings().setDomStorageEnabled(true);
//        wvDesc.getSettings().setUseWideViewPort(true);
//        wvDesc.getSettings().setLoadWithOverviewMode(true);

//        wvDesc.setListener(getActivity(), this);
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

//    public class JavaScriptInterface {
//        Context mContext;
//
//        JavaScriptInterface(Context c) {
//            mContext = c;
//        }
//
//        @JavascriptInterface
//        public void changeActivity() {
//            Intent i = new Intent(getActivity(), LoginActivity.class);
//            i.putExtra("item_id", getItem_id());
//            startActivityForResult(i, LoginActivity.REQUEST_CODE);
////            startActivity(i);
//        }
//    }

    class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }
    }

}
