package com.mdy.android.memo2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mdy.android.memo2.domain.Loader;
import com.mdy.android.memo2.domain.Memo;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 1. 데이터
        ArrayList<Memo> datas = Loader.getData(this);  // this(context)를 넘겨줘야 데이터를 가져올 수 있다.

        // 2. 아답터
        RecyclerAdapter adapter = new RecyclerAdapter(datas);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerVIew);
        recyclerView.setAdapter(adapter);
        // 3. 레이아웃 매니저
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        floatingActionButton = (FloatingActionButton) findViewById(R.id.btnNew);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }

}
