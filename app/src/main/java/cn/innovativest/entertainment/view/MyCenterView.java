package cn.innovativest.entertainment.view;

import cn.innovativest.entertainment.bean.TabBean;
import cn.innovativest.entertainment.common.HttpRespond;

/**
 * Created by victor on 2018/4/11.
 */

public interface MyCenterView {
    void onGetBottomTab(HttpRespond<TabBean> respond);
}
