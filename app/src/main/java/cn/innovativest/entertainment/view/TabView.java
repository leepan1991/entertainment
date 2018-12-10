package cn.innovativest.entertainment.view;

import cn.innovativest.entertainment.bean.VersionBean;
import cn.innovativest.entertainment.common.HttpRespond;

/**
 * Created by Yangli on 2018/4/11.
 */

public interface TabView {
    void onGetTaoPwd(HttpRespond<VersionBean> respond);
}
