package com.mdy.android.httpbbs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DataLoader.CallBack {

    RecyclerView recyclerView;
    TextView txtId, txtTitle, txtAuthor, txtContent;

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtId = (TextView) findViewById(R.id.txtId);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        txtContent = (TextView) findViewById(R.id.txtContent);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        // 1. 데이터 정의
//        Data data = convertJson(jsonString);


        DataLoader loader = new DataLoader();
        loader.getData("http://192.168.10.79:8080/bbs/json/list", this);




        // 2. 아답터 생성
        adapter = new CustomAdapter();

        // 3. 연결
        recyclerView.setAdapter(adapter);

        // 4. 레이아웃 매니저 등록
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }

    @Override
    public void setData(List<Bbs> list) {
        adapter.setList(list);
        adapter.notifyDataSetChanged();

    }




//
//    public Data convertJson(String jsonString){
//        Gson gson = new Gson();
//
//        return gson.fromJson(jsonString, Data.class);  // 스트링을 클래스로 변환해준다.
//    }

}


