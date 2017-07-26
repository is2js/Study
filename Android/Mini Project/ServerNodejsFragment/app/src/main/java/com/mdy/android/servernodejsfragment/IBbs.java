package com.mdy.android.servernodejsfragment;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by MDY on 2017-07-25.
 */

public interface IBbs {
    public static final String SERVER = "http://192.168.10.79/";    // Retrofit을 사용할 때, 반드시 주소 뒤에 슬래시(/)를 붙여줘야 한다.
    @GET("bbs")
    public Observable<ResponseBody> read();

    @POST("bbs")
    public Observable<ResponseBody> write(@Body RequestBody body);

    @PUT("bbs")
    public Observable<ResponseBody> update(Bbs bbs);

    @DELETE("bbs")
    public Observable<ResponseBody> delete(Bbs bbs);
}