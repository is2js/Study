package com.mdy.android.retrofitexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdy.android.retrofitexam.domain.Data;
import com.mdy.android.retrofitexam.domain.RoomsData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends AppCompatActivity {

    private List<RoomsData> aData;
    private RecyclerAdapter recyclerAdapter;
    private TextView txtTitle;
    private RecyclerView recyclerView;

    Retrofit retrofit;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setViews();

        aData = new ArrayList<>();


        loader();
//        recyclerAdapter = new RecyclerAdapter(this, aData);

//        recyclerView.setAdapter(recyclerAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }

    private void loader(){
        Call<ResponseBody> house = apiService.getHouse("27");
        house.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    Log.e("==================== house 1 ", "house 1 " + jsonString);

                    Gson gson = new Gson();
                    Data data = gson.fromJson(jsonString, Data.class);


                    recyclerAdapter = new RecyclerAdapter(ListActivity.this, data);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void setViews(){
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

}
