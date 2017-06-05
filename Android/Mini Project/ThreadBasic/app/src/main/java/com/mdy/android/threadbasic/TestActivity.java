package com.mdy.android.threadbasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class TestActivity extends AppCompatActivity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // 1. 버튼을 클릭하면 1 부터 100까지 출력하는 함수를 실행
        findViewById(R.id.btn10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogThread().start();
            }
        });

        // 2. Thread 클래스 에서 1부터 100까지 출력하는 함수를 실행
        new LogThread().start();

        // 3. 위의 Thread 클래스의 실행순서를 1번과 바꿔서 실행
    }

    public void print100T(String caller){
        for(int i=0; i<100; i++){
            Log.i("Thread Test", caller +" : Current Number======="+i);
            try {
                Thread.sleep(1000);
                // Log를 찍고, 1초를 쉬게 해주는 것인데
                // Exception이 발생할 수 있어서 try ~ catch문으로 감싸줬다.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class LogThread extends Thread {
        @Override
        public void run() {
            count++;
            print100T("SubThread" + count);
        }
    }
}
