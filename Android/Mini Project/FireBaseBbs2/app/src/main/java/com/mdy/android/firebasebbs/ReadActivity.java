package com.mdy.android.firebasebbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mdy.android.firebasebbs.domain.Bbs;

public class ReadActivity extends AppCompatActivity {

    ImageView imageView;
    TextView txtTitle, txtAuthor, txtDate, txtCount, txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        setViews();

        setData();
    }

    public void setData(){
        // 목록에서 넘어온 position 값을 이용해 상세보기 데이터를 결정
        Intent intent = getIntent();
        // null값 확인
        int position = intent.getIntExtra("LIST_POSITION", -1);

        // 값이 있으면
        if(position > -1){
            Bbs bbs = Data.list.get(position);

            // 이미지 세팅
            if(bbs.fileUriString != null && !"".equals(bbs.fileUriString)){
                Glide.with(this).load(bbs.fileUriString).into(imageView);
            }


            // 값 세팅
            txtTitle.setText(bbs.title);
            txtAuthor.setText(bbs.author);
            txtDate.setText("Date : " + bbs.date);
            txtCount.setText("Count : " + bbs.count);
            txtContent.setText(bbs.content);
        }

    }




    private void setViews() {
        imageView = (ImageView) findViewById(R.id.imageView);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtCount = (TextView) findViewById(R.id.txtCount);
        txtContent = (TextView) findViewById(R.id.txtContent);
    }
}
