package com.mdy.android.firebasebbs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mdy.android.firebasebbs.domain.Bbs;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    TextView txtTitle, txtAuthor, txtContent;
    EditText editTextTitle, editTextAuthor, editTextContent;
//    Button btnWrite;

    FirebaseDatabase database;
    DatabaseReference bbsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");

        setViews();
    }

    // onClick (데이터 전송)
    public void postData(View view){
        String title = editTextTitle.getText().toString();
        String author = editTextAuthor.getText().toString();
        String content = editTextContent.getText().toString();

        // 파이어베이스 데이터베이스에 데이터 넣기
        // 1. 데이터 객체 생성
        Bbs bbs = new Bbs(title, author, content);
        bbs.date = inputCurrentDate();

        // 2. 입력할 데이터의 키 생성
        String bbsKey = bbsRef.push().getKey(); // 자동생성된 키를 가져온다.

        // 3. 생성된 키를 레퍼런스로 데이터를 입력
        //   insert와 update, delete 는 동일하게 동작
        bbsRef.child(bbsKey).setValue(bbs);
        /* 수정 */
        // bbsRef.child(bbsKey).setValue(bbs)
        /* 삭제 */
        // bbsRef.child(bbsKey).setValue(null);

        finish();   // 데이터 입력 후 창 닫기
    }

    public String inputCurrentDate(){
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

        return sdf.format(currentTime);
    }

    public void setViews(){
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        txtContent = (TextView) findViewById(R.id.txtContent);

//        btnWrite = (Button) findViewById(R.id.btnWrite);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextAuthor = (EditText) findViewById(R.id.editTextAuthor);
        editTextContent = (EditText) findViewById(R.id.editTextContent);
    }
}
