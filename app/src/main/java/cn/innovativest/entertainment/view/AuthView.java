package cn.innovativest.entertainment.view;

import cn.innovativest.entertainment.base.BaseView;
import cn.innovativest.entertainment.common.HttpRespond;

/**
 * Created by Administrator on 2018/3/7.
 */

public interface AuthView extends BaseView {

    void authComplete(HttpRespond respond);

}
