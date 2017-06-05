package com.mdy.android.threadbasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ThreadBasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_basic);



        // 1.1 Thread 생성 (방법1)
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                Log.i("Thread Test", "Hello Thread!");
            }
        };

        // 1.2 Thread 실행
        thread1.start(); // run() 함수를 실행시켜준다.

        // 2.1. Thread 생성 (방법2)       // Thread가 implement Runnable을 하고 있기 때문에(인터페이스 사용)
        Runnable thread2 = new Runnable() {
            @Override
            public void run() {
                Log.i("Thread Test", "Hello Runnable!");
            }
        };

        // 2.2. Threade 실행
        new Thread(thread2).start();

        // 3.2 Thread 실행 3
        CustomThread thread3 = new CustomThread();
        thread3.start();

        // 4.2 Runnable 실행
        Thread thread4 = new Thread(new CustomRunnable());
        thread4.start();


    }


    // 3.1 Thread 생성
    class CustomThread extends Thread {
        @Override
        public void run() {
            Log.i("Thread Test","Hello Custom Thread!");
        }
    }

    // 4.1 Runnable 구현      // Thread를 많이 사용할 경우 사용하는 방법
    class CustomRunnable implements Runnable {
        @Override
        public void run() {
            Log.i("Thread Test","Hello Custom Runnable!");
        }
    }

}

