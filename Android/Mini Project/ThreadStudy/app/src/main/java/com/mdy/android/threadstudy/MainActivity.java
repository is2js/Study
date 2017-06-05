package com.mdy.android.threadstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread mainThread = Thread.currentThread();
//        Log.i("프로그램 시작 스레드 이름", "프로그램 시작 스레드 이름: " + mainThread.getName());
        System.out.println("프로그램 시작 스레드 이름: " + mainThread.getName());

        ThreadA threadA = new ThreadA();
        System.out.println("작업 스레드 이름: " + threadA.getName());
        threadA.start();
//        Log.i("작업 스레드 이름", "작업 스레드 이름: " + threadA.getName());

        ThreadB threadB = new ThreadB();
        System.out.println("작업 스레드 이름: " + threadB.getName());
        threadB.start();
//        Log.i("작업 스레드 이름", "작업 스레드 이름: " + threadB.getName());
    }

}




