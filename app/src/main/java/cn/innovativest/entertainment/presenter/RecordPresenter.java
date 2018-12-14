package cn.innovativest.entertainment.presenter;

import java.util.List;

import cn.innovativest.entertainment.base.BasePresent;
import cn.innovativest.entertainment.bean.RecordBean;
import cn.innovativest.entertainment.bean.UserInfoBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.rxbase.RetrofitFactory;
import cn.innovativest.entertainment.view.MyCenterView;
import cn.innovativest.entertainment.view.RecordView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by victor on 2018/4/11.
 */

public class RecordPresenter extends BasePresent<RecordView> {
    public void getRecord(String phone) {
//        JSONObject requestData = new JSONObject();
//        try {
//            requestData.put("phone", phone);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "phone=" + phone);
        add(RetrofitFactory.getInstance().getApiService().getRecord(requestBody), new Consumer<HttpRespond<List<RecordBean>>>() {
            @Override
            public void accept(HttpRespond<List<RecordBean>> respond) throws Exception {
                view.getRecord(respond);
            }
        });
    }
}
