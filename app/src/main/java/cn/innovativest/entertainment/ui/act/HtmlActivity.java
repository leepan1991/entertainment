package cn.innovativest.entertainment.ui.act;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import butterknife.BindView;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.base.BaseActivity;
import cn.innovativest.entertainment.bean.WebJsonBean;
import cn.innovativest.entertainment.utils.SPUtils;
import cn.innovativest.entertainment.utils.ToastUtils;

public class HtmlActivity extends BaseActivity {

    @BindView(R.id.number_progress_bar)
    NumberProgressBar mProgressBar;

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

    boolean scroll = false;
    String url;

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
        return R.layout.activity_html;
    }

    @Override
    public void initView() {
        btnBack.setImageResource(R.mipmap.login_arrow_left);
        tvwTitle.setVisibility(View.INVISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showLong(this, "数据错误");
            return;
        }
        setCookies(url);
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
        // 资源加载
        wvDesc.getSettings().setLoadsImagesAutomatically(true); // 是否自动加载图片
        wvDesc.getSettings().setBlockNetworkImage(false);       // 禁止加载网络图片
        wvDesc.getSettings().setBlockNetworkLoads(false);       // 禁止加载所有网络资源
//        wvDesc.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_SCROLL)
//                    scroll = false;
//                else
//                    scroll = true;
//
//                return false;
//            }
//        });
//        wvDesc.setWebViewClient(new MyWebViewClient(wvDesc));
        wvDesc.setWebChromeClient(new WebChromeClient() {
            /*** 视频播放相关的方法 **/
            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(HtmlActivity.this);
                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 95) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    mProgressBar.setProgress(newProgress);
                }

                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
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

    /**
     * 视频播放全屏
     */
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {

        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }
        getWindow().getDecorView();

        //获取虚拟按键高度，防止遮挡
//        if (ScreenUtils.hasNavBar(this)) {
//            COVER_SCREEN_PARAMS.setMargins(0, 0, 0, ScreenUtils.getNavigationBarHeight(this));
//        }

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (!scroll) {
//                view.loadUrl(url);
//            }
//            return super.shouldOverrideUrlLoading(view, url);
//        }
//
//
//        @Override
//        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
//            if (scroll)
//                return true;
//            return false;
//        }
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }
        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
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
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public void setCookies(String cookiesPath) {

        if (!TextUtils.isEmpty((String) SPUtils.get(this, "user_cookie_pre", ""))) {
            String value = (String) SPUtils.get(this, "user_cookie_pre", "") + "phone" + "=" + (String) SPUtils.get(this, "user_phone", "");// 键值对拼接成 value
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
        // ...
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // ...
    }
}
