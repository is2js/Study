package com.mdy.android.activitycontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.mdy.android.activitycontrol.R.id.subEditText;
import static com.mdy.android.activitycontrol.R.id.subTextView;

public class SubActivity extends AppCompatActivity {


    TextView sTextView;
    EditText sEditText;
    Button button;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        sTextView = (TextView) findViewById(subTextView);
        sEditText = (EditText) findViewById(subEditText);
        button = (Button) findViewById(R.id.button);

        // 1. 이전 activity에서 넘어온 intent 객체
        Intent intent = getIntent(); // 여기는 null이 안된다.
        // 2. 값의 묶음을 꺼낸다. // getExtras()의 반환값은 bundle 형태이다.
        Bundle bundle = intent.getExtras(); // 여기는 전달된 값이 없으면 null이 된다.
        // 3. 단일 값을 꺼낸다. 꺼내기 전에 null 체크를 해줘야 한다.
        if(bundle != null) {
            value = bundle.getString("key");     // .putExtra("변수", "값")으로
            sTextView.setText(value);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity에서 넘겨받은 값을 int로 변환
                int num1 = Integer.parseInt(value);
                // 현재 Activity에 입력된 값을 받아서
                String temp = sEditText.getText().toString();
                // int로 변환
                int num2 = Integer.parseInt(temp);

                int result = num1 + num2;

                /* 값 반환하기 */

                // 결과값을 intent에 담아서
                Intent intent = new Intent();
                intent.putExtra("result", result);

                // setResult에 넘겨준다.
                setResult(RESULT_OK, intent);

                // 3. 현재 activity를 종료한다.
                finish();
            }
        });


        // 이렇게 한번에 해줘도 된다.
        // getIntent().getExtras().getString("key");

        // 위의 두 줄(2,3번)을 합쳐놓은 method
        // - 자체적으로 bundle에 대한 null 처리 로직을 포함하고 있다.
        // String value = intent.getStringExtra("key");



    }
}
