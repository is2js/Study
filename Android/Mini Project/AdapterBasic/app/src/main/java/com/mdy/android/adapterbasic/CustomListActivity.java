package com.mdy.android.adapterbasic;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);

        listView = (ListView) findViewById(R.id.listView);

        // 1. 데이터
//        ArrayList<Data> datas = new ArrayList<>();
        ArrayList<Data> datas = Loader.getData();

        // 2. 아답터
        CustomAdapter adapter = new CustomAdapter(datas, this);

        // 3. 연결
        listView.setAdapter(adapter);

    }
}

// CustomAdapter역할 : Presenter(데이터 가져오고, 화면 뿌려주고, 컨트롤하고)
// M(데이터를 가져오고) -> C(가공을 하고) > V(화면에 뿌려준다)
class CustomAdapter extends BaseAdapter { // BaseAdapter는 Adapter의 기본이 되는 기능이 정의되어 있다.
    ArrayList<Data> datas;
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(ArrayList<Data> datas, Context context) {
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
    public Object getItem(int position) { // 데이터 클래스 하나를 리턴
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
            convertView = inflater.inflate(R.layout.item_custom_list, null);
            holder.no = (TextView) convertView.findViewById(R.id.txtNo);   // item_custom_list.xml 에 있는 id값을 가져온다.
            holder.title = (TextView) convertView.findViewById(R.id.txtTitle);
            convertView.setTag(holder);  // 라벨링을 해주기 위해서 , 몇번째 행을 가져오기 위해서
        }else{
            holder = (Holder) convertView.getTag();    // getTag()는 타입이 Object이니까 Holder로 캐스팅을 해준다.
        }

        // 매줄에 해당되는 데이터를 꺼낸다.
        Data data = datas.get(position);
        holder.no.setText(Integer.toString(data.getId()));
        holder.title.setText(data.getTitle());

        return convertView;
    }

    class Holder {
        public TextView no;
        public TextView title;
    }

}


class Loader {
    public static ArrayList<Data> getData(){
        ArrayList<Data> result = new ArrayList<>();
        for(int i=0; i<100; i++){
            Data data = new Data();
            data.setId(i+1);
            data.setTitle("Grid Title"+i);
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