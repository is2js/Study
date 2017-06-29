package com.mdy.android.serverconnection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버터나이프 annotation 활성화
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btnPost, R.id.btnTest})
    public void goWrite(View view){

        switch (view.getId()){
            case R.id.btnPost :
                Intent intent = new Intent(this, WriteActivity.class);
                startActivity(intent);
                break;
            case R.id.btnTest :
                break;
        }

    }
}
