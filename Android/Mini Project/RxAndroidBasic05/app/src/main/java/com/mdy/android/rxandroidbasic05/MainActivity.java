package com.mdy.android.rxandroidbasic05;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    PublishSubject<String> publishSubject = PublishSubject.create();

    // 발행
    public void doPublish(View view) {
        new Thread() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    publishSubject.onNext("A" + i);     // 발행
                    Log.i("Publish", "A" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }.start();
    }

    // 구독
    public void getPublish(View view){
        publishSubject.subscribe(
            item -> Log.i("Subscribe", "item = " + item)
        );
    }

    //------------------------------------------------------------------------
    BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

    // 발행
    public void doBehavior(View view) {
        new Thread() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    behaviorSubject.onNext("B" + i);     // 발행
                    Log.i("Behavior", "B" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }.start();
    }

    //
    public void getBehavior(View view){
        behaviorSubject.subscribe(
                item -> Log.i("Subscribe", "item = " + item)
        );
    }

    public void doReplay(View view){

    }

    public void doAsync(View view){

    }
}
