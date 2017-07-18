package com.mdy.android.rxandroidbasic01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAddObserver;
    private Button btnDeleteObserver;
    Subject subject;
    private Button btnObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListeners();

        // 서브젝트 생성
        subject = new Subject();

        // 서브젝트 동작시작
        subject.start();    // Thread를 상속받아 만들었기 때문에 명령어를 날려주면 run이 계속 동작을 하게 된다.

    }

    int count = 0;

    private void initView() {
        btnAddObserver = (Button) findViewById(R.id.btnAddObserver);



        btnDeleteObserver = (Button) findViewById(R.id.btnDeleteObserver);


        btnObservable = (Button) findViewById(R.id.btnObservable);
    }

    private void setListeners(){
        // 버튼이 클릭될 때마다 Subject에 옵저버를 추가한다.
        btnAddObserver.setOnClickListener(v -> {
            count++;
            subject.addObserver(new ObserverImpl("Observer" + count));
        });

        // 버튼이 클릭되면 옵저버를 삭제한다.
        btnDeleteObserver.setOnClickListener(v -> {
            count--;
            subject.deleteObserver(count);
        });

        // 버튼이 클릭되면 ObservableActivity로 이동한다.
        btnObservable.setOnClickListener( v -> {
            Intent intent = new Intent(MainActivity.this, ObservableActivity.class);
            startActivity(intent);
            finish();
        });
    }


    // 옵저버의 구현체
    public class ObserverImpl implements Subject.Observer {
        String myName = "";

        public ObserverImpl(String name) {
            myName = name;
        }

        @Override
        public void notification(String msg) {
            System.out.println(myName + ":" + msg);
        }
    }
}