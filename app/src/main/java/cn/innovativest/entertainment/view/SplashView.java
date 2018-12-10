package cn.innovativest.entertainment.view;

import cn.innovativest.entertainment.bean.VersionBean;
import cn.innovativest.entertainment.common.HttpRespond;

/**
 *
 * Created by Yangli on 2018/6/1.
 */

public interface SplashView {
    void onCheckVersionDone(HttpRespond<VersionBean> respond);

    void onNetworkError(Throwable t);
}
