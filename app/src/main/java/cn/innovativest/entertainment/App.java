package cn.innovativest.entertainment;


import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * 初始化操作
 * Created by Victor on 2018/3/10.
 */

public class App extends MultiDexApplication {

    private static App my;

//    static {
//        // todo QQ分享配置
//        PlatformConfig.setQQZone("1105428021", "HBEie4AnwaGw7k0i");
//        PlatformConfig.setWeixin("wxf960fe81a2dc30d4", "565d624a5e4dfdd8b3519d7f35af26a1");
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        my = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        CrashReport.initCrashReport(getApplicationContext(), "0f93a8573b", false);
//        // 初始化友盟
//        UMConfigure.init(this, "5aaf5392f29d987d2600014e",
//                "UmengHuanlegou", UMConfigure.DEVICE_TYPE_PHONE, null);
//        UMConfigure.setLogEnabled(true);

    }

    public static App get() {
        return my;
    }

}
