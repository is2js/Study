package com.mdy.android.danielmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.mdy.android.danielmemo.domain.Loader;
import com.mdy.android.danielmemo.domain.Memo;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerAdapter adapter;
    Button btnDelete;

    boolean firstFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 1. 데이터
        ArrayList<Memo> datas = Loader.getData(this);  // this(context)를 넘겨줘야 데이터를 가져올 수 있다.

        // 2. 아답터
        adapter = new RecyclerAdapter(datas);
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



        // 버튼이 클릭되면 메모를 삭제한다.
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteContent();
            }
        });





    }


    // 디테일 액티비티를 열었다가 종료하면 호출될때
    // 데이터를 갱신하고, 목록을 다시 그려준다.
    @Override
    protected void onResume() {
        super.onResume();
        // 데이터를 갱신
        if(!firstFlag) {
            Loader.getData(this);
            // 아답터를 갱신
            adapter.notifyDataSetChanged();
        } else {
            firstFlag = false;
        }
    }



    private void deleteContent() {

    }




}
