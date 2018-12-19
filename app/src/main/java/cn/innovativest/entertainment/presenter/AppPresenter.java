package cn.innovativest.entertainment.presenter;


import java.util.List;

import cn.innovativest.entertainment.base.BasePresent;
import cn.innovativest.entertainment.bean.AppBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.rxbase.RetrofitFactory;
import cn.innovativest.entertainment.view.AppView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Victor on 2018/3/14.
 */

public class AppPresenter extends BasePresent<AppView> {
    public void requestAppList(String appName) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "phone=" );
        add(RetrofitFactory.getInstance().getApiService().getAppList(requestBody), new Consumer<HttpRespond<List<AppBean>>>() {
            @Override
            public void accept(HttpRespond<List<AppBean>> respond) throws Exception {
                view.showAppList(respond);
            }
        });
    }

}
