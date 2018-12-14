package cn.innovativest.entertainment.view;

import java.util.List;

import cn.innovativest.entertainment.bean.OrderBean;
import cn.innovativest.entertainment.bean.RecordBean;
import cn.innovativest.entertainment.common.HttpRespond;

/**
 * Created by victor on 2018/4/11.
 */

public interface OrderView {
    void getOrder(HttpRespond<List<OrderBean>> respond);
}
