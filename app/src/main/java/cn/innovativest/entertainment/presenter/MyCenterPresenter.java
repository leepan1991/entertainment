package cn.innovativest.entertainment.presenter;

import cn.innovativest.entertainment.base.BasePresent;
import cn.innovativest.entertainment.bean.UserInfoBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.rxbase.RetrofitFactory;
import cn.innovativest.entertainment.view.MyCenterView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by victor on 2018/4/11.
 */

public class MyCenterPresenter extends BasePresent<MyCenterView> {
    public void getUserInfo(String phone) {
//        JSONObject requestData = new JSONObject();
//        try {
//            requestData.put("phone", phone);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "phone=" + phone);
        add(RetrofitFactory.getInstance().getApiService().getUserInfo(requestBody), new Consumer<HttpRespond<UserInfoBean>>() {
            @Override
            public void accept(HttpRespond<UserInfoBean> respond) throws Exception {
                view.getUserInfo(respond);
            }
        });
    }

    public void recharge(String phone, String recharge) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "phone=" + phone + "&recharge=" + recharge + "&type=ATH手办娱乐" + "&bid=");
        add(RetrofitFactory.getInstance().getApiService().recharge(requestBody), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond respond) throws Exception {
                view.rechargeComplete(respond);
            }
        });
    }
}
