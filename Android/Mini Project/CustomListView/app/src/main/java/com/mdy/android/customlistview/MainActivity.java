package com.mdy.android.customlistview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String DATA_KEY = "position";
    public static final String DATA_RES_ID = "resid";
    public static final String DATA_TITLE = "title";

    ArrayList<Data> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 데이터
        datas = Loader.getData(this);
        // 2. 아답터
        CustomAdapter adapter = new CustomAdapter(datas, this);
        // 3. 연결
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                Data data = datas.get(position);

                intent.putExtra(DATA_KEY, position);
                intent.putExtra(DATA_RES_ID, data.resId);
                intent.putExtra(DATA_TITLE, data.title);

                startActivity(intent);
            }
        });
    }
}

class CustomAdapter extends BaseAdapter {  // BaseAdapter를 상속받으면 기본적으로 구현해줘야 하는 것들이 꼭 있다. 메소드들이 정의되어 있다.
    // CustomAdapter에서 필요한 3가지
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
        // 1. 컨버터뷰를 체크해서 null 일 경우만 item layout을 생성해준다.
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
            holder = new Holder();
            holder.no = (TextView) convertView.findViewById(R.id.txtNo);
            holder.title = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);  // holder를 converView의 태그에 달아준다. (참조하게 해준다 / 연결해준다.)
        } else {
            holder = (Holder) convertView.getTag(); //   setTag로 할때 들어갈때 Object 타입으로 들어가기 때문에 Holder로 캐스팅을 해준다.
        }
        // 2. 내 아이템에 해당하는 데이터를 세팅해준다.
        Data data = datas.get(position);

        // 이렇게 해줘도 되는데, 캐스팅을 하게 되면 대부분 속도 저하가 일어난다.
//        ((TextView) convertView.findViewById(R.id.txtNo)).setText(data.no + "");
//        ((TextView) convertView.findViewById(R.id.txtTitle)).setText(data.title);

        holder.no.setText(data.no + "");
        holder.title.setText(data.title);

        // id를 가져오는 작업을 Loader에서 한다.
        // id = context.getResources().getIdentifier(image, "mipmap", context.getPackageName());
        holder.image.setImageResource(data.resId);

        return convertView;
    }

    class Holder {
        TextView no;
        TextView title;
        ImageView image;
    }
}


class Loader {
    public static ArrayList<Data> getData(Context context) {
        ArrayList<Data> result = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Data data = new Data();
            data.no = i;
            data.title = "아기사진";

            data.setImage("baby" + i, context);
            result.add(data);
        }
        return result;
    }
}

class Data {
    public int no;
    public String title;
    public String image;
    public int resId;

    public void setImage(String str, Context context) {
        image = str;
        // 문자열로 리소스 아이디 가져오기
        resId = context.getResources().getIdentifier(image, "mipmap", context.getPackageName());

    }
}
