package com.mdy.android.sqlliteorm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // DBHelper 생성자를 설정해놨기 때문에 이제 사용할때,
        // activity라면 생성자의 인자인 context에 this만 넘겨주면 데이터베이스에 접속해서 사용할 수 있는 환경을 만들어 준 것이다.
        DBHelper helper = new DBHelper(this);

        // 1. 데이터 입력
//        for(int i=0; i<10; i++) {
//            Memo memo = new Memo();
//            memo.setTitle("제목");
//            memo.setContent("내용");
//            helper.create(memo);
//        }
        // 이렇게 해주면 insert가 끝난 것이다.

        // 2. 데이터 한개 읽어오기
//        Memo one = helper.read(3);
//        Log.i("Memo", one.getId()+" : title= "+one.getTitle()+", content= "+one.getContent());


        // 3. 데이터 전체 읽어오기
//        List<Memo> datas =  helper.readAll();
//        for(Memo one : datas) {
//            Log.i("Memo", one.getId()+" : title= "+one.getTitle()+", content= "+one.getContent());
//        }


        // 4. 데이터 검색하기
        // 기본 데이터 넣기
//        helper.create(new Memo("제목1", "내용1"));
//        helper.create(new Memo("제목2", "내용2"));
//        helper.create(new Memo("제목3", "내용3"));
//        helper.create(new Memo("제목4", "내용4"));


        // 검색하기
//        List<Memo> datas =  helper.search("내용3");
//        for(Memo one : datas) {
//            Log.i("Memo", one.getId()+" : title= "+one.getTitle()+", content= "+one.getContent());
//        }


        // 5. 수정하기
//        Memo memo = helper.read(3);
//        memo.setContent("내용");
//        helper.update(memo);

        // 6. 삭제하기
        helper.delete(5);


    }
}
