package cn.innovativest.entertainment.rxbase;

import java.util.List;

import cn.innovativest.entertainment.ath.CommonItem;
import cn.innovativest.entertainment.bean.AppBean;
import cn.innovativest.entertainment.bean.EBuyBean;
import cn.innovativest.entertainment.bean.LoginBean;
import cn.innovativest.entertainment.bean.OrderBean;
import cn.innovativest.entertainment.bean.RecordBean;
import cn.innovativest.entertainment.bean.TabBean;
import cn.innovativest.entertainment.bean.UserInfoBean;
import cn.innovativest.entertainment.common.HttpRespond;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface ApiService {


    @GET(APIFactory.BOTTOM_URL)
    Observable<HttpRespond<TabBean>> getBottomTab();

    @POST(APIFactory.GET_USER_INFO)
    Observable<HttpRespond<UserInfoBean>> getUserInfo(@Body RequestBody requestBody);

    @POST(APIFactory.LOGIN)
    Observable<HttpRespond<LoginBean>> login(@Body RequestBody requestBody);

    @POST(APIFactory.AUTH)
    Observable<HttpRespond> auth(@Body RequestBody requestBody);

    @GET(APIFactory.DEFAULT_PAGE)
    Observable<HttpRespond<TabBean>> getDefaultPage();

    @POST(APIFactory.RECORD)
    Observable<HttpRespond<List<RecordBean>>> getRecord(@Body RequestBody requestBody);

    @POST(APIFactory.ORDER)
    Observable<HttpRespond<List<OrderBean>>> getOrder(@Body RequestBody requestBody);

    @POST(APIFactory.RENEW)
    Observable<HttpRespond<EBuyBean>> renew(@Body RequestBody requestBody);

    @POST(APIFactory.BUY_SUIT)
    Observable<HttpRespond> buySuit(@Body RequestBody requestBody);

    @POST(APIFactory.RECHARGE)
    Observable<HttpRespond> recharge(@Body RequestBody requestBody);

    @GET(APIFactory.APP)
    Observable<HttpRespond<List<AppBean>>> getAppList(@Query("name") String name);
}
