package cn.innovativest.entertainment.view;

import java.util.List;

import cn.innovativest.entertainment.bean.RecordBean;
import cn.innovativest.entertainment.bean.UserInfoBean;
import cn.innovativest.entertainment.common.HttpRespond;

/**
 * Created by victor on 2018/4/11.
 */

public interface RecordView {
    void getRecord(HttpRespond<List<RecordBean>> respond);
}
