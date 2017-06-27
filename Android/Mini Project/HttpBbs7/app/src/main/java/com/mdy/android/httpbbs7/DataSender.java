package com.mdy.android.httpbbs7;

import android.os.AsyncTask;
import android.util.Log;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by MDY on 2017-06-27.
 */

public class DataSender {

    public void sendData(String url, String jsonString, final CallBack callBack){

        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                String url = params[0];
                String jsonString = params[1];

                boolean success = sendJsonStringByUrl(url, jsonString);

                return success;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                callBack.call(result);
            }
        }.execute(url, jsonString);

    }

    public interface CallBack {
        public void call(boolean result);
    }

    private boolean sendJsonStringByUrl(String url, String jsonString){
        try {
            // 1. 서버와 연결하기
            URL serverUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();

            // 2. 전송방식 결정
            con.setRequestMethod("POST");

            // 3. 데이터 전송
            con.setDoOutput(true);

            // 4. 키=값의 형태로 전송할 데이터 모양을 만들어주고,
            String data = "jsonString=" + URLEncoder.encode(jsonString, "utf-8");
            OutputStream os = con.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            // 5. 전송결과 체크
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            }

            os.close();
            Log.e("HttpError", "errorCode=" + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
