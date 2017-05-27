package com.mdy.android.memo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static com.mdy.android.memo.R.id.txtNo;

public class ListActivity extends AppCompatActivity {

    RecyclerView listView;

    public static final String DATA_KEY = "position";
    public static final String DATA_NO = "no";
    public static final String DATA_TITLE = "title";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        listView = (RecyclerView) findViewById(R.id.recycleView);

        // 1. 데이터 정의
        ArrayList<Data> datas = Loader.getData(this);

        // 2. 아답터 생성
        CustomRecycler adapter = new CustomRecycler(datas, this);

        // 3. 연결 (뷰 와 아답터)
        listView.setAdapter(adapter);

        // 4. 레이아웃 매니저 등록
        listView.setLayoutManager(new LinearLayoutManager(this));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                startActivity(intent);



//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

}


class CustomRecycler extends RecyclerView.Adapter<CustomRecycler.Holder>{

    ArrayList<Data> datas;
    Context context;

    public CustomRecycler(ArrayList<Data> datas, Context context){
        this.datas = datas;
        this.context = context;

    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent , false);
        return new Holder(view, context);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 1. 데이터를 꺼내고
        Data data = datas.get(position);
        // 2. 데이터를 세팅
        holder.setNo(data.no);
        holder.setTitle(data.title);

    }

    // 데이터의 전체 개수
    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView no;
        TextView title;
        View view;
        Context context;

        public Holder(View itemView, final Context context) {
            super(itemView);
            no = (TextView) itemView.findViewById(txtNo);
            title = (TextView) itemView.findViewById(R.id.txtTitle);


            // view로 부터 Context 받아오기
/*            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);   // v.getContext()
                    intent.putExtra(ListActivity.DATA_TITLE, title.getText());
                    v.getContext().startActivity(intent);
                }
            });*/

    // 인자값으로 Context 받아오기
            // 생성자에 Context 인자를 넘겨주고,
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(ListActivity.DATA_TITLE, title.getText());

                    // TextView는 getText() 뒤에 toString()을 안해줘도 된다.
                    // EditText는 getText() 뒤에 toString()을 해줘야 한다. (타입이 Editable이라서)

                    context.startActivity(intent);
                }
            });


        }

        public void setNo(int no){
            this.no.setText(no+""); // 이름이 겹쳐서 this.을 앞에 써줬다.
        }

        public void setTitle(String title){
            this.title.setText(title);
        }

    }


}






class Loader {
    public static ArrayList<Data> getData(Context context) {
        ArrayList<Data> result = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Data data = new Data();
            data.no = i;
            data.title = i+"번째 메모";

            result.add(data);
        }
        return result;
    }
}

class Data {
    public int no;
    public String title;
}
