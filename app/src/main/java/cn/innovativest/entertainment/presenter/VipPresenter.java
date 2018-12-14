package cn.innovativest.entertainment.presenter;

import cn.innovativest.entertainment.base.BasePresent;
import cn.innovativest.entertainment.bean.EBuyBean;
import cn.innovativest.entertainment.bean.UserInfoBean;
import cn.innovativest.entertainment.common.HttpRespond;
import cn.innovativest.entertainment.rxbase.RetrofitFactory;
import cn.innovativest.entertainment.view.MyCenterView;
import cn.innovativest.entertainment.view.VipView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by victor on 2018/4/11.
 */

public class VipPresenter extends BasePresent<VipView> {
    public void getVipInfo(String phone) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "phone=" + phone);
        add(RetrofitFactory.getInstance().getApiService().renew(requestBody), new Consumer<HttpRespond<EBuyBean>>() {
            @Override
            public void accept(HttpRespond<EBuyBean> respond) throws Exception {
                view.getVipInfo(respond);
            }
        });
    }

    public void buySuit(String phone, String type, String cardDay, String cardCredit) {
        RequestBody requestBody = null;
        if (type.equals("1")) {
            requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "phone=" + phone + "&credit=积分购买" + "&card_day=" + cardDay + "&card_credit=" + cardCredit);
        } else {
            requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "phone=" + phone + "&submit=金币购买" + "&card_day=" + cardDay + "&card_credit=" + cardCredit);
        }
        add(RetrofitFactory.getInstance().getApiService().buySuit(requestBody), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond respond) throws Exception {
                view.payComplete(respond);
            }
        });
    }
}
