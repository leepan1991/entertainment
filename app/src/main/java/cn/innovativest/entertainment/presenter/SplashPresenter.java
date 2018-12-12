package cn.innovativest.entertainment.presenter;

import cn.innovativest.entertainment.base.BasePresent;
import cn.innovativest.entertainment.view.SplashView;

/**
 *
 * Created by Yangli on 2018/6/1.
 */

public class SplashPresenter extends BasePresent<SplashView> {
    public void checkVersionUpdate() {

//        Call<HttpRespond<VersionBean>> call = RetrofitFactory.getInstance().getApiService()
//                .getNewVersion(Constants.CLIENT, Constants.PACKAGE, Constants.VER);
//        call.enqueue(new Callback<HttpRespond<VersionBean>>() {
//            @Override
//            public void onResponse(@NonNull Call<HttpRespond<VersionBean>> call,
//                                   @NonNull Response<HttpRespond<VersionBean>> response) {
//                view.onCheckVersionDone(response.body());
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<HttpRespond<VersionBean>> call, @NonNull Throwable t) {
//                view.onNetworkError(t);
//            }
//        });
    }
}
