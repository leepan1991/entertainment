package cn.innovativest.entertainment.ath;

import android.content.Context;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    public static AthService getService(final Context mCtx) {

        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request orgRequest = chain.request();
                final Request newRequest = orgRequest.newBuilder()
                        .build();

                Response response = chain.proceed(newRequest);
                return response;
            }
        })
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS);

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ath.pub/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client.build())
                .build();

        return retrofit.create(AthService.class);
    }

    public static final void main(String[] args) {

//        RetrofitClient client = new RetrofitClient();
//        client.baseUrl = "https://api.internationalsos.com";
//        client.authorization = "57260aee5f0391000100000f33a2e402e36d4580619865c1704db6f6";
//
//        try {
//            AlertsResponse response = client.getService().getAlerts().execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
