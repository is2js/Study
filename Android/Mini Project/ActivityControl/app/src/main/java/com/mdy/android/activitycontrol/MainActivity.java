package com.mdy.android.activitycontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonStart, buttonForResult;
    Intent intent;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, SubActivity.class);

        buttonStart = (Button) findViewById(R.id.btnStart);
        buttonForResult = (Button) findViewById(R.id.btnForResult);
        editText = (EditText) findViewById(R.id.editText);

        buttonStart.setOnClickListener(this);
        buttonForResult.setOnClickListener(this);



    }

    public static final int BUTTON_RESULT = 99;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 일반적인 Activity start
            case R.id.btnStart:
                startActivity(intent);
                break;
            // 값을 돌려받는 Activity start
            case R.id.btnForResult:
                intent.putExtra("key", editText.getText().toString());  //  putExtra("변수", "값")

                startActivityForResult(intent, BUTTON_RESULT);
                // startActivityForResult 두번째 인자는 호출받는 곳에서 구별할 수 있게 해주는 구분자이다.

                // > start SubActivity > finish() > 결과값을 돌려준다. > MainActivity.onActivityResult(결과값) 메소드를 시스템이 호출한다.
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                                                                     // Intent에 결과값이 담겨온다.
        super.onActivityResult(requestCode, resultCode, data);

    }
}
