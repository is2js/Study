package com.mdy.android.listviewpractice1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    // 다른 액티비티와 데이터를 주고 받을때 사용하는 키를 먼저 정의해둔다.
    public static final String DATA_KEY = "ListActivityData";

    ListView listView;
    ArrayList<String> datas = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listView);

        // 1. 데이터 정의
        for(int i=0; i<100; i++){
            datas.add("데이터"+i);
        }

        // 2. 아답터
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);


                intent.putExtra(DATA_KEY, datas.get(position));

                startActivity(intent);
            }
        });


        // 3. 뷰 > 연결 < 아답터




                // Activity 에 값 전달하기
                // 1. 전달받은 목적지 Intent 생성

                // 2. putExtra로 값 입력


                // 3. intent 를 이용한 Activity 생성 요청


    }
}
