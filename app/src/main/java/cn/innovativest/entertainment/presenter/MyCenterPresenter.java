package cn.innovativest.entertainment.presenter;

import cn.innovativest.entertainment.base.BasePresent;
import cn.innovativest.entertainment.bean.UserInfoBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.rxbase.RetrofitFactory;
import cn.innovativest.entertainment.view.MyCenterView;
import io.reactivex.functions.Consumer;

/**
 * Created by victor on 2018/4/11.
 */

public class MyCenterPresenter extends BasePresent<MyCenterView> {
    public void getUserInfo() {
        add(RetrofitFactory.getInstance().getApiService().getUserInfo(), new Consumer<HttpRespond<UserInfoBean>>() {
            @Override
            public void accept(HttpRespond<UserInfoBean> respond) throws Exception {
                view.getUserInfo(respond);
            }
        });
    }
}
