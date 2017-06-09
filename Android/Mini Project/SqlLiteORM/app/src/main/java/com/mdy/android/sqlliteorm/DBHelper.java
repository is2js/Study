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
    // Singleton 으로 구성해보세.
    public static DBHelper getInstance(Context context){
        if(instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }


    // 그렇게 DBHelper 생성자를 설정해놓으면 이제 사용할때,
    // activity라면 this만 넘겨주면 데이터베이스에 접속해서 사용할 수 있는 환경을 만들어 준 것이다.
    // 최초 호출될때 (MainActivity에서 new DBHelper를 해줄때, 자동으로 database.db파일을 생성해준다.)
    // database.db 파일을 data/data/패키지명/database/ 디렉토리 아래에 생성해준다.
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // 3번째 인자(factory) - 커넥션 연결 단위를 미리 만들어놓고, 가져다쓰는 형태로 사용할 수 있다.
        // 그러나 그렇게 거의 사용하지 않고, 모바일에서는 사용할 일이 없다고 봐도 된다.
    }

    // 최초에 생성되면 버전체크를 해서 onCreate를 호출한다.
    // onCreate가 하는 역할
    // 우리는 create 쿼리를 직접 날리는게 아니다. onCreate 함수내에서
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        // 여기서 테이블을 생성해야 한다.
        try {
            TableUtils.createTable(connectionSource, Memo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // DATABASE_VERSION이 업그레이드 되면 호출된다. (onCreate 대신에)
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
            // Memo 테이블의 특정 컬럼만 변경되었을 경우
            // 1. 기존 테이블을 백업하기 위한 임시테이블을 생성하고 데이터를 담아둔다.
            //    예) create table temp_memo,    <- 기존데이터
            // 2. Memo 테이블을 삭제하고 다시 생성한다.
            // 3. 백업된 데이터를 다시 입력한다.
            // 4. 임시테이블을 삭제한다.
    }

    // Create - 데이터를 입력 (set과 같다.)
    public void create(Memo memo) { // 데이터 단위가 메모클래스가 된다.
        try {
            // 1. 테이블에 연결
            Dao<Memo, Integer> dao = getDao(Memo.class);   // Data Access Object
            // 2. 데이터를 입력
            dao.create(memo);   // 이렇게 해주면 insert쿼리를 작성할 필요가 없다.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read All
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

    // Read One
    public Memo read(int memoid) {
        Memo memo = null;
        try {
            // 1. 테이블에 연결
            Dao<Memo, Integer> dao = getDao(Memo.class);   // Data Access Object
            // 2.1 데이터 한개 읽어오기
            memo = dao.queryForId(memoid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memo;
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
            datas = temps.getResults();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }


    // Update
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
    // Delete By Id
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