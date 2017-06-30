package com.mdy.android.serverconnection;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.mdy.android.serverconnection.domain.Data;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    CustomAdapter adapter;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    // 리모트 관련 설정
    final String DOMAIN = "http://192.168.10.79:8080";
    final String SERVER_PATH = "/bbs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = (RecyclerView) findViewById(R.id.recycler);
        adapter = new CustomAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

    }

    // 데이터 로드하는 함수
    private void load(){
        run(DOMAIN + SERVER_PATH);
    }


    private void run(String url){

        // 서브 thread에서 실행
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String result = null;
                try {
                    String url = params[0];
                    result = getData(url);
                } catch(Exception e)

                {
                    Log.e("MainActivity", e.getMessage());
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                // 결과값인 json 스트링을 객체로 변환
                Gson gson = new Gson();
                Data data = gson.fromJson(result, Data.class);

                // ListView의 Adapter 에 세팅
                adapter.setData(data.bbsList);

                // ListView를 notify
                adapter.notifyDataSetChanged();
            }

        }.execute(url);
    }




    // url만 넣으면 GET방식으로 동작
    // post를 하려면 .post() 로 body를 추가
    private String getData(String url) throws IOException {
        // 요청 정보를 담고 있는 객체
        Request request = new Request.Builder()
                .url(url)
                .build();

        // 응답 정보를 받을 객체
        Response response = null;

        // 서버로 요청해서 데이터 받음
        response = client.newCall(request).execute();

        // 응답객체에서 실제 데이터만 추출
        ResponseBody resBody = response.body();

        // 데이터를 스트링으로 변환해서 리턴

        return resBody.string();    // 요청한 데이터의 전체 데이터
    }


    // 화면 xml의 위젯의 속성에 추가  ->  android:onClick="btnPost"
    public void btnPost(View view){
        Intent intent = new Intent(this, WriteActivity.class);
        startActivity(intent);
    }
}