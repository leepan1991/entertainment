package cn.innovativest.entertainment.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import cn.innovativest.entertainment.base.BasePresent;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.rxbase.RetrofitFactory;
import cn.innovativest.entertainment.view.AuthView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/3/7.
 */

public class AuthPresenter extends BasePresent<AuthView> {


    public void auth(final String phone, final String password, final String password1) {
        JSONObject requestData = new JSONObject();
        try {
            // 生成私有token
            requestData.put("phone", phone);
            requestData.put("password", password);
            requestData.put("password1", password1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "phone=" + phone + "&password=" + password + "&password1=" + password1);
        add(RetrofitFactory.getInstance().getApiService().auth(requestBody), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond respond) throws Exception {
                view.authComplete(respond);
            }
        });

    }
}
