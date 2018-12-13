package cn.innovativest.entertainment.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import cn.innovativest.entertainment.base.BasePresent;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.rxbase.RetrofitFactory;
import cn.innovativest.entertainment.view.LoginView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/3/6.
 */

public class LoginPresenter extends BasePresent<LoginView> {

    public void doLogin(final String phone, final String password) {
        JSONObject requestData = new JSONObject();
        try {
            // 生成私有token
            requestData.put("phone", phone);
            requestData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        add(RetrofitFactory.getInstance().getApiService().login(requestBody), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond respond) throws Exception {
                view.loginSuccess(respond);
            }
        });
    }

}
