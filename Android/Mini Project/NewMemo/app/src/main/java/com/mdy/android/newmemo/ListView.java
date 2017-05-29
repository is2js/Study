package com.mdy.android.newmemo;

import android.support.design.widget.FloatingActionButton;
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
    }
}