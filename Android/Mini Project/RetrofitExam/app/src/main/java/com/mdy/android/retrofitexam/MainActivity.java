package com.mdy.android.retrofitexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mdy.android.retrofitexam.domain.Data;
import com.mdy.android.retrofitexam.domain.House_images;
import com.mdy.android.retrofitexam.domain.RoomsData;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mdy.android.retrofitexam.R.id.img1;
import static com.mdy.android.retrofitexam.R.id.img2;
import static com.mdy.android.retrofitexam.R.id.img3;
import static com.mdy.android.retrofitexam.R.id.img4;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Retrofit retrofit;
    ApiService apiService;

    Button btnHouse, btnUser, btnTotalHouse, btnGoList, btnGoAllList;
    TextView txtResult;
    EditText editPrimaryKey;
    ImageView img[] = new ImageView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();




    }

    public void setViews(){
        btnHouse = (Button) findViewById(R.id.btnHouse);
        btnUser = (Button) findViewById(R.id.btnUser);
        btnTotalHouse = (Button) findViewById(R.id.btnTotalHouse);
        btnGoList = (Button) findViewById(R.id.btnGoList);
        btnGoAllList = (Button) findViewById(R.id.btnGoAllList);
        txtResult = (TextView) findViewById(R.id.txtResult);
        editPrimaryKey = (EditText) findViewById(R.id.editPrimaryKey);
        img[0] = (ImageView) findViewById(img1);
        img[1] = (ImageView) findViewById(img2);
        img[2] = (ImageView) findViewById(img3);
        img[3] = (ImageView) findViewById(img4);

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public void setListeners(){
        btnHouse.setOnClickListener(this);
        btnUser.setOnClickListener(this);
        btnTotalHouse.setOnClickListener(this);
        btnGoList.setOnClickListener(this);
        btnGoAllList.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHouse :
                Call<ResponseBody> house = apiService.getHouse(editPrimaryKey.getText().toString());
                house.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String jsonString = response.body().string();
                            Log.w("==================== house 1 ", "house 1 " + jsonString);
//                            txtResult.setText(response.body().string());


                            Gson gson = new Gson();
                            Data data = gson.fromJson(jsonString, Data.class);

                            RoomsData roomsData = new RoomsData();

                            roomsData.price_per_day = data.getPrice_per_day();
                            roomsData.introduce = data.getIntroduce();
                            roomsData.room_type = data.getRoom_type();
                            House_images[] imagesEx = data.getHouse_images();
                            roomsData.image = imagesEx[0].getImage();

                            TotalData.totalData.add(roomsData);



//                            House_images  house_images = gson.fromJson(jsonString, House_images.class);

                            Log.w("data 확인", data.getPrice_per_day());
                            txtResult.setText("price_per_day : " + data.getPrice_per_day() + "\n"
                                            + "introduce : " + data.getIntroduce() + "\n"
                                            + "room_type : " + data.getRoom_type() + "\n");

                            //TODO 배열 길이 체크 필요

                            House_images[] images = data.getHouse_images();
//                            Glide.with(MainActivity.this)
//                                    .load(images[0].getImage())
//                                    .into(img1);
//                            Glide.with(MainActivity.this)
//                                    .load(images[1].getImage())
//                                    .into(img2);
//                            Glide.with(MainActivity.this)
//                                    .load(images[2].getImage())
//                                    .into(img3);
//                            Glide.with(MainActivity.this)
//                                    .load(images[3].getImage())
//                                    .into(img4);

                            for( int i=0 ; i<images.length ; i++){
                                Glide.with(MainActivity.this)
                                        .load(images[i].getImage())
                                        .into(img[i]);
                            }

                            Log.w("====================== img Url", "img Url" + images[0].getImage());
//                            Glide.with(MainActivity.this)
//                                    .load(data.getHouse_images())
//                                    .into(img);





                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;
            case R.id.btnUser :
                Call<ResponseBody> user = apiService.getUser(editPrimaryKey.getText().toString());
                user.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.w("==================== user ", "user" + response.body().string());
//                            txtResult.setText(response.body().string());


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;
            case R.id.btnTotalHouse :
                Call<List<Data>> totalHouse = apiService.getTotalHouse();
                totalHouse.enqueue(new Callback<List<Data>>() {
                    @Override
                    public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                        try {
                            String jsonString = response.body().toString();
                            Log.w("==================== total house ", "total house" + jsonString);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                     }

                    @Override
                    public void onFailure(Call<List<Data>> call, Throwable t) {

                    }
                });
            case R.id.btnGoList :
                Intent intent1 = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.btnGoAllList :
                Intent intent2 = new Intent(MainActivity.this, AllListActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}