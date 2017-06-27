package com.mdy.android.httpbbs6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DataLoader.CallBack{

    TextView txtId, txtTitle, txtAuthor, txtContent, txtDate;
    RecyclerView recycler;
    Button btnPost;

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setListeners();

        // 1. 데이터 정의
        DataLoader loader = new DataLoader();
        String url = "http://192.168.10.79:8080/bbs/json/list";
        loader.getData(url, this);

        // 2. 아답터 생성
        adapter = new CustomAdapter();

        // 3. 연결
        recycler.setAdapter(adapter);

        // 4. 레이아웃 매니저 등록
        recycler.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void setData(List<Bbs> list) {
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    public void setViews(){
        txtId = (TextView) findViewById(R.id.txtId);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        txtDate = (TextView) findViewById(R.id.txtDate);

        btnPost = (Button) findViewById(R.id.btnPost);

        recycler = (RecyclerView) findViewById(R.id.recycler);
    }

    public void setListeners(){
        btnPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, WriteActivity.class);
        startActivity(intent);
    }
}
