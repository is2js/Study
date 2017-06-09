package com.mdy.android.sqlliteorm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by MDY on 2017-06-09.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 2;


    // 최초 호출될때 database.db 파일을 data/data/패키지명/database/ 디렉토리 아래에 생성해준다.
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  // 3번째 인자(factory) - 커넥션 연결 단위를 만들어놓고, 사용할 수 있다.
    }

    // 최초에 생성되면 버전체크를 해서 onCreate를 호출한다.
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        // 여기서 테이블을 생성해야 한다.
        try {
            TableUtils.createTable(connectionSource, Memo.class);
            TableUtils.createTable(connectionSource, Bbs.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // DATABASE_VERSION이 업그레이드 되면 호출된다. (onCreate 대신에)
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            // Memo 테이블의 특정 컬럼만 변경되었을 경우
            // 1. 기존 테이블을 백업하기 위한 임시테이블을 생성하고 데이터를 담아둔다.
            //    예) create table temp_memo,    <- 기존데이터
            // 2. Memo 테이블을 삭제하고 다시 생성한다.
            // 3. 백업된 데이터를 다시 입력한다.
            // 4. 임시테이블을 삭제한다.
            TableUtils.createTable(connectionSource, Bbs.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    // Update
    public void update() {

    }

    // Delete
    public void delete() {

    }


}
