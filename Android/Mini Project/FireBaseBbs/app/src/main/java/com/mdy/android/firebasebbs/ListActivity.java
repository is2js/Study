package com.mdy.android.firebasebbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mdy.android.firebasebbs.domain.Bbs;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView recycler;
    CustomAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference bbsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");

        setViews();

        loadData();
    }

    public void loadData(){
        bbsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                List<Bbs> list = new ArrayList<>();
                for( DataSnapshot item : data.getChildren() ){
                    // json 데이터를 Bbs 인스턴스로 변환오류 발생 가능성 있어서 예외처리 필요
                    try {
                        Bbs bbs = item.getValue(Bbs.class);
                        list.add(bbs);
                    } catch (Exception e){
                        Log.e("FireBase", e.getMessage());
                    }
                }
                refreshData(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void refreshData(List<Bbs> data){
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    // onClick
    public void postData(View view){
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }

    public void setViews(){
        recycler = (RecyclerView) findViewById(R.id.recycler);
        adapter = new CustomAdapter(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
