package com.mdy.android.serverconnection;

import com.mdy.android.serverconnection.domain.Bbs;
import com.mdy.android.serverconnection.domain.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by MDY on 2017-06-30.
 */

public interface BbsService {
    // 도메인 = 서버주소
    final String BASE_URL = "http://192.168.10.79:8080/";
    // @HttpMethod(" '도메인/' 을 제외한 서버주소")
    @POST("bbs")
    Call<Result> createBbs(@Body Bbs bbs);
}
