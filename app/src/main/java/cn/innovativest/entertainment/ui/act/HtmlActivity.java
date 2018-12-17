package cn.innovativest.entertainment.ui.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.base.BaseActivity;
import cn.innovativest.entertainment.utils.ToastUtils;
import im.delight.android.webview.AdvancedWebView;

public class HtmlActivity extends BaseActivity implements AdvancedWebView.Listener {

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.tvwTitle)
    TextView tvwTitle;

    @BindView(R.id.wvDesc)
    AdvancedWebView wvDesc;

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
        url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showLong(this, "数据错误");
            return;
        }
        wvDesc.setListener(this, this);
        wvDesc.loadUrl(url);
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