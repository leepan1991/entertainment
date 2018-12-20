package cn.innovativest.entertainment.ui.frag;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import butterknife.BindView;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.base.BaseFragment;
import cn.innovativest.entertainment.bean.WebJsonBean;
import cn.innovativest.entertainment.ui.act.LoginActivity;
import cn.innovativest.entertainment.ui.act.VipActivity;
import cn.innovativest.entertainment.utils.LogUtils;
import cn.innovativest.entertainment.utils.SPUtils;

public class CommonFragment extends BaseFragment {

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.tvwTitle)
    TextView tvwTitle;

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
//            synCookies(getActivity());
            setCookies(url);
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
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivityForResult(i, LoginActivity.REQUEST_CODE);
                            } else if (webJsonBean.getType().equals("open")) {
                                if ((boolean) SPUtils.get(getActivity(), "is_login", false)) {
                                    Intent i = new Intent(getActivity(), VipActivity.class);
                                    i.putExtra("item_id", getItem_id());
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivityForResult(i, LoginActivity.REQUEST_CODE);
                                } else {
                                    Intent i = new Intent(getActivity(), LoginActivity.class);
                                    i.putExtra("item_id", getItem_id());
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivityForResult(i, LoginActivity.REQUEST_CODE);
                                }

                            }
                        }
                    }
//                    function.onCallBack("submitFromWeb exe, response data 中文 from Java");
                }
            });
            wvDesc.loadUrl(url);
        }
    }

    private boolean back() {
        if (wvDesc.canGoBack()) {
            wvDesc.goBack();
            return true;
        }
        return false;
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
        btnBack.setImageResource(R.mipmap.login_arrow_left);
        tvwTitle.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        wvDesc.getSettings().setJavaScriptEnabled(true);
        wvDesc.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        wvDesc.setDefaultHandler(new DefaultHandler());


        wvDesc.setWebViewClient(new MyWebViewClient(wvDesc));
        wvDesc.setWebChromeClient(new WebChromeClient() {
            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(getActivity());
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//播放时横屏幕，如果需要改变横竖屏，只需该参数就行了
            }
        });

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
//        CookieManager cookieManager = CookieManager.getInstance();
//        String cookieStr = cookieManager.getCookie(getDomain(url));
//        SPUtils.put(getActivity(), "cookie", cookieStr);
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

    public void synCookies(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);// 允许接受 Cookie
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookie();// 移除
        } else {
            cookieManager.removeSessionCookies(null);// 移除
        }
        if (!TextUtils.isEmpty((String) SPUtils.get(getActivity(), "user_cookie_pre", ""))) {
            cookieManager.setCookie((String) SPUtils.get(getActivity(), "user_cookie_pre", "") + "_phone", (String) SPUtils.get(getActivity(), "user_phone", ""));
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
        } else {
            cookieManager.flush();
        }
    }

    public void setCookies(String cookiesPath) {
//        Map<String, String> cookieMap = new HashMap<>();
//        String cookie = (String) SPUtils.get(getActivity(), "cookie", "");
//        if (!TextUtils.isEmpty(cookie)) {
//            String[] cookieArray = cookie.split(";");// 多个Cookie是使用分号分隔的
//            for (int i = 0; i < cookieArray.length; i++) {
//                int position = cookieArray[i].indexOf("=");// 在Cookie中键值使用等号分隔
//                String cookieName = cookieArray[i].substring(0, position);// 获取键
//                String cookieValue = cookieArray[i].substring(position + 1);// 获取值

//                String value = cookieName + "=" + cookieValue;// 键值对拼接成 value

        if (!TextUtils.isEmpty((String) SPUtils.get(getActivity(), "user_cookie_pre", ""))) {
            String value = (String) SPUtils.get(getActivity(), "user_cookie_pre", "") + "phone" + "=" + (String) SPUtils.get(getActivity(), "user_phone", "");// 键值对拼接成 value
            Log.e("cookie", value);
            CookieManager.getInstance().setCookie(getDomain(cookiesPath), value);// 设置 Cookie
        }

//            }
//        }
    }

    /**
     * 获取URL的域名
     */
    private String getDomain(String url) {
        url = url.replace("http://", "").replace("https://", "");
        if (url.contains("/")) {
            url = url.substring(0, url.indexOf('/'));
        }
        return url;
    }

}
