package com.mdy.android.httpurlconnection_interface2;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MDY on 2017-06-13.
 */

public class Remote {


    // thread 를 생성
    public static void newTask(final TaskInterface taskInterface){

        new AsyncTask<String, Void, String>(){
            // 백그라운드 처리 함수
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try {
                    // getData 함수로 데이터를 가져온다.
                    result = getData(params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                // 결과처리
                taskInterface.postExecute(result);
            }
        }.execute(taskInterface.getUrl());
    }




    // 인자로 받은 url 로 네트웍을 통해 데이터를 가져오는 함수
    public static String getData(String url) throws Exception {  // <- 요청한 곳에서 Exception 처리를 해준다.
        String result = "";

        // 네트웍 처리
        // 1. 요청처리 Request
        // 1.1 URL 객체 만들기
        URL serverUrl = new URL(url);
        // 1.2 연결객체 생성
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection(); // url 객체에서 연결을 꺼낸다.
        // 1.3 http method 결정
        con.setRequestMethod("GET");

        // 2. 응답처리 Response
        // 2.1 응답코드 분석
        int responseCode = con.getResponseCode();
        // 2.2 정상적인 응답처리
        if(responseCode == HttpURLConnection.HTTP_OK){ // 정상적인 코드 처리

            BufferedReader br = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
            String temp = null;
            while( (temp = br.readLine()) != null){
                result += temp;
            }
            // 2.3 오류에 대한 응답처리
        } else {
            // 각자 호출측으로 Exception 을 만들어서 넘겨줄것~~~
            Log.e("Network","error_code="+responseCode);
        }

        return result;
    }
}