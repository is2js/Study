package com.mdy.android.danielmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.mdy.android.util.FileUtil;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    public static final String DOC_KEY_NAME = "document_id";
    FloatingActionButton btnSave;   // 버튼
    EditText editText;  // 입력 위젯

    String document_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // 호출한 activity에서 intent 에 값을 아무것도 넘기지 않으면 bundle이 null이 되기 때문에
        // null 에서는 getString 호출 시 Exception이 발생한다.
        // 따라서 bundle의 null 여부를 체크해준다.
        if(bundle != null) {
            document_id = bundle.getString(DOC_KEY_NAME);
        }

        editText = (EditText) findViewById(R.id.editText);


        // 가. 액티비티가 실행되면 파일의 내용을 불러와서 화면에 뿌려준다.
        loadContent();

        // 나. 버튼이 클릭되면 메모를 저장한다.
        btnSave = (FloatingActionButton) findViewById(R.id.fab);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeContent();
                finish();
            }
        });
    }

    private void writeContent() {
        // 1. 메모의 내용을 가져온다.
        String content = editText.getText().toString();

        // 2. 파일이름을 생성한다.
        String filename = "Memo"+System.currentTimeMillis()+".txt"; // System.nanoTime()으로 해도 된다.

        // document_id가 있으면 파일을 새로 생성하지 않고, 기존 이름을 사용해서 수정처리하다.
        if(!document_id.equals("")) {
            filename = document_id;
        }

        // 3. 메모를 파일에 저장한다.
        FileUtil.write(getBaseContext(), filename, content);

        // write 메소드가 onClick 안에 있을때는 DetailActivity.this로 썼는데
        // onClick 밖으로 메소드를 뺐으니까 이제는 this라고 써도 된다.

        // DetailActivity.this 대신에 getBaseContext()를 해줘도 된다.
        // getBaseContext() 대신에 view.getContext()를 사용해도 된다.

    }

    private void loadContent() {
        // document_id 값이 있을 때만 파일을 읽어와서 화면에 출력한다.
        if(!"".equals(document_id)) {
            String memo = FileUtil.read(this, document_id);
            editText.setText(memo);
        }
    }







}