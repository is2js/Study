package com.mdy.android.newmemo;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by pc on 5/29/2017.
**/

public class DetailView {
    // Presenter
    DetailActivity activity;

    // 위젯 연결
    EditText memo;
    FloatingActionButton button;
    Toolbar toolbar;

    public DetailView(DetailActivity activity){
        this.activity = activity;
    }

    public void init(){
        activity.setContentView(R.layout.activity_detail);

        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        memo = (EditText) activity.findViewById(R.id.editText);

        button = (FloatingActionButton) activity.findViewById(R.id.fab);

        // 메모 저장
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = memo.getText().toString();
                if(content != null && !"".equals(content)){
                    activity.save(content);
                } else {
                    Toast.makeText(activity, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                activity.back();
                return true;
            }
        });
    }

    public void setMemo(String content) {
        memo.setText(content);
    }
}