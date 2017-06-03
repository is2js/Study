package com.mdy.android.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListFragment list;
    DetailFragment detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragments();

        addList();
    }

    private void createFragments(){
        list = new ListFragment();
        list.setActivity(this); // 나를 던져준다.
        detail = new DetailFragment();
        detail.setActivity(this);
    }

    public void addList(){
        /*
         프래그먼트 화면에 넣기
        */
        // 1. 프래그먼트 트랜잭션 시작하기
        // 하위버전 호환성을 감안해서 android.support.v4로 FragmentTransaction을 만든다.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 2. 화면에 프래그먼트 넣기
        transaction.add(R.id.container, list);
        // 3. 트랜잭션 완료
        transaction.commit();
        // transation을 사용하면 스택을 사용할 수 있다.
    }

    public void addDetail(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, detail);
        // 트랜잭션 처리 전체를 stack에 담아놨다가, 안드로이드의 back 버튼으로 뒤로가기를 할 수 있다.
        transaction.addToBackStack(null); // null로 해주면 순서대로 빠져나온다.
        transaction.commit();
//        getSupportFragmentManager().popBackStack();


    }

    public void backToList(){
        // MainActivity가 AppCompatActivity을 상속받았기 때문에 onBackPressed()를 사용할 수 있다.
        // 뒤로가기 효과
        super.onBackPressed();
    }
}
