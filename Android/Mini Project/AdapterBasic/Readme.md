### AdapterBasic
- Spinner
- ListView
- GridView
- CustomView
---
#### Adapter 클래스 만들기
GridAdapter, CustomAdapter를 만들때 BaseAdapter를 상속받는다.
BaseAdapter는 Adapter의 기본이 되는 기능이 정의되어 있다.
BaseAdapter를 상속받을 경우, 강제로 만들어야 하는 메소드가 4개가 있다.
1. int getCount() {}
> 사용하는 데이터의 총 개수를 리턴 -> 리스트뷰의 길이를 추정할 수 있게 해준다. (스크롤의 길이를 알 수 있다.)
2. Object getItem() {}
> 데이터 하나를 리턴
3. long getItemId() {}
> 대부분 인덱스가 그대로 리턴된다.
4. View getView() {}
> 아이템 뷰 하나를 리턴한다.
  내가 화면에 뿌려줄 것을 뿌려준다.

##### <예시 소스코드>
```java
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
```




### 소스코드
##### [1] Spinner
```java
public class MainActivity extends AppCompatActivity {

    // 1. 데이터 정의
    String datas[] = {"선택하세요", "ListView", "GridView", "RecyclerView"};

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        // ListView 에 데이터 연결하기
        // 1. 데이터 정의 - datas 정의하였음.
        // 2. 아답터 생성  - 뷰에 넣을 데이터를 조작해주는게 아답터이다.
        //    데이터의 변경 사항이 있어도 아답터가 처리를 해준다. (레이아웃 등)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        // 3. 뷰에 아답터 연결
        spinner.setAdapter(adapter);
    }
```

---
##### [2] ListView
- **1.ListActivity**
```java
package com.mdy.android.adapterbasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> datas = new ArrayList<>();

    // 다른 액티비티와 데이터를 주고 받을때 사용하는 키를 먼저 정의해둔다.
    public static final String DATA_KEY = "ListActivityData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listView);
        // 1. 데이터
        for(int i=0; i<100; i++){
            datas.add("데이터"+i);
        }
        // 2. 아답터
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, datas);

        // 3. 뷰 > 연결 < 아답터
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Activity 에 값 전달하기
                // 1. 전달받은 목적지 Intent 생성
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                // 2. putExtra로 값 입력
                String result = datas.get(position);
                intent.putExtra(DATA_KEY, result);
                // 3. intent 를 이용한 Activity 생성 요청
                startActivity(intent);
            }
        });
    }
}
```
- **2. DetailActivity**
```java
package com.mdy.android.adapterbasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = (TextView) findViewById(R.id.textView);

        // Activity 에서 넘어온 값 처리하기
        // 1. intent를 꺼낸다.
        Intent intent = getIntent();
        // 2. 값의 묶음인 bundle을 꺼낸다.
        Bundle bundle = intent.getExtras(); // 데이터의 모음
        String result = "";
        // 3. bundle 이 있는지 유효성 검사를 한다.
        if(bundle != null)
            // 3.1 bundle이 있으면 값을 꺼내서 변수에 담는다.
            result =  bundle.getString(ListActivity.DATA_KEY);

        textView.setText(result);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아래와 같이 해주면 Activity Stack에 계속 쌓이는 구조이기 때문에 이렇게 안쓴다.
                /*Intent intent = new Intent(DetailActivity.this, ListActivity.class);
                startActivity(intent);*/

                finish(); // 이렇게 해줘야 스택에 쌓이지 않고, 실행된다.
            }
        });
    }
}
```
##### [3] CustomActivity
```java
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

class CustomAdapter extends BaseAdapter { // BaseAdapter는 Adapter의 기본이 되는 기능이 정의되어 있다.
    ArrayList<Data> datas;
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(ArrayList<Data> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //getSystemService 함수를 쓰기 위해서 LayoutInflater로 캐스팅해줬다.
    }
    @Override
    public int getCount() { // 사용하는 데이터의 총 개수를 리턴 -> 리스트뷰의 길이를 추정할 수 있게 해준다.
        return datas.size();
    }

    @Override
    public Object getItem(int position) { // 데이터 클래스 하나를 리턴
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) { // 대부분 인덱스가 그대로 리턴된다.
        return position;
    }

    // 아이템 뷰 하나를 리턴한다.
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
```
- **3-1. item_custom_list.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/txtNo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="3dp"
        android:text="TextView" />

    <TextView
        android:id="@+id/txtTitle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="3dp"
        android:text="TextView" />
</LinearLayout>
```
