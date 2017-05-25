package com.mdy.android.recycler1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (RecyclerView) findViewById(R.id.listView);

        // 1. 데이터 정의
        ArrayList<Data> datas = Loader.getData(this);

        // 2. 아답터 생성







    }
}


class CustomRecycler extends RecyclerView.Adapter<CustomRecycler.Holder>{

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder {
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