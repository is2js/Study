package com.mdy.android.mycalculate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // 1. 위젯 변수를 선언
    EditText displayView;
    Button button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9;
    Button button_plus, button_minus, button_multiply, button_divide, button_clean, button_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. 위젯 변수를 화면과 연결
        displayView = (EditText) findViewById(R.id.display);

        button_0 = (Button) findViewById(R.id.button_0);
        button_1 = (Button) findViewById(R.id.button_1);
        button_2 = (Button) findViewById(R.id.button_2);
        button_3 = (Button) findViewById(R.id.button_3);
        button_4 = (Button) findViewById(R.id.button_4);
        button_5 = (Button) findViewById(R.id.button_5);
        button_6 = (Button) findViewById(R.id.button_6);
        button_7 = (Button) findViewById(R.id.button_7);
        button_8 = (Button) findViewById(R.id.button_8);
        button_9 = (Button) findViewById(R.id.button_9);

        button_plus = (Button) findViewById(R.id.button_plus);
        button_minus = (Button) findViewById(R.id.button_minus);
        button_multiply = (Button) findViewById(R.id.button_multiply);
        button_divide = (Button) findViewById(R.id.button_divide);
        button_result = (Button) findViewById(R.id.button_result);
        button_clean = (Button) findViewById(R.id.button_clean);

        // 3. 클릭리스너 연결  (implements View.OnClickListener)
        button_0.setOnClickListener(this);  // this는 MainActivity
        button_1.setOnClickListener(this);  // 해당 이벤트가 발생시 this(뭔가)를 호출해준다.
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
        button_9.setOnClickListener(this);

        button_plus.setOnClickListener(this);
        button_minus.setOnClickListener(this);
        button_multiply.setOnClickListener(this);
        button_divide.setOnClickListener(this);
        button_result.setOnClickListener(this);
        button_clean.setOnClickListener(this);
    }

        @Override
        public void onClick(View button) {  // 시스템의 이벤트 리스너를 통해 호출된다.
            String data = ((Button)button).getText().toString();

            switch (button.getId()) {
                case R.id.button_0: inputData(data); break;
                case R.id.button_1: inputData(data); break;
                case R.id.button_2: inputData(data); break;
                case R.id.button_3: inputData(data); break;
                case R.id.button_4: inputData(data); break;
                case R.id.button_5: inputData(data); break;
                case R.id.button_6: inputData(data); break;
                case R.id.button_7: inputData(data); break;
                case R.id.button_8: inputData(data); break;
                case R.id.button_9: inputData(data); break;
                case R.id.button_plus: inputData(data); break;
                case R.id.button_minus: inputData(data); break;
                case R.id.button_multiply: inputData(data); break;
                case R.id.button_divide: inputData(data); break;
                    // 아래의 구문을 inputData 메소드로 대체하였음.
                    /*String data = ((Button) button).getText();
                    displayView.setText(displayView.getText().append(data));
                    break;*/

                    // 다른 방법
                    /*data = ((Button)button).getText().toString();  //data = ((TextView)button).getText().toString();   // TextView로 형변환해도 된다.
                    String temp = displayView.getText().append(data).toString();
                    displayView.setText(temp);
                    break;*/
                case R.id.button_result:
                    Toast.makeText(this, "계산이 되었습니다." + "\n\n입력한 수식은 "+displayView.getText()+" 입니다." , Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, ((Button) button).getText(), Toast.LENGTH_SHORT).show();  // 입력한 값을 보여준다.
                    break;
                case R.id.button_clean:
                    Toast.makeText(this, "초기화 되었습니다.", Toast.LENGTH_SHORT).show();
                    data = "";
                    displayView.setText(data);
                    break;
            }
        }

        public void inputData(String numb){
            String temp = displayView.getText().append(numb).toString();
            displayView.setText(temp);
        }
}







