package com.mdy.android.sqlliteorm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by MDY on 2017-06-09.
 */



// 앞으로 DBHelper를 select, insert, update, delete를 할때 통해서 할 것이다.
// OrmLiteSqliteOpenHelper는 ORM기능이 없이 쿼리를 날리는 형태이다.
// (Object를 사용하는게 아니라 쿼리를 만들어서 질의를 직접 쿼리 형태로 던지는 것이다. 물론 함수들이 있긴 하지만)
// (ORMLite는 그 쿼리를 직접 내가 만드는 것이 아니라 Memo만들때 Memo객체에 저장하고, Memo객체를 리스트에 넣고, 혹은 Memo객체를 파일에 넣었다.
// 그것처럼 내가 객체에 데이터를 넣어놓고, ORMLite에 던져주면 ORMLite insert 쿼리를 만들어서 SQLite에 데이터를 넣어준다.
public class DBHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "database.db";   // 파일명
    public static final int DATABASE_VERSION = 2;               // 데이터베이스 버전

    private static DBHelper instance = null;

    // DBHelper 를 메모리에 하나만 있게 해서 효율을 높혀보세~~~~
    // Singleton 으로 구성해보세~
    // 일반코드에서 생성자를 호출하지 못하게 생성자를 private으로 하면 된다.
    // 그리고 getter, setter처럼 DBHelper를 get해주는 함수를 하나 만들어주면 된다.
    public static DBHelper getInstance(Context context){
        if(instance == null) {  // getInstance를 호출했는데 instance가 있으면 생성하지 않게끔 if문을 사용
            instance = new DBHelper(context);
            // DBHelper가 context를 인자로 받아야하니까 getInstance도 인자로 context를 넘겨주게 설계한다.
        }
        return instance;
    }
    /*
    이게 싱글톤패턴 이다.  (메모리에 하나만 떠 있을 수 있게 만들어주는 그 구조를 싱글톤이라고 한다.)
    싱글톤으로 만들어 놓으면 메모리 상에 하나만 띄워놓고 사용할 수 있고,
    중간에 메모리가 해제되면 instance가 null이 될테니까
    다시 new 를 해주게 된다.
     */


    // 그렇게 DBHelper 생성자를 설정해놓으면 이제 사용할때,
    // activity라면 this만 넘겨주면 데이터베이스에 접속해서 사용할 수 있는 환경을 만들어 준 것이다.
    // 최초 호출될때 (MainActivity에서 new DBHelper를 해줄때, 자동으로 database.db파일을 생성해준다.)
    // database.db 파일을 data/data/패키지명/database/ 디렉토리 아래에 생성해준다.
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // 3번째 인자(factory) - 커넥션 연결 단위를 미리 만들어놓고, 가져다쓰는 형태로 사용할 수 있다.
        // 그러나 그렇게 거의 사용하지 않고, 모바일에서는 사용할 일이 없다고 봐도 된다.
    }


    // 최초 new DBHelper를 통해 생성자가 호출되면 onCreate를 호출한다.
    // 최초에 생성되면 버전체크를 해서 onCreate를 호출한다.
    // onCreate가 하는 역할
    // 우리는 create 쿼리를 직접 날리는게 아니다. onCreate 함수내에서 테이블을 생성한다.
    // 테이블 생성을 아주 쉽게 도와주는 것이 ORM의 장점 중 하나이다.
    // 테이블을 만드는 방법은 지금까지 domain을 만들었듯이 Memo 클래스(도메인)을 만든다.
    // Memo라는 클래스를 DBHelper의 특정 API를 통해서 호출만 해주면
    // Memo 클래스에 정의되어 있는 속성들로 컬럼을 만든 다음에 테이블을 생성해준다.
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource)  {
        // 여기서 테이블을 생성해야 한다.
        try {
            TableUtils.createTable(connectionSource, Memo.class);
            // TableUtils => 테이블을 관리해주는 유틸이다.
//            TableUtils.createTable(connectionSource, Bbs.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // DBHelper를 선언하면 생성자를 통해 onCreate가 호출되고,
    // 그러면 onCreate 안에 있는 TableUtils.createTable(connectionSource, Memo.class);를 통해
    // 데이터베이스의 테이블과 컬럼이 생성된다.




    // 내가 앱을 출시를 하고, 데이터베이스가 업그레이드가 되었을때(컬럼이 추가 되었거나 테이블이 추가되었을때)
    // DATABASE_VERSION을 다음 숫자로 바꿔주면 onCreate가 아닌 onUpgrade가 호출된다.
    // (이 말은 그 전에 이미 한번 설치가 되어 있어야 한다는 말이다.)
    // DATABASE_VERSION이 업그레이드 되면 호출된다. (onCreate 대신에)
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
            // Memo 테이블의 특정 컬럼만 변경되었을 경우 ( ex. <추가될경우>  private String name; )
            // 1. 기존 테이블을 백업하기 위한 임시테이블을 생성하고 데이터를 담아둔다.
            //    예) create table temp_memo,    <- 기존데이터
            // 2. Memo 테이블을 삭제하고 다시 생성한다.
            // 3. 백업된 데이터를 다시 입력한다.
            // 4. 임시테이블을 삭제한다.

//            TableUtils.createTable(connectionSource, Bbs.class);
    }






    /*
    DBHelper를 통해서 데이터베이스를 Access하기 위해서
    DBHelper 클래스에 CRUD 함수 4개를 만든다.
     */



    // 데이터 하나의 단위는 메모 클래스 하나와 같다.
    // 입력단위는 메모클래스가 하나가 입력 단위가 된다.
    // Create - 데이터를 입력 (set메소드와 같다.)
    public void create(Memo memo) {
        try {
            // 1. 테이블에 연결
            Dao<Memo, Integer> dao = getDao(Memo.class);   // Data Access Object
            // 타입이 Dao 이다. 테이블에 연결하면 연결한 연결 객체를 Dao 타입으로 넘겨준다.
            // Dao 커넥션을 가져오면 Dao가 사용하는 객체를 제네릭으로 선언해주는데
            // 객체(Memo)랑 ID를 선언해줘야 한다.
            // ID는 primary key 인 Integer 이다.
            // 제네릭은 타입 제한을 해주는 것이다.

            // 2. 데이터를 입력
            dao.create(memo);
            // 데이터 하나의 세트 memo를 넘겨주면
            // 이렇게 해주면 insert쿼리를 작성할 필요가 없다.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read All (전체 데이터 읽어오기)
    public List<Memo> readAll() {
        List<Memo> datas = null;
        try {
            // 1. 테이블에 연결
            Dao<Memo, Integer> dao = getDao(Memo.class);   // Data Access Object
            // 2.1 데이터 전체 읽어오기
            datas = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }


    // Read One (한개만 읽어오기)
    public Memo read(int memoid) {
        // read는 id값이 인자로 넘어와야 한다.
        // ex) select * from memo where memoid = 1;
        Memo memo = null;
        try {
            // 1. 테이블에 연결
            Dao<Memo, Integer> dao = getDao(Memo.class);   // Data Access Object
            // 2.1 데이터 한개 읽어오기
            memo = dao.queryForId(memoid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memo;    // 리턴타입이 메모 객체 1개이다. 메모 단위로 넘겨준다.
    }


    // search
    public List<Memo> search(String word) {
        List<Memo> datas = null;
        try {
            // 1. 테이블에 연결
            Dao<Memo, Integer> dao = getDao(Memo.class);   // Data Access Object
            // 2.1 데이터 검색하기
            String query = "select * from memo where content like '%"+word+"%'";
            GenericRawResults<Memo> temps = dao.queryRaw(query, dao.getRawRowMapper());
            // queryRaw의 리턴타입이 GenericRawResults이다.
            // 쿼리를 이용해서 결과값을 뽑아오고,
            datas = temps.getResults();
            // 그 결과값을 리스트의 형태로 리턴을 해줄 수 있다.
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }


    // Update  (Update는 create와 유사하다.)
    public void update(Memo memo) {
        try {
            // 1. 테이블에 연결
            Dao<Memo, Integer> dao = getDao(Memo.class);   // Data Access Object
            // 2. 데이터를 입력
            dao.update(memo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Object
    public void delete(Memo memo){
        try {
            // 1. 테이블에 연결
            Dao<Memo,Integer> dao = getDao(Memo.class);
            // 2. 데이터를 삭제
            dao.delete(memo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Delete By Id (ID로 데이터를 삭제할 경우)
    public void delete(int id){
        try {
            // 1. 테이블에 연결
            Dao<Memo,Integer> dao = getDao(Memo.class);
            // 2. 데이터를 아이디로 삭제
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}