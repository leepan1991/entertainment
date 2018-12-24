package cn.innovativest.entertainment.ui.frag;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
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

    @BindView(R.id.btnAction)
    ImageButton btnAction;

    @BindView(R.id.wvDesc)
    BridgeWebView wvDesc;

    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;

    private WebChromeClient.CustomViewCallback customViewCallback;


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
                }
            });
            wvDesc.registerHandler("app_ad", new BridgeHandler() {
                @Override
                public void handler(String data, CallBackFunction function) {
                    if (!TextUtils.isEmpty(data)) {
                        WebJsonBean webJsonBean = new Gson().fromJson(data, WebJsonBean.class);
                        if (webJsonBean != null) {
                            if (webJsonBean.getType().equals("url")) {
                                Uri uri = Uri.parse(webJsonBean.getZh_cn());
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        }
                    }
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

        btnBack.setImageResource(R.mipmap.login_arrow_left);
        tvwTitle.setVisibility(View.VISIBLE);
        tvwTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        btnAction.setVisibility(View.VISIBLE);
        btnAction.setImageResource(R.mipmap.ic_refresh_new);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wvDesc.reload();
            }
        });

        wvDesc.getSettings().setJavaScriptEnabled(true);
        wvDesc.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvDesc.getSettings().setJavaScriptEnabled(true);
//        告诉JavaScript自动打开窗口。这适用于JavaScript函数的窗口。open()。
        wvDesc.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//      告诉webview 是否使用插件
        wvDesc.getSettings().setPluginState(WebSettings.PluginState.ON);
//        启用或禁用WebView中的文件访问
        wvDesc.getSettings().setAllowFileAccess(true);
//      是否调节内容 是否全屏
        wvDesc.getSettings().setLoadWithOverviewMode(true);
//        重写缓存使用的方式。      WebSettings.LOAD_NO_CACHE 不要使用缓存，从网络加载。
        wvDesc.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);


        wvDesc.setWebViewClient(new MyWebViewClient(wvDesc));
        wvDesc.setWebChromeClient(new WebChromeClient() {
            /*** 视频播放相关的方法 **/
            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(getActivity());
                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }
        });
    }

    /**
     * 视频播放全屏
     */
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {

        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }
        getActivity().getWindow().getDecorView();

        //获取虚拟按键高度，防止遮挡
//        if (ScreenUtils.hasNavBar(this)) {
//            COVER_SCREEN_PARAMS.setMargins(0, 0, 0, ScreenUtils.getNavigationBarHeight(this));
//        }

        FrameLayout decor = (FrameLayout) getActivity().getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(getActivity());
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }
        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getActivity().getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        wvDesc.setVisibility(View.VISIBLE);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getActivity().getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        if (!TextUtils.isEmpty((String) SPUtils.get(getActivity(), "user_cookie_pre", ""))) {
            String value = (String) SPUtils.get(getActivity(), "user_cookie_pre", "") + "phone" + "=" + (String) SPUtils.get(getActivity(), "user_phone", "");// 键值对拼接成 value
            Log.e("cookie", value);
            CookieManager.getInstance().setCookie(getDomain(cookiesPath), value);// 设置 Cookie
        }

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
