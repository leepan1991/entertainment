package cn.innovativest.entertainment.view;

import java.util.List;

import cn.innovativest.entertainment.bean.AppBean;
import cn.innovativest.entertainment.common.HttpRespond;

/**
 * Created by Victor on 2018/3/14.
 */

public interface AppView {
    void showAppList(HttpRespond<List<AppBean>> respond);

}
