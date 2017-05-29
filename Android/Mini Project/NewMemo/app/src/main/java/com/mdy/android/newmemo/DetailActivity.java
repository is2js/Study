package com.mdy.android.newmemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    DetailView detailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 뷰를 분리한다
        detailView = new DetailView(this);
        // 뷰를 초기화한다.
        detailView.init();
    }

    // 저장
    public void save() {
        Toast.makeText(this, "저장하였습니다.", Toast.LENGTH_SHORT).show();
        // 저장후 상세보기 액티비티를 종료하여 목록으로 돌아간다.
        finish();
    }

    // 뒤로가기 (취소)
    public void back(){
        Toast.makeText(this, "취소하였습니다.", Toast.LENGTH_SHORT).show();
        // 저장하지 않고 목록으로 돌아간다.
        finish();
    }

    // 첨부
    public void attach(){

    }
}