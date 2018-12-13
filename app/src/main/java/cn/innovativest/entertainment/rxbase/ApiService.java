package cn.innovativest.entertainment.rxbase;

import cn.innovativest.entertainment.bean.TabBean;
import cn.innovativest.entertainment.bean.UserInfoBean;
import cn.innovativest.entertainment.common.HttpRespond;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface ApiService {


//
//    @POST(APIFactory.CANCERORDER)
//    Observable<HttpRespond> cancelOrder(
//            @Body RequestBody requestBody);

    @GET(APIFactory.BOTTOM_URL)
    Observable<HttpRespond<TabBean>> getBottomTab();

    @GET(APIFactory.GETUSERINFO)
    Observable<HttpRespond<UserInfoBean>> getUserInfo();

    @POST(APIFactory.LOGIN)
    Observable<HttpRespond> login(@Body RequestBody requestBody);

    @POST(APIFactory.AUTH)
    Observable<HttpRespond> auth(@Body RequestBody requestBody);
}
