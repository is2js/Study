package com.mdy.android.multiplecounter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViews[] = new TextView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0 ; i<4; i++) {
            // 텍스트로 아이디 가져오기
            int resId = getResources().getIdentifier("textView"+(i+1), "id", getPackageName());
            textViews[i] = (TextView) findViewById(resId);
        }

        // 생성부
        Counter counter1 = new Counter(textViews[0], this);
        Counter counter2 = new Counter(textViews[1], this);
        Counter counter3 = new Counter(textViews[2], this);
        Counter counter4 = new Counter(textViews[3], this);

        // 실행부
        counter1.start();
        counter2.start();
        counter3.start();
        counter4.start();
    }
}


class Counter extends Thread {
    Activity context;   // runOnUiThread()가 activity에 있는 것이라서.
    TextView textView;
    int count=0;

    public Counter(TextView textView, Activity context){
        this.textView = textView;
        this.context = context;
    }

    @Override
    public void run(){

        for(int i=0; i<10; i++){


            // 서브 thread 에서 UI를 조작하기 위해 로직을 Main Thread에 붙여준다.
            count++;
            context.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(count+""); // <- 여기만 메인스레드에서 동작한다.
                        }
                    }
            );


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}



/*class Counter {
    TextView textView;
    int count=0;

    public Counter(TextView textView){
        this.textView = textView;
    }

    public void start(){

        for(int i=0; i<10; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 로직 중간에 새로운 thread를 생성해서 텍스트에 값을 세팅해준다.
            new Thread(){
                public void run(){
                    textView.setText(count+"");
                }
            }.start();
            count++;
        }
    }

}*/
