package com.mdy.android.httpurlconnection;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progress;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        String url = "http://google.com";
        newTask(url);
    }


    // AsyncTask를 처리해주는 newTask 메소드
    // AsyncTask를 사용하면 리턴타입이 생길 수 있을까?
    // ->  불가능하다.
    // 그 이유는 AsyncTask를 사용한다는 것은 Sub Thread에서 무엇인가를 돌린다는 것인데
    // return을 해버리면 결과가 나오기 전에 return이 되버린다.
    // 그래서 AsyncTask나 Thread를 호출하는 함수는 대부분 리턴타입이 void이다. 리턴을 할 수가 없다.
    // 언제끝날지 모르기때문에 기다릴 수 없다. 몇분 안에 끝날지 모르고, 그렇게 기다리면 시스템에 영향을 미치게 된다.
    public void newTask(String url) {
        // 서브 thread 를 생성 (리턴타입 void)

        new AsyncTask<String, Void, String>(){

            // 백그라운드 처리 함수
            // AsyncTask의 doInBackground 메소드는 Thread의 run과 같은 역할이다.
            // 쓰레드를 사용해서 쓰레드 안에서 HttpUrlConnection을 통해서 서버에 접속하고 데이터를 가져왔다. (result)
            // 그러면 결과값(result)을 화면에 출력해주려면
            // 쓰레드에서 처리한 값을 화면에 출력해주려면 서브쓰레드에서 처리한 값을 메인쓰레드로 넘겨줘야한다.
            // 그것을 하는 것이 onPostExecute() 이다.
            // 그러면 doInBackground()의 리턴타입이 바뀌고, onPostExecute()의 인자 타입이 String으로 바뀐다.
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try {
                    // getData 함수로 데이터를 가져온다.
                    // getData 함수는 throws Exception을 해줬기 때문에 try ~ catch를 해줘야 한다.
                    result = getData(params[0]);
//                    Log.i("Network", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                textView.setText(result);
            }
        }.execute(url);

    }




    // 인자로 받은 url로 네트웍을 통해 데이터를 가져오는 함수
    // *** 처음에 네트워크에서 데이터를 가져온 순간에는 무조건 string 이다. ***
    // 그래서 리턴타입은 String 이다.
    public String getData(String url) throws Exception {    // <- 요청한 곳에서 Exception 처리를 해준다.
        String result = "";

        /* 네트웍 처리 */
        // 1. 요청처리 Request (서버 url 연결)
        // 1.1 URL 객체 만들기
        URL serverUrl = new URL(url);

        /* HttpURLConnection */
        // DBHelper랑 비슷한 역할을 함.
        // 네트웍이랑 연결해주는 역할

        // 1.2 연결객체 생성
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection(); // url 객체에서 연결을 꺼낸다.
        // 1.3 http method 결정
        con.setRequestMethod("GET");


        //////////// 여기까지는 내가 서버에 무엇인가를 요청한 상태 ////////////


        // 2. 응답처리 Response
        // 2.1 응답코드 분석
        int responseCode = con.getResponseCode();   // int로 응답코드를 넘겨준다.

        // 2.2 정상적인 응답처리
        if(responseCode == HttpURLConnection.HTTP_OK){
            // 정상적인 코드 처리
            // 서버에서 데이터를 정상적으로 보내줄 수 있다는 뜻

            // 읽어온 데이터를 빠르게 처리하기 위해 Buffer에 담는다.
            // BufferReader에 담아놓았을 때, 좋은 점은 줄단위로 읽을 수 있다는 것이다.
            // 여기서는 BufferedReader로 했는데 나중에는 Text만 받는게 아니기 때문에 BufferedInputStream으로 해야 한다.
            // con.getInputStream() 을 BufferedReader가 읽을 수 있는 형태로 Wrapping을 해줘야 한다..
            BufferedReader br = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
            // 여기까지 스트림을 열어서 버퍼에 담는 것까지 해준 상태이다.

            String temp = null;
            while( (temp = br.readLine()) != null){
                // br.readLine() 으로 한줄씩 읽는다. (null이 아닐때까지)
                // readLine()은 return이 String이다.
                result += temp;
            }

        // 2.3 오류에 대한 응답처리 (서버에서 데이터를 정상적으로 보낼 수 없을 때)
        } else {
            // 각자 호출측으로 Exception 을 만들어서 넘겨줄 것~~~ (throws로 날려주는 방법이 있음.)
            Log.e("Network", "error_code="+responseCode);
        }
        return result;
    }
}
