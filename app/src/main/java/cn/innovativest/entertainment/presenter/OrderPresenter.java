package cn.innovativest.entertainment.presenter;

import java.util.List;

import cn.innovativest.entertainment.base.BasePresent;
import cn.innovativest.entertainment.bean.OrderBean;
import cn.innovativest.entertainment.bean.RecordBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.rxbase.RetrofitFactory;
import cn.innovativest.entertainment.view.OrderView;
import cn.innovativest.entertainment.view.RecordView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by victor on 2018/4/11.
 */

public class OrderPresenter extends BasePresent<OrderView> {
    public void getOrder(String phone) {
//        JSONObject requestData = new JSONObject();
//        try {
//            requestData.put("phone", phone);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "phone=" + phone);
        add(RetrofitFactory.getInstance().getApiService().getOrder(requestBody), new Consumer<HttpRespond<List<OrderBean>>>() {
            @Override
            public void accept(HttpRespond<List<OrderBean>> respond) throws Exception {
                view.getOrder(respond);
            }
        });
    }
}
