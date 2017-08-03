package com.mdy.android.retrofitexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Retrofit retrofit;
    ApiService apiService;

    Button btnHouse, btnUser;
    TextView txtResult;
    EditText editPrimaryKey;

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
        txtResult = (TextView) findViewById(R.id.txtResult);
        editPrimaryKey = (EditText) findViewById(R.id.editPrimaryKey);

        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
        apiService = retrofit.create(ApiService.class);
    }

    public void setListeners(){
        btnHouse.setOnClickListener(this);
        btnUser.setOnClickListener(this);
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
                            Log.w("==================== house ", "house" + response.body().string());
                            txtResult.setText(response.body().string());
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
                            txtResult.setText(response.body().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;
        }
    }
}
