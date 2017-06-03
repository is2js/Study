package com.mdy.android.mycalculate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

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
                    String result = calculate(displayView.getText().toString());
                    Toast.makeText(this, "입력한 수식은 "+ displayView.getText() +" 이고," + "\n\n계산한 결과는 " + result + " 입니다." , Toast.LENGTH_SHORT).show();
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


        public String calculate(String preview){
            String result = "";

            // 문자열을 쪼갠 후 우선순위에 따라 연산하시오.
            // 1. 문자열을 정규식으로 * / + - 을 이용해서 배열로 자른다
            String splitted[] = preview.split("(?<=[*/+-])|(?=[*/+-])");
            // 예) 123 * 45 + 67 / 89
            // 결과값 : splitted[0] = 123
            //          splitted[1] = *
            //          splitted[2] = 45
            //          splitted[3] = +
            //          splitted[4] = 67
            //          splitted[5] = /
            //          splitted[6] = 89

            // 배열을 중간에 삭제하기 위해서 컬렉션을 사용한다.
            ArrayList<String> saveList = new ArrayList<>();

            for(int i=0 ; i<splitted.length ; i++){
                saveList.add(splitted[i]);
            }

            // 이렇게 해도 된다. (향상된 for문)
                /*for(String temp : splitted){
                    saveList.add(temp);
                }*/


            //        int saveListSize = saveList.size(); // 컬렉션의 크기는 밖에서 선언해주는 것을 권장한다.


            // 반복문이 splitted 을 돌면서 * 와 / 만 먼저 연산해준다
            for(int i=0 ; i<saveList.size(); i++ ){
                String temp = saveList.get(i);
                int resultTemp = 0;
                if(temp.equals("*") || temp.equals("/")) {
                    int before = Integer.parseInt(saveList.get(i-1));
                    int after = Integer.parseInt(saveList.get(i+1));
                    if(temp.equals("*"))
                        resultTemp = before * after;
                    else
                        resultTemp = before / after;

                    //결과값 저장
                    //                saveList.set(i, Integer.toString(resultTemp));
                    saveList.set(i, resultTemp+"");
                    //필요없는 배열 뒤, 앞 2개 삭제
                    saveList.remove(i+1);
                    saveList.remove(i-1);
                    i--;
                }
            }

            // 반복문이 splitted 을 돌면서 + 와 - 만 먼저 연산해준다
            for(int i=0 ; i<saveList.size(); i++){
                String temp = saveList.get(i);
                int resultTemp = 0;
                if(temp.equals("+") || temp.equals("-")){
                    int before = Integer.parseInt(saveList.get(i-1));
                    int after = Integer.parseInt(saveList.get(i+1));
                    if(temp.equals("+"))
                        resultTemp = before + after;
                    else
                        resultTemp = before - after;

                    // 결과값 저장
                    //                saveList.set( i, Integer.toString(resultTemp) );
                    saveList.set(i, resultTemp+"");

                    // 필요없는 배열 뒤, 앞 2개 삭제
                    saveList.remove(i+1);
                    saveList.remove(i-1);
                    i--;
                }
            }
            return saveList.get(0);
        }
}







