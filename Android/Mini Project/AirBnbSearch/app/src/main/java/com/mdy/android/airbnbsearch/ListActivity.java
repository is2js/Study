package com.mdy.android.airbnbsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private TextView txtReservationList;
    private Button btnBack;
    private RecyclerView recyclerView;
    private List<Reservation> data;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setViews();
        setListeners();

        data = Data.data;
        adapter = new RecyclerAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setViews(){
        txtReservationList = (TextView) findViewById(R.id.txtReservationList);
        btnBack = (Button) findViewById(R.id.btnBack);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    private void setListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}