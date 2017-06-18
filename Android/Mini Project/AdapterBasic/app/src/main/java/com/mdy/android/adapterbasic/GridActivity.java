package com.mdy.android.adapterbasic;

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
import android.widget.TextView;

import java.util.ArrayList;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        // 1. 데이터
        ArrayList<Data> datas = Loader.getData();

        // 2. 아답터
        GridAdapter adapter = new GridAdapter(datas, this);

        // 3. 연결
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adapter);

    }
}

class GridAdapter extends BaseAdapter { // BaseAdapter는 Adapter의 기본이 되는 기능이 정의되어 있다.
    ArrayList<Data> datas;
    Context context;
    LayoutInflater inflater;

    public GridAdapter(ArrayList<Data> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //getSystemService 함수를 쓰기 위해서 LayoutInflater로 캐스팅해줬다.
        // getSystemService() 메소드를 사용해 LayoutInflater 객체를 참조하고 있다. (p257)
    }
    @Override
    public int getCount() { // 사용하는 데이터의 총 개수를 리턴 -> 리스트뷰의 길이를 추정할 수 있게 해준다. (스크롤의 길이를 알 수 있다.)
        return datas.size();
    }

    @Override
    public Object getItem(int position) { // 데이터 하나를 리턴
        Log.e("Adapter", "getItem position="+position);
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) { // 대부분 인덱스가 그대로 리턴된다.
        Log.e("Adapter", "getItem[Id] position="+position);
        return position;
    }


    // 아이템 뷰 하나를 리턴한다.
    // 내가 화면에 뿌려줄 것을 뿌려준다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // xml을 class로 변환한다.

        /*View view = inflater.inflate(R.layout.item_custom_list, null);
        TextView no = (TextView) view.findViewById(R.id.txtNo);
        TextView title = (TextView) view.findViewById(R.id.txtTitle);

        // 매줄에 해당되는 데이터를 꺼낸다
        Data data = datas.get(position);
        no.setText(data.getId()+"");
        title.setText(data.getTitle());

        return view;*/

        Log.d("ConvertView",position+" : convertView="+convertView);
        Holder holder;
        if(convertView == null) {
            holder = new Holder();  // holder 객체를 하나 생성
            convertView = inflater.inflate(R.layout.item_custom_grid, null);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);   // item_custom_list.xml 에 있는 id값을 가져온다.
            holder.title = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);  // 라벨링을 해주기 위해서 , 몇번째 행을 가져오기 위해서
        }else{
            holder = (Holder) convertView.getTag();    // getTag()는 타입이 Object이니까 Holder로 캐스팅을 해준다.
        }

        // 매줄에 해당되는 데이터를 꺼낸다.
        Data data = datas.get(position);

        // 이미지 세팅하기
        // 1. 이미지 suffix 만들기
        int image_suffix = (data.getId() % 5) + 1;
        // 2. 문자열로 리소스 아이디 가져오기
        int id = context.getResources().getIdentifier("baby" + image_suffix, "mipmap", context.getPackageName());

        // 3. 리소스 아이디를 이미지뷰에 세팅하기
        holder.image.setImageResource(id);
        holder.title.setText(data.getTitle());

        return convertView;
    }

    // Holder에는 내가 사용하는 item layout에 있는 위젯을 정의해둔다.
    class Holder {
        public ImageView image;
        public TextView title;
    }

}