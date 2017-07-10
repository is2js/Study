package com.mdy.android.treee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListActivity extends AppCompatActivity {

    ImageView btnMinus, btnListIcon, btnProfile;
    ImageView imageViewTopTree, imageViewBottomTree, imageViewList;
    RecyclerView recyclerList;

    // 파이어베이스 데이터베이스
    FirebaseDatabase database;
    DatabaseReference memoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setViews();
    }

    public void setViews(){
        btnMinus = (ImageView) findViewById(R.id.btnMinus);
        btnListIcon = (ImageView) findViewById(R.id.btnListIcon);
        btnProfile = (ImageView) findViewById(R.id.btnProfile);

        imageViewTopTree = (ImageView) findViewById(R.id.imageViewTopTree);
        imageViewBottomTree = (ImageView) findViewById(R.id.imageViewBottomTree);
        imageViewList = (ImageView) findViewById(R.id.imageViewList);

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
    }
}