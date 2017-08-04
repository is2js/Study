package com.mdy.android.retrofitexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.mdy.android.retrofitexam.domain.Data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllListActivity extends AppCompatActivity {

    private TextView txtTitle;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;

    private List<Data> dataList;
    Retrofit retrofit;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_list);
        dataList = new ArrayList<>();
        setViews();


        loader();

    }


    private void loader(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        Call<List<Data>> totalHouse = apiService.getTotalHouse();
        totalHouse.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                try {
                    dataList = response.body();
                    customAdapter = new CustomAdapter(AllListActivity.this, dataList);
                    recyclerView.setAdapter(customAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AllListActivity.this));


                    Log.e("dataList", "=============" + dataList);
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }

//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String jsonString = response.body().string();
//                    Log.e("==================== house 1 ", "house 1 " + jsonString);
//
//
//                    Gson gson = new Gson();
//                    Data data = gson.fromJson(jsonString, Data.class);
//
//                    totalData.add(data);
//
//
//                    recyclerAdapter = new RecyclerAdapter(ListActivity.this, data);
//                    recyclerView.setAdapter(recyclerAdapter);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
//
//
////                    RoomsData roomsData = new RoomsData();
////
////                    roomsData.price_per_day = data.getPrice_per_day();
////                    Log.e("==================== house 2 ", "house 2 " + roomsData.price_per_day);
////                    roomsData.introduce = data.getIntroduce();
////                    roomsData.room_type = data.getRoom_type();
////                    House_images[] imagesEx = data.getHouse_images();
////                    roomsData.image = imagesEx[0].getImage();
////
////                    Log.e("==================== house 3 ", "house 3 " + roomsData.image);
////                    aData.add(roomsData);
//
//
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });

        });

    }


    private void setViews(){
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerAll);
    }
}
