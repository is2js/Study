package com.mdy.android.properties;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    Properties properties;  // 안쓴다.
//    Preference preference;  // 안쓴다.
    SharedPreferences sharedPreferences;   // 안드로이드 SharedPreference

    // 위젯 연결
    EditText editName, editEmail, editPassword;
    Button btnSave;
    TextView txtTest1, txtTest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        properties = new Properties();
//        preference = new Preference(this);  // 다른 액티비티가 내 Preference를 사용하지 못하게 한다. 지금은 사장됐다.


        // 파일의 제일 상단에서 실행을 시키고, 아래서는 변수값만 가져다 쓴다.
        // xml은 텍스트 파일이다. 별다른 확장자가 없다. 그래서 매직넘버가 없다. (텍스트파일, html파일, xml파일 모두)
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        //  MODE_PRIVATE - 다른 앱이 접근하지 못하게
        // xml 형태로 저장된다.

        editName = (EditText) findViewById(R.id.editName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);

        txtTest1 = (TextView) findViewById(R.id.txtTest1);
        txtTest2 = (TextView) findViewById(R.id.txtTest2);


        // 데이터 불러오기
        loadPreference();

        String firstData = sharedPreferences.getString("hi", "해당 데이터 없음");
        String secondData = sharedPreferences.getString("bye", "해당 데이터 없음");


        txtTest1.setText(firstData);
        txtTest2.setText(secondData);

        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePref("name", editName.getText().toString());
                savePref("email", editEmail.getText().toString());
                savePref("password", editPassword.getText().toString());
            }
        });
    }




    // read에만 트랜잭션이 없다. (변경되지 않기 떄문에)
    // 그래서 reade만 editor를 사용하지(꺼내지) 않는다.

    // 읽기에는 transaction이란 개념이 적용되지 않는다.
    // 저장해둔 설정값 가져오기
    private void loadPreference(){
        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");

        editName.setText(name);
        editEmail.setText(email);
        editPassword.setText(password);
    }



    // Preference 저장
    public void savePref(String key, String value){
        // 1. editor 꺼내기 (sharedPreference는 editor를 통해서 사용할 수 있다.)
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 2. editor를 통해서 키 값을 저장
        // 타입별로 저장할 수 있다. (ex.  editor.putInt(); )
        editor.putString("hi", "인사잘하네");
        editor.putString("bye", "잘가라~");
        editor.putString(key, value);

        // transaction을 위해서 커밋을 이용하는 것이다.
        // 중간에 오류가 나면 복원을 시켜주기 위해서
        // 3. editor 커밋
        editor.commit();
    }

    // 삭제하기
    private void removePreferences(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    // 전체 삭제하기
    private void removeAllPreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


}
