package com.mdy.android.rxandroidbasic02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnJust;
    private Button btnFrom;
    private Button btnDefer;
    private TextView txtResult;

    // 목록
    private RecyclerView recycler;
    private List<String> data = new ArrayList<>();  // 빈 목록
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();
    }

    private void setViews() {
        btnJust = (Button) findViewById(R.id.btnJust);
        btnFrom = (Button) findViewById(R.id.btnFrom);
        btnDefer = (Button) findViewById(R.id.btnDefer);
        txtResult = (TextView) findViewById(R.id.txtResult);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        adapter = new CustomAdapter(data);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListeners(){
        btnJust.setOnClickListener(this);
        btnFrom.setOnClickListener(this);
        btnDefer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnFrom:
                break;
            case R.id.btnJust :
                break;
            case R.id.btnDefer:
                break;
        }
    }
}


class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{

    List<String> data;
    LayoutInflater inflater;

    public CustomAdapter(List<String> data){
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}