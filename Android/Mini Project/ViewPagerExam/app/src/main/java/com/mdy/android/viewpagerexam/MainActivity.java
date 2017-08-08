package com.mdy.android.viewpagerexam;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.mdy.android.viewpagerexam.domain.ApiService;
import com.mdy.android.viewpagerexam.domain.RoomsData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    ApiService apiService;
    List<RoomsData> dataList = new ArrayList<>();

    CustomAdapter customAdapter;

    ViewPager viewPager1, viewPager2;
    TextView txtTitle1, txtTitle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        getData();




        viewPager2.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        viewPager2.setCurrentItem(0);


    }




    private void setViews(){
        viewPager1 = (ViewPager) findViewById(R.id.viewPager1);
        viewPager2 = (ViewPager) findViewById(R.id.viewPager2);
        txtTitle1 = (TextView) findViewById(R.id.txtTitle1);
        txtTitle2 = (TextView) findViewById(R.id.txtTitle2);
    }

    private void getData(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        Call<List<RoomsData>> totalHouse = apiService.getTotalHouse();
        totalHouse.enqueue(new Callback<List<RoomsData>>() {
            @Override
            public void onResponse(Call<List<RoomsData>> call, Response<List<RoomsData>> response) {
                try {
                    dataList = response.body();
                    Log.e("=========== dataList", dataList+"");
                    customAdapter = new CustomAdapter(MainActivity.this, dataList);
                    viewPager1.setAdapter(customAdapter);
//                    viewPager1.setCurrentItem(0);
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<RoomsData>> call, Throwable t) {

            }
        });

    }
}
