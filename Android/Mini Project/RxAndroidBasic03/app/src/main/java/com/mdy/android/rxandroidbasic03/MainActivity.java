package com.mdy.android.rxandroidbasic03;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button btnAsync;
    private RecyclerView recyclerView;

    // private ListView listView;

//    ArrayAdapter<String> adapter;
//    CustomAdapter adapter;
    RecyclerAdapter adapter;
    Observable<String> observable;
    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();
        // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);

        // adapter = new CustomAdapter(this, data);
        // listView.setAdapter(adapter);

        adapter = new RecyclerAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        // 캘린더에서 1월 ~ 12월까지 텍스트를 추출
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();


        observable = Observable.create( emitter  -> {
            for(String month : months){
                emitter.onNext(month);
                Thread.sleep(1000);
            }
            emitter.onComplete();
        });
    }


    private void setViews() {
        btnAsync = (Button) findViewById(R.id.btnAsync);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    private void setListeners(){
        btnAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observable.subscribeOn(Schedulers.io()) // 옵저버블의 Thread 를 지정 (데이터를 모으는 것)
                        .observeOn(AndroidSchedulers.mainThread())  // 옵저버의 Thread 를 지정  - 데이터를 화면에 뿌려주는 것 (UI는 메인쓰레드에서만 핸들링할 수 있으니까)
                        .subscribe(
                        str -> {
                            data.add(str);
                            // adapter.notifyItemChanged(포지션) : // 변경된 데이터만 갱신해준다.
                            // adapter.notifyItemInserted(data.dize()-1); // 추가된 데이터만 갱신해준다.
                            adapter.notifyDataSetChanged(); // 매번 데이터를 가져올 때마다 출력하기 위해 설정
                            },   // onNext
                        error -> Log.e("Error", error.getMessage()),    // onError
                        ( ) -> {    // onComplete
                            data.add("complete");
                            adapter.notifyDataSetChanged();
                        }
                );
            }
        });
    }
}

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    LayoutInflater inflater = null;
    List<String> data = null;

    public RecyclerAdapter(Context context, List<String> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Log.i("Refresh","~~~~~~~~~~~~~~~~~position="+position);
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}

class CustomAdapter extends BaseAdapter {

    List<String> data = null;
    LayoutInflater inflater;

    public CustomAdapter(Context context, List<String> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private int lastPosition = -1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(position > lastPosition){
            // 아래 동작..
        } else {
            return convertView;
        }

        if(convertView == null){
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }


        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(data.get(position));

        Log.i("Refresh", "~~~~~~~~~~~~ position = " + position);

        return convertView;
    }

}