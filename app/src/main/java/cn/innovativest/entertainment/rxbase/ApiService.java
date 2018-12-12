package cn.innovativest.entertainment.rxbase;

import cn.innovativest.entertainment.bean.TabBean;
import cn.innovativest.entertainment.common.HttpRespond;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface ApiService {

//    @POST(APIFactory.GETORDERLIST)
//    Observable<HttpRespond<List<OrderBean>>> GetOrderList(@Body RequestBody requestBody);
//
//    @POST(APIFactory.CANCERORDER)
//    Observable<HttpRespond> cancelOrder(
//            @Body RequestBody requestBody);

    @GET(APIFactory.BOTTOM_URL)
    Observable<HttpRespond<TabBean>> getBottomTab();
}
