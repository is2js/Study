package com.mdy.android.threadasynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int SET_DONE = 1;
    TextView textView;

    // thread 에서 호출하기 위한 handler
    // Handler를 만들고 handleMessage() 메소드를 Override해준다.
    /*Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SET_DONE:
                    setDone();
                    break;
            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAsync();
            }
        });


        /* ProgressDialog에 관한 주석 설명 */
        /* // 메인(onCreate)에 놓고 사용하려면 ProgressDialog progress;를 위에 선언해야 한다.
        // 그러나 AsyncTask 내부에 ProgressDialog를 사용할 경우에는 AsyncTask 내부에 ProgressDialog progress;를 선언하고 사용한다.

        // progress는 화면에 진행상태를 표시
        // ProgressDialog 정의
        progress = new ProgressDialog(this);
        // 여기에는 getBaseContext()를 넘겨주면 오류가 난다.
        // getBaseContext()는 Context의 리소스만 갖고 있기 때문에
        // 그러나 지금은 Dialog가 Context말고 theme라는 것을 쓴다. (액티비티의 스타일 요소)

        // progress 사용시 기본적으로 필요한 아래 3가지를 세팅해주면 된다.
        progress.setTitle("진행중...");
        progress.setMessage("진행중 표시되는 메시지입니다");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);    // 빙글빙글 돌아가는거 */


    }

    private void setDone() {
        textView.setText("Done!!!");
    }


    private void runAsync(){
        new AsyncTask<String, Integer, Float>(){

            ProgressDialog progress;

            // 제네릭 타입 1 - doInBackground 의 인자
            //             2 - onProgressUpdate 의 인자
            //             3 - doInBackground 의 리턴타입 & onPostExecute 의 인자

            // onPreExecute()는 doInBackground 가 호출되기 전에 먼저 호출된다.
            @Override
            protected void onPreExecute() {
                // progress는 화면에 진행상태를 표시
                // ProgressDialog 정의
                progress = new ProgressDialog(MainActivity.this);
                progress.setTitle("진행중...");
                progress.setMessage("진행중 표시되는 메시지입니다");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                // 프로그래스 창을 호출
                progress.show();
            }

            // AsyncTask의 doInBackground 메소드는 Thread의 run과 같은 역할이다.
            @Override
            protected Float doInBackground(String... params) { // 데이터를 처리...
                // doInBackground() 메소드에 인자를 String으로 넣어주면
                // params[0], params[1], params[2] ... 이런 식으로 배열 형태로 값을 받게 된다.
                // 인자를 넣어줄때는 execute를 통해 넣어준다.
                // ex)  .execute("안녕", "하세요");
                // 같은 타입인 여러개의 인자로 넘어가면 꺼냈을때 배열처럼 꺼내쓸 수 있다.
                // 안녕하세요를 execute에 넘기면 execute가 doInBackground() 를 직접 호출해주는 것이다.
                // 그런데 doInBackground()가 호출이 되기 전에, onPreExecute()가 먼저 자동으로 호출이 된다.
                try {
                    Log.e("AsyncTask", "첫번째값" + params[0]);     // '안녕'  이 출력
                    Log.e("AsyncTask", "두번째값" + params[1]);     // '하세요'   가 출력
                    for(int i=0 ; i<10 ; i++) {
                        publishProgress(i*10); // <- onProgressUpdate 를 주기적으로 업데이트 해준다.
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 1000.4f; // 리턴되는 값은 onPostExecute의 인자로 넘겨준다.
            }

            // doInBackground 가 처리되고 나서 호출된다.
            @Override
            protected void onPostExecute(Float result) {
                Log.e("AsyncTask","doInBackground의 결과값="+result);
                // 결과값을 메인 UI 에 세팅하는 로직을 여기에 작성한다.
                setDone();
                // 프로그래스 창을 해제
                progress.dismiss();
            }

            // 주기적으로 doInBackground 에서 호출이 가능한 함수
            @Override
            protected void onProgressUpdate(Integer... values) {
                progress.setMessage("진행율 = " + values[0] + "%");
            }
        }.execute("안녕", "하세요"); // <- doInBackground 를 실행시켜준다.

    }

/*    private void runThread() {
        // 프로그래스 창을 호출
        progress.show();
        CustomThread thread = new CustomThread(handler);
        thread.start();
    }*/


}



// 생성자를 통해 MainActivity의 handler를 넘겨준다.
/*
class CustomThread extends Thread {
    Handler handler;

    public CustomThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);    // 10초 후에
            // Main UI 에 현재 thread 가 접근할 수 없으므로
            // handler 를 통해 호출해준다.
            // 메세지객체를 직접 생성하지 않고, 값을 넘기지 않을 때는 what에 해당하는 메세지만 넘겨주면 된다.
            // 그게 sendEmptyMessage()이다.
            handler.sendEmptyMessage(MainActivity.SET_DONE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}*/
