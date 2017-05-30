package com.mdy.android.newmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mdy.android.newmemo.util.FileUtil;

public class DetailActivity extends AppCompatActivity {
    DetailView detailView;
    String document_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 뷰를 분리한다
        detailView = new DetailView(this);
        // 뷰를 초기화한다.
        detailView.init();

        Intent intent = getIntent();
        document_id = intent.getExtras().getString("document_id");

        if(!document_id.equals("")){
            String content = FileUtil.read(this, document_id);
            detailView.setMemo(content);
        }
    }

    // 저장
    public void save(String content) {
        // 저장후 상세보기 액티비티를 종료하여 목록으로 돌아간다.
        String fileName = "Memo_"+System.currentTimeMillis()+".txt";
        if(!document_id.equals("")){
            fileName = document_id;
        }
        FileUtil.write(this, fileName, content);

        Toast.makeText(this, "저장하였습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    // 뒤로가기 (취소)
    public void back(){
        // 저장하지 않고 목록으로 돌아간다.
        finish();
    }

    public String getDocument_id(){
        return document_id;
    }
}