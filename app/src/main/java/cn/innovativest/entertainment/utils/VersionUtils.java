package cn.innovativest.entertainment.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;

import java.io.File;

import cn.innovativest.entertainment.R;
import cn.innovativest.entertainment.bean.VersionBean;
import cn.innovativest.entertainment.widget.MyProgressBar;

/**
 * 版本更新工具类
 * Created by victor on 2015/12/21.
 */
public class VersionUtils {

    private static AlertDialog dialog;

    public static boolean isUpdate(String oldVer, String newVer) {
        String[] oldArray = oldVer.split("\\.");
        String[] newArray = newVer.split("\\.");
        if (oldArray.length >= 1 && newArray.length >= 1) {
            if (Integer.parseInt(oldArray[0]) > Integer.parseInt(newArray[0])) {
                return false;
            } else if (Integer.parseInt(oldArray[0]) < Integer.parseInt(newArray[0])) {
                return true;
            } else if (oldArray.length >= 2 && newArray.length >= 2) {
                if (Integer.parseInt(oldArray[1]) > Integer.parseInt(newArray[1])) {
                    return false;
                } else if (Integer.parseInt(oldArray[1]) < Integer.parseInt(newArray[1])) {
                    return true;
                } else if (oldArray.length >= 3 && newArray.length >= 3) {
                    if (Integer.parseInt(oldArray[2]) < Integer.parseInt(newArray[2])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // 获取当前版本号名称
    public static String getVersionName(Context context) throws Exception {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packInfo.versionName;
    }

    public static void showDialog(final Context context, final VersionBean versionBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.loading_dialog_style);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(context, R.layout.dialog_down, null);
        dialog.setView(view);
        ImageView closeView = view.findViewById(R.id.close_iv);
        TextView titleView = view.findViewById(R.id.title_tv);
        titleView.setText(String.format("发现新版本 V%s", versionBean.getVerName()));
        TextView contentView = view.findViewById(R.id.content_tv);
        contentView.setText("版本说明");
        final MyProgressBar progressBar = view.findViewById(R.id.progress_bar);
        final TextView cancleView = view.findViewById(R.id.cancel_tv);
        final TextView okView = view.findViewById(R.id.ok_tv);
        View lineView = view.findViewById(R.id.qiangzhi_line);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (context instanceof Activity) ((Activity) context).finish();
            }
        });
        cancleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoHttp.getDownloadQueueInstance().cancelAll();
                dialog.dismiss();
                if (context instanceof Activity) ((Activity) context).finish();
            }
        });
        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (versionBean.getApkUrl().toLowerCase().contains(".apk")) {
                    DownFile(context, versionBean, progressBar, okView);
                } else if (versionBean.getApkUrl().toLowerCase().startsWith("http")) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(versionBean.getApkUrl());//splitflowurl为分流地址
                        intent.setData(content_url);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception e) {

                    }
                } else {
                    ToastUtils.showShort(context, "下载地址错误");
                }

            }
        });
        if (versionBean.getIsForced().equals("1")) {
            closeView.setVisibility(View.GONE);
            cancleView.setVisibility(View.GONE);
            lineView.setVisibility(View.GONE);
        }
        dialog.show();
    }

    private static void DownFile(final Context context, final VersionBean versionBean, final MyProgressBar progressBar, final TextView okView) {
        DownloadRequest downloadRequest = NoHttp.createDownloadRequest(versionBean.getApkUrl(), context.getExternalCacheDir().getPath() + "/download/", "hlg-v" + versionBean.getVerName() + ".apk", true, false);
        NoHttp.getDownloadQueueInstance().add(0, downloadRequest, new DownloadListener() {
            @Override
            public void onDownloadError(int what, Exception exception) {
                ToastUtils.showShort(context, "未知错误，下载失败，请稍后重试");
                if (context instanceof Activity) ((Activity) context).finish();
            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
                if (versionBean.getIsForced().equals("1")) {
                    okView.setText("下载中");
                    okView.setOnClickListener(null);
                } else {
                    okView.setText("最小化");
                    okView.setOnClickListener(null);
                }
            }

            @Override
            public void onProgress(int what, int progress, long fileCount, long speed) {
                progressBar.setMyProgress(progress);
            }

            @Override
            public void onFinish(int what, String filePath) {
                dialog.dismiss();
                installApk(context, filePath);
            }

            @Override
            public void onCancel(int what) {

            }
        });
    }

    private static void installApk(Context context, String apkPath) {
        if (context == null || TextUtils.isEmpty(apkPath)) {
            ToastUtils.showShort(context, "安装失败");
            return;
        }
        File file = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.getUriForFile(context,
                    "cn.innovativest.entertainment.fileprovider", file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
        //if (context instanceof Activity) ((Activity) context).finish();
    }
}
