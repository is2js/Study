package com.mdy.android.viewpagerexam.domain;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MDY on 2017-08-03.
 */

public interface ApiService {
    public static final String API_URL = "http://crusia.xyz/";


    @GET("apis/house/")
    Call<List<RoomsData>> getTotalHouse();

}