package com.mdy.android.httpbbs7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener, DataSender.CallBack {

    EditText editTitle, editAuthor, editContent;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        setViews();
        setListeners();



    }

    public void setViews(){
        editTitle = (EditText) findViewById(R.id.editTitle);
        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editContent = (EditText) findViewById(R.id.editContent);
        btnPost = (Button) findViewById(R.id.btnPost);
    }

    public void setListeners(){
        btnPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String title = editTitle.getText().toString();
        String author = editAuthor.getText().toString();
        String content = editContent.getText().toString();

        Bbs bbs = new Bbs();
        bbs.title = title;
        bbs.author = author;
        bbs.content = content;

        Gson gson = new Gson();
        String jsonString = gson.toJson(bbs);

        Log.d("jsonString", "===== jsonString =====" + jsonString);
    }

    @Override
    public void call(boolean result) {
        Log.d("WriteActivity", "전송결과= " + result);
    }
}
