package com.mdy.android.contacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mdy.android.contacts.domain.Data;
import com.mdy.android.contacts.domain.Loader;

import java.util.List;

public class ContactActivity extends AppCompatActivity {

    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Log.e("TAG", "=========== ContactActivity =========== onCreate 호출 ===========");

        // 1. 데이터 정의
        Loader loader = new Loader();

        List<Data> datas = loader.getContacts();


        // 우리가 정의한 메소드이기 때문에 getContents(); 만 호출해도 데이터를 만들어서 리턴까지 해준다.
        // List<Data> = getContacts();  이것을 생략하고 바로 향상된 for문 안에 넣었다.
        /*for(Data data : getContacts()){
            Log.i("Contacts", "이름="+data.getName() +", tel"+data.getTel());
        }*/


        recycler = (RecyclerView) findViewById(R.id.recyclerView);

        // 2. 아답터 생성
        RecyclerAdapter adapter = new RecyclerAdapter(datas);


        // 3. 연결
        recycler.setAdapter(adapter);

        // 4. 레이아웃 매니저 등록
        recycler.setLayoutManager(new LinearLayoutManager(this));

    }


}
