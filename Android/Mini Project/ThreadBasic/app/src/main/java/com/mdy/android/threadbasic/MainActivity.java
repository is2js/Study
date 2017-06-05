package com.mdy.android.threadbasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Thread 실행
        Thread thread = new Thread(){
            @Override
            public void run() {
                Log.i("Thread Test", "Hello Thread!");
            }
        };

        // 2. Thread 실행
        thread.start(); // run() 함수를 실행시켜준다.
    }
}
