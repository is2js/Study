package com.mdy.android.activitycontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.mdy.android.activitycontrol.R.id.mainEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonStart, buttonForResult;
    Intent intent;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, SubActivity.class);

        buttonStart = (Button) findViewById(R.id.btnStart);
        buttonForResult = (Button) findViewById(R.id.btnForResult);
        mEditText = (EditText) findViewById(mainEditText);

        buttonStart.setOnClickListener(this);
        buttonForResult.setOnClickListener(this);



    }

    public static final int BUTTON_START = 98;
    public static final int BUTTON_RESULT = 99;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 일반적인 Activity start
            case R.id.btnStart:
                intent.putExtra("key", "0");  //  putExtra("변수", "값")
                startActivityForResult(intent, BUTTON_START);
                break;
            // 값을 돌려받는 Activity start
            case R.id.btnForResult:
                intent.putExtra("key", mEditText.getText().toString());  //  putExtra("변수", "값")

                startActivityForResult(intent, BUTTON_RESULT);
                // startActivityForResult 두번째 인자는 호출받는 곳에서 구별할 수 있게 해주는 구분자이다.

                // > start SubActivity > finish() > 결과값을 돌려준다. > MainActivity.onActivityResult(결과값) 메소드를 시스템이 호출한다.
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                                                                     // Intent에 결과값이 담겨온다.

        // requestCode에는 BUTTON_RESULT가 담긴다.
        // resultCode는 반환하는 쪽에서 세팅해준다. (여기서는 SubActivity.java쪽에서 RESULT_OK를 보내줬음.)


//        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "Result Code"+resultCode, Toast.LENGTH_SHORT).show();

        int result;

        if(resultCode == RESULT_OK)
            switch(requestCode){
                case BUTTON_RESULT :
                    // Intent인 data에서 result 변수로 값을 꺼내는데 값이 없을 경우 디폴트값으로 0 을 사용한다.
                    result = data.getIntExtra("result", 7); // 뒤에 인자는 디폴트값(값이 아예 안넘어왔을 경우)
                    // SubActivity.java 소스에서  이 부분(intent.putExtra("result", result);)을 주석처리해주면 결과값이 7로 넘어온다.

                    mEditText.setText("결과값="+result);
                    Toast.makeText(this, "Result 버튼을 눌렀다가 돌아옴", Toast.LENGTH_SHORT).show();
                    break;
                case BUTTON_START :
                    result = data.getIntExtra("result", 7);
                    mEditText.setText("결과값="+result);
                    Toast.makeText(this, "Start 버튼을 눌렀다가 돌아옴", Toast.LENGTH_SHORT).show();

            }
        }
}
