package com.mdy.android.adapterbasic;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CustomListActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);

        // 1. 데이터
//        ArrayList<Data> datas = new ArrayList<>();
        ArrayList<Data> datas = Loader.getData();

        // 2. 아답터
        CustomAdapter adapter = new CustomAdapter(datas, this);

        // 3. 연결
        listView.setAdapter(adapter);

    }
}

class CustomAdapter extends BaseAdapter {
    ArrayList<Data> datas;
    Context context;

    public CustomAdapter(ArrayList<Data> datas, Context context) {
        this.datas = datas;
        this.context =context;
    }
    @Override
    public int getCount() { // 사용하는 데이터의 총 개수를 리턴 -> 리스트뷰의 길이를 추정할 수 있게 해준다.
        return 0;
    }

    @Override
    public Object getItem(int position) { // 데이터 클래스 하나를 리턴
        return null;
    }

    @Override
    public long getItemId(int position) { // 대부분 인덱스가 그대로 리턴된다.
        return 0;
    }

    // 아이템 뷰 하나를 리턴한다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    } // BaseAdapter는 Adapter의 기본이 되는 기능이 정의되어 있다.

}


class Loader {
    public static ArrayList<Data> getData(){
        ArrayList<Data> result = new ArrayList<>();
        for(int i=0; i<100; i++){
            Data data = new Data();
            data.setId(i+1);
            data.setTitle("타이틀"+i);
            result.add(data);
        }
        return result;
    }
}

class Data {
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}