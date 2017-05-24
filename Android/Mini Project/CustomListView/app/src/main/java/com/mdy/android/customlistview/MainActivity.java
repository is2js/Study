package com.mdy.android.customlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 데이터
        ArrayList<Data> datas = Loader.getData();

        // 2. 아답터


    }
}

class CustomAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}



class Loader {
    public static ArrayList<Data> getData() {
        ArrayList<Data> result = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Data data = new Data();
            data.no = i + 1;
            data.title = "데이터" + i;
        }


        return result;
    }
}

class Data {
    public int no;
    public String title;
}
