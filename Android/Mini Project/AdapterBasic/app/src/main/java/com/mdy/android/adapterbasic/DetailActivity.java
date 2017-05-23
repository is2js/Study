package com.mdy.android.adapterbasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = (TextView) findViewById(R.id.textView);

        // Activity 에서 넘어온 값 처리하기
        // 1. intent를 꺼낸다.
        Intent intent = getIntent();
        // 2. 값의 묶음인 bundle을 꺼낸다.
        Bundle bundle = intent.getExtras(); // 데이터의 모음
        String result = "";
        // 3. bundle 이 있는지 유효성 검사를 한다.
        if(bundle != null)
            // 3.1 bundle이 있으면 값을 꺼내서 변수에 담는다.
            result =  bundle.getString(ListActivity.DATA_KEY);

        textView.setText(result);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아래와 같이 해주면 Activity Stack에 계속 쌓이는 구조이기 때문에 이렇게 안쓴다.
                /*Intent intent = new Intent(DetailActivity.this, ListActivity.class);
                startActivity(intent);*/

                finish(); // 이렇게 해줘야 스택에 쌓이지 않고, 실행된다.
            }
        });
    }
}
