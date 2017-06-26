package com.mdy.android.httpbbs3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DataLoader.CallBack{

    TextView txtId, txtTitle, txtContent, txtAuthor, txtDate;
    RecyclerView recycler;

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();

        // 1. 데이터 정의
        DataLoader loader = new DataLoader();
        loader.getData("http://192.168.10.79:8080/bbs/json/list", this);

        // 2. 아답터 생성
        adapter = new CustomAdapter();

        // 3. 연결
        recycler.setAdapter(adapter);

        // 4. 레이아웃 매니저 등록
        recycler.setLayoutManager(new LinearLayoutManager(this));

    }




    private void setView() {
        txtId = (TextView) findViewById(R.id.txtId);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        txtDate = (TextView) findViewById(R.id.txtDate);

        recycler = (RecyclerView) findViewById(R.id.recycler);
    }

    @Override
    public void setData(List<Bbs> list) {
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }
}
