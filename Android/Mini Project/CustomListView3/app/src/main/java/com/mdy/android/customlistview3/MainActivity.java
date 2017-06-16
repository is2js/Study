package com.mdy.android.customlistview3;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView txtListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        txtListView = (TextView) findViewById(R.id.txtListView);

        // 1. 데이터 생성
        ArrayList<Data> datas = Loader.getData();
        // 2. 아답터 생성
        CustomAdapter adapter = new CustomAdapter(datas, this);
        // 3. 연결
        listView.setAdapter(adapter);



    }
}


class CustomAdapter extends BaseAdapter {

    // 1. 데이터
    ArrayList<Data> datas;
    // 2. 인플레이터
    LayoutInflater inflater;


    public CustomAdapter(ArrayList<Data> datas, Context context) {
        this.datas = datas;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);

            holder = new Holder();
            holder.no = (TextView) convertView.findViewById(R.id.txtNo);
            holder.title = (TextView) convertView.findViewById(R.id.txtTitle);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Data data = datas.get(position);
        holder.setNo(data.no);
        holder.setTitle(data.title);

//            ((TextView) convertView.findViewById(R.id.txtNo)).setText(data.no+"");
//            ((TextView) convertView.findViewById(R.id.txtTitle)).setText(data.title);

        return convertView;
    }

    class Holder {
        TextView no;
        TextView title;

        public void setNo(int no){
            this.no.setText(no+"");
        }
        public void setTitle(String title){
            this.title.setText(title);
        }
    }


}



class Loader {
    public static ArrayList<Data> getData(){
        ArrayList<Data> datas = new ArrayList<>();
        for(int i=0; i<100; i++){
            Data data = new Data();
            data.no = i+1;
            data.title = "데이터" + (i+1);
            datas.add(data);
        }
        return datas;
    }
}


class Data {
    public int no;
    public String title;
}