package com.mdy.android.musiclist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Music> datas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // 1. 데이터 생성
        datas = Database.read(this);
        // 2. 아답터 생성
        ListAdapter adapter = new ListAdapter(datas, this);
        // 3. 연결
        recyclerView.setAdapter(adapter);
        // 4. 레이아웃 매니저 등록
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
