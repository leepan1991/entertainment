package cn.innovativest.entertainment.ath;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AthService {


    @GET("xishuAndStatic")
    Observable<CommonResponse> commonInfo(@Query("id") int id);
}
