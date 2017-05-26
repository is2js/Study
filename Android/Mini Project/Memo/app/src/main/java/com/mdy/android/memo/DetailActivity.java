package com.mdy.android.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import static android.R.string.no;

public class DetailActivity extends AppCompatActivity {

    TextView txtNo, txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtNo = (TextView) findViewById(R.id.txtNo);
        txtTitle = (TextView) findViewById(R.id.editTxtTitle);

        // Activity에서 넘어온 값 처리하기
        // 1. intent 를 꺼낸다.
        Intent intent = getIntent();
        // 2. 값의 묶음인 bundle 을 꺼낸다.
        Bundle bundle = intent.getExtras(); // 데이터의 모음



//        txtTitle.setText(bundle.getString(ListActivity.DATA_TITLE));

        int position = -1;
        // 3. bundle이 있는지 유효성 검사를 한다.
        if(bundle != null) {
            // 3.1 bundle이 있으면 값을 꺼내서 변수에 담는다.
            position = bundle.getInt(ListActivity.DATA_KEY);
        }
        if(no > -1) {
            //txtNo.setText(bundle.getInt(ListActivity.DATA_NO)+"");
            txtTitle.setText(bundle.getString(ListActivity.DATA_TITLE));
        }



        // 뒤로가기 버튼
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });







        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Save", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, "Cancel", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
                // false로 해놓으면 LongClick을 했을때, Cancel 메세지가 나온후, 바로 Save 메세지까지 나온다. (onClick까지 이어서 실행된다.)
                // click과 longClick을 같이 사용할 때는 true로 해야 한다.
            }
        });
    }

}
