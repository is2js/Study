package com.mdy.android.newmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mdy.android.newmemo.domain.Loader;
import com.mdy.android.newmemo.domain.Memo;

import java.util.ArrayList;

/*
  자주 사용되는 기능 알아보기
  1. Activity 갤러리 사용하기
    가. Basic Activity
      new > Activity > Gallery... 에서 선택
    나. floating 버튼 사용하기
      click 과 longClick 구분해서 적용하기
  2. 툴바 사용하기
    가. 툴바에 타이틀 넣기
      toolbar.setTitleTextColor(Color.parseColor("#ffff33")); //제목의 칼라
      toolbar.setNavigationIcon(R.mipmap.ic_launcher); //제목앞에 아이콘 넣기
      toolbar.setTitle( "타이틀" );
      toolbar.setSubtitle(R.string.subtitle); //서브타이틀
    나. 툴바에 메뉴를 달기위한 xml 세팅
      <?xml version="1.0" encoding="utf-8"?>
      <menu xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto" >
            <item android:id="@+id/action_bt1"
                android:icon="@drawable/download32"
                android:title="버튼1"
                app:showAsAction="always|withText" />
            <item android:id="@+id/action_bt2"
                android:title="버튼2"
                app:showAsAction="never" />
      </menu>
    다. 툴바 버튼 이벤트
        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            switch (item.getItemId()){
                case R.id.action_bt1:
                    Toast.makeText(this, "버튼1을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_bt2:
                    Toast.makeText(this, "버튼2을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
  3. 파일읽기 쓰기
    가. FileWriter
    나. FileReader
 */

public class ListActivity extends AppCompatActivity {
    public static final String DOCUMENT_ID = "document_id";
    ListView view;
    ArrayList<Memo> datas;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new ListView(this);
        view.init();

        // 데이터 정의 (데이터 가져오기)
        datas = Loader.getData(this);

        // 아답터 생성 (RecyclerView 세팅하기)
        adapter = new RecyclerAdapter(datas);   // adapter를 전역변수로 선언(RecyclerAdapter adapter)

        // 연결 (아답터 <=> 뷰)
        view.setRecyclerAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 데이터 가져오기
        datas = Loader.getData(this);
        adapter.notifyDataSetChanged();
    }

    // 입력창 이동
    public void goDetail() {
        Intent intent = new Intent(this, DetailActivity.class);
        //intent.putExtra(DOCUMENT_ID, "");
        startActivity(intent);
    }
}