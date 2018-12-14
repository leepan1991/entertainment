package cn.innovativest.entertainment.view;

import cn.innovativest.entertainment.bean.LoginBean;
import cn.innovativest.entertainment.common.HttpRespond;

/**
 * Created by Administrator on 2018/3/6.
 */

public interface LoginView {

    void loginSuccess(HttpRespond<LoginBean> respond);
}
