package com.mdy.android.serverconnection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteActivity extends AppCompatActivity {

    @BindView(R.id.editTitle)
    EditText editTitle;
    @BindView(R.id.editAuthor)
    EditText editAuthor;
    @BindView(R.id.editContent)
    EditText editContent;
    @BindView(R.id.btnPost)
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        // 버터나이프 annotation 활성화
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btnPost)
    public void onClick(){
        Toast.makeText(this, "불렀나요?", Toast.LENGTH_SHORT).show();
    }
}
