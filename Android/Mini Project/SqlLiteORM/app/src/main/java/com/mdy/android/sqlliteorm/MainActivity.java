package com.mdy.android.sqlliteorm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // DBHelper 생성자를 설정해놨기 때문에 이제 사용할때,
        // activity라면 생성자의 인자인 context에 this만 넘겨주면  데이터베이스에 접속해서 사용할 수 있는 환경을 만들어 준 것이다.
        DBHelper helper = new DBHelper(this);


        /*  Insert  */

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
        List<Memo> datas =  helper.readAll();
        for(Memo one : datas) {
            Log.i("Memo", one.getId()+" : title= "+one.getTitle()+", content= "+one.getContent());
        }

        // 앱을 삭제하면 internal storage가 다 삭제되기 때문에 저장된 값들이 삭제된다.







        // 4. 데이터 검색하기
        // 기본 데이터 넣기
//        helper.create(new Memo("제목1", "내용1"));    // 생성자를 오버로드 해놔서 간단하게 1줄로 만들수 있게 되었다.
//        helper.create(new Memo("제목2", "내용2"));
//        helper.create(new Memo("제목3", "내용3"));
//        helper.create(new Memo("제목4", "내용4"));

        // 검색하기
//        List<Memo> datas =  helper.search("내용3");
//        for(Memo one : datas) {
//            Log.i("Memo", one.getId()+" : title= "+one.getTitle()+", content= "+one.getContent());
//        }






        // 5. 수정하기
//        Memo memo = helper.read(3);   // 메모는 new로 생성되지 않고, 데이터베이스에 있는 내용을 읽어와서 수정된 내용만 반영하게 한다.
//        memo.setContent("내용");
//        helper.update(memo);





        // 6. 삭제하기
//        helper.delete(5);





        /*
        DBHelper를 직접 부르는 것이 아니라
        중간에 매개를 해주는 dao라는 인터페이스가 있는 것이다.
        그러면 테이블이 여러개가 되도 내 테이블에 엑세스하는 Object는 해당 Dao Object밖에 없다.
        그러면 DBHelper 클래스에는 CRUD 메소드가 필요가 없어지고
        기본적인 것만 있으면 되는데


        DBHelper를 new하는 부분을 살펴보면
        BbsDao는 여러군데에서 사용될 수 있을텐데.. BbsDao를 new 할때마다 데이터베이스가 new 된다.
        액티비티를 목록액티비티랑 상세액티비티를 나눠놓으면 BbsDao를 여기서도 new하고, 저기서도 new를 하면 2번 new하게 된다.
        그런 것을 방지하기 위해서 DBHelper계열들은 싱글톤 형태로 만들어진다.
        메모리에 하나만 존재하게 만드는 것이다.
        */

        // BbsDao의 접근제한자를 private으로 만들고 사용해봐라. (과제)
        BbsDao dao = new BbsDao(this);
        dao.create(new Bbs());


    }
}
