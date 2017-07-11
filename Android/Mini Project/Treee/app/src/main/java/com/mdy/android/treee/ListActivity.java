package com.mdy.android.treee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mdy.android.treee.domain.Data;
import com.mdy.android.treee.domain.Memo;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    ImageView btnMinus, btnListIcon, btnProfile;
    ImageView imageViewTopTree, imageViewBottomTree, imageViewList;
    RecyclerView recyclerList;
    ListAdapter listAdapter;

    // 파이어베이스 데이터베이스
    FirebaseDatabase database;
    DatabaseReference memoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setViews();

        database = FirebaseDatabase.getInstance();
        memoRef = database.getReference("memo");

        loadListData();
    }

    public void loadListData(){
        memoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                Data.list.clear();
                for( DataSnapshot item : data.getChildren() ){
                    // json 데이터를 Bbs 인스턴스로 변환오류 발생 가능성 있어서 예외처리 필요
                    try {
                        Memo memo = item.getValue(Memo.class);
                        Data.list.add(memo);
                    } catch (Exception e){
                        Log.e("FireBase", e.getMessage());
                    }
                }
                refreshList(Data.list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void refreshList(List<Memo> data){
        listAdapter.setListData(data);
        listAdapter.notifyDataSetChanged();
    }

    public void setViews(){
        btnMinus = (ImageView) findViewById(R.id.btnMinus);
        btnListIcon = (ImageView) findViewById(R.id.btnListIcon);
        btnListIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, FeedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnProfile = (ImageView) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        imageViewTopTree = (ImageView) findViewById(R.id.imageViewTopTree);
        imageViewBottomTree = (ImageView) findViewById(R.id.imageViewBottomTree);
        imageViewList = (ImageView) findViewById(R.id.imageViewList);
        imageViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        listAdapter = new ListAdapter(this);
        recyclerList.setAdapter(listAdapter);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
    }
}