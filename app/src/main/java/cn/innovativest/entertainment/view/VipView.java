package cn.innovativest.entertainment.view;

import cn.innovativest.entertainment.bean.EBuyBean;
import cn.innovativest.entertainment.bean.UserInfoBean;
import cn.innovativest.entertainment.common.HttpRespond;

/**
 * Created by victor on 2018/4/11.
 */

public interface VipView {
    void getVipInfo(HttpRespond<EBuyBean> respond);

    void payComplete(HttpRespond respond);
}
