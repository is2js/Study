package com.mdy.android.memo3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnTextMemo, btnHandMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnTextMemo = (Button) findViewById(R.id.btnTextMemo);
        btnHandMemo = (Button) findViewById(R.id.btnHandMemo);

        btnTextMemo.setOnClickListener(this);
        btnHandMemo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btnTextMemo :
                intent = new Intent(MenuActivity.this, TextMemoListActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHandMemo :
                intent = new Intent(MenuActivity.this, HandMemoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
