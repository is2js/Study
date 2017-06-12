package com.mdy.android.httpurlconnection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "naver.com";
        String result = getData(url);

        Log.i("Result", result);

    }

    // 인자로 받은 url로 네트웍을 통해 데이터를 가져오는 함수
    // 처음에 데이터를 가져온 순간에는 무조건 string 이다.
    // 그래서 리턴타입은 String 이다.
    public String getData(String url) throws Exception {    // <- 요청한 곳에서 Exception 처리를 해준다.
        String result = "";

        // 네트웍 처리
        // 1. 서버 url 연결
        // 1.1 URL 객체 만들기
        URL serverUrl = new URL(url);

        /* HttpURLConnection */
        // DBHelper랑 비슷한 역할을 함.
        // 네트웍이랑 연결해주는 역할

        // 1.2 연결객체 생성
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection(); // url 객체에서 연결을 꺼낸다.


        return result;
    }
}
