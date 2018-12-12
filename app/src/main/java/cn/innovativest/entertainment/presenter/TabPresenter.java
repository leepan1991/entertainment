package cn.innovativest.entertainment.presenter;

import cn.innovativest.entertainment.base.BasePresent;
import cn.innovativest.entertainment.bean.TabBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.rxbase.RetrofitFactory;
import cn.innovativest.entertainment.view.TabView;
import io.reactivex.functions.Consumer;

/**
 * Created by victor on 2018/4/11.
 */

public class TabPresenter extends BasePresent<TabView> {
    public void onGetBottomTab() {
        add(RetrofitFactory.getInstance().getApiService().getBottomTab(), new Consumer<HttpRespond<TabBean>>() {
            @Override
            public void accept(HttpRespond<TabBean> tabBeanHttpRespond) throws Exception {
                view.onGetBottomTab(tabBeanHttpRespond);
            }
        });
    }
}
