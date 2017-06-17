package com.mdy.android.customlistview4;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        listView = (ListView) findViewById(R.id.listView);
        gridView = (GridView) findViewById(R.id.gridView);

        // 1. 데이터 생성
        ArrayList<Data> datas = Loader.getData();
        // 2. 아답터 생성
        CustomAdapter adapter = new CustomAdapter(datas, this);
        // 3. 연결
        gridView.setAdapter(adapter);


    }
}


class CustomAdapter extends BaseAdapter {

    ArrayList<Data> datas;
    LayoutInflater inflater;
    Context context;

    public CustomAdapter(ArrayList<Data> datas, Context context){
        this.datas = datas;
        this.context = context;
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
            convertView = inflater.inflate(R.layout.item_grid, null);

            holder = new Holder();
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);
            holder.imageTitle = (TextView) convertView.findViewById(R.id.imageTitle);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Data data = datas.get(position);

//        holder.setNo(data.no);
//        holder.setTitle(data.title);

        // 이미지 세팅하기
        // 1. 이미지 suffix 만들기
        int image_suffix = (data.getNo() % 5 ) + 1;
        // 2. 문자열로 리소스 아이디 가져오기
        Log.w("image_suffix", "========== image_suffix ==========" + image_suffix);
        Log.w("data.getNo()", "========== data.getNo() ==========" + data.getNo());
        int id = context.getResources().getIdentifier("baby" + image_suffix, "mipmap", context.getPackageName());


        // 3. 리소스 아이디를 이미지뷰에 세팅하기
        holder.image.setImageResource(id);
        holder.imageTitle.setText( data.getNo() + "번째 baby");




//            ((TextView) convertView.findViewById(R.id.txtNo)).setText(data.no+"");
//            ((TextView) convertView.findViewById(R.id.txtTitle)).setText(data.title);
        return convertView;
    }
}

class Holder {
    TextView no;
    TextView title;
    ImageView image;
    TextView imageTitle;

    public void setNo(int no){
        this.no.setText(no+"");
    }
    public void setTitle(String title){
        this.title.setText(title);
    }

//    public void setImage(int resId){
//        this.image.setImageResource();
//    }
//    public void setImageTitle(String ImageTitle){
//        this.imageTitle.setText(ImageTitle);
//    }
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
    public int resId;
    public String imageTitle;

    public void setResId(int resId){

    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}