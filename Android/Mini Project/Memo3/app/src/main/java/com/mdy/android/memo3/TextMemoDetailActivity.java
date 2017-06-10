package com.mdy.android.memo3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mdy.android.memo3.util.FileUtil;

import static com.mdy.android.memo3.R.id.fab;

public class TextMemoDetailActivity extends AppCompatActivity {

    public static final String DATA_TITLE = "title";
    public static final String DATA_CONTENTS = "contents";


    FloatingActionButton btnSave;
    EditText editTextTitle;     // 제목 입력 위젯
    EditText editTextContent;   // 내용 입력 위젯


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_memo_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextTitle = (EditText) findViewById(R.id.detailTitle);
        editTextContent = (EditText) findViewById(R.id.detailContent);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            editTextTitle.setText(bundle.getString(TextMemoDetailActivity.DATA_TITLE));
            editTextContent.setText(bundle.getString(TextMemoDetailActivity.DATA_CONTENTS));
        }



        // 가. 액티비티가 실행되면 파일의 내용을 불러와서 화면에 뿌려준다.
        loadContent();





        btnSave = (FloatingActionButton) findViewById(fab);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeContent();
                finish();
                Toast.makeText(TextMemoDetailActivity.this , "저장하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }






    private void writeContent() {
        // 1. 메모의 제목과 내용을 가져온다.
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();

        // 2. 파일의 이름을 생성한다.
        String fileName = "Memo" + System.currentTimeMillis() + ".txt";

        // TODO 파일의
        if(!title.equals("")) {
            fileName = title;
        }


        // 3. 메모를 파일에 저장한다.
        FileUtil.write(getBaseContext(), fileName, title);
        FileUtil.write(getBaseContext(), fileName, content);

    }



    private void loadContent() {
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();

        // title 값이 있을 때만 파일을 읽어와서 화면에 출력한다.
        if(!"".equals(title)) {
            String temp_title = FileUtil.read(this, title);
            String temp_content = FileUtil.read(this, content);
            editTextTitle.setText(temp_title);
            editTextContent.setText(temp_content);
        }
    }


}
