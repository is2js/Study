package com.mdy.android.rxandroidbasic01;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 사용하기 전에 gradle에 dependency 추가 : compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
 */

public class ObservableActivity extends AppCompatActivity {

    // 문자열을 발행하는 옵저버블
    Observable<String> observable;
    private Button btnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable);

        createObservable();
        bindObserver();

        initView();
        setListeners();
    }


    // 옵저버블 생성
    // onNext로 데이터를 발행
    private void createObservable() {

        // Java
//        observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("Hello RxAndroid");
//                emitter.onNext("Good to see you");
//                emitter.onComplete();
//            }
//        });
//    }

        // Lambda
        observable = Observable.create(emitter -> {
            emitter.onNext("Hello RxAndroid");
            emitter.onNext("Good to see you");
            emitter.onComplete();
        });
    }

    // 옵저버 등록
    @TargetApi(Build.VERSION_CODES.N)
    private void bindObserver() {
        // 1번 형태 : 옵저버안에 4개의 함수가 구현되어 있다
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                // 데이터가 있으면 onNext가 자동으로 실행이 된다.
                Log.e("OnNext", "============= 1" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("OnError", "xxxxxxx 1" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.w("OnComplete", "ooooooooooooooo 1 complete!");
            }
        };
        observable.subscribe(observer);

        // 2번 형태 : 람다식을 사용하기 전단계로
        //            옵저버 내에 있는 함수들을 하나씩 분리한다.
        Consumer<String> onNext = new Consumer<String>() {
            @Override
            public void accept(String str) {
                Log.e("OnNext", "============= 2" + str);
            }
        };
        Consumer<Throwable> onError = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
            }
        };
        observable.subscribe(onNext, onError);

        // 3번 형태 : 인터페이스 하나당 하나의 함수만 존재하므로 람다식으로 바로 구현
        observable.subscribe(
                str -> Log.e("OnNext", "============= 3" + str),
                throwable -> Log.e("OnError", "xxxxxxx 3" + throwable.getMessage()),
                () -> Log.w("OnComplete", "ooooooooooooooo 3 complete!")
        );
    }

    private void initView() {
        btnMain = (Button) findViewById(R.id.btnMain);
    }

    private void setListeners() {
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ObservableActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}