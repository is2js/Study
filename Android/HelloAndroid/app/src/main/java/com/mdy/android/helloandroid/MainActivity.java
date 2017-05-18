package com.mdy.android.helloandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 클릭리스너 구현하는 방법 3가지
 * 1. 위젯을 사용하는 객체가 상속받아서 구현한다.
 * 2. 객체내에서 변수로 생성한다.
 * 3. setOnClickListener 함수에 익명 객체로 전달한다.
 */

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.btnClick);


        // 1번 형태로 구현






        // 2번 형태로 구현
      /*  View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tv.setText("안녕 안드로이드!!!");
            }
        };*/


        // 3번 형태로 구현
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("안녕 안드로이드!!");
            }
        });
    }

}
