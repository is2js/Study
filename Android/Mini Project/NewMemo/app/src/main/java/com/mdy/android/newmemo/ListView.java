package com.mdy.android.newmemo;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by MDY on 2017-05-29.
 */

// strings.xml 의 값 가져오기
// String value = getString(R.id.아이디);

public class ListView {
    ListActivity activity;

    Toolbar toolbar;
    FloatingActionButton fab;
    RecyclerView recyclerView;

    public ListView(ListActivity activity){
        this.activity = activity;
    }

    public void init(){
        activity.setContentView(R.layout.activity_list);

        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_list_activity);

        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);

        activity.setSupportActionBar(toolbar);
        // setSupportActionBar() 는 AppCompatActivity 클래스에 선언되어 있는 메소드이다.
        // 여기서 activity는 ListActivity인데 ListActivity가 AppCompatActivity 클래스를 상속하기 때문에 사용할 수 있다.

        fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.goDetail();
            }
        });
    }

    public void setRecyclerAdapter(RecyclerAdapter adapter) {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }
}