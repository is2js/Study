package com.mdy.android.sqlliteorm;

/**
 * Created by MDY on 2017-06-09.
 */

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by pc on 6/9/2017.
 */


 /*
 BbsDao는 데이터베이스에서 엑세스해서 무엇인가를 가져오는 것인데
 기본적으로 CRUD 함수가 4개 포함된다.
 */
public class BbsDao {

    DBHelper helper;
    Dao<Bbs,Integer> dao;

    private BbsDao(Context context){
        // 데이터베이스 연결
        helper = DBHelper.getInstance(context);
        try {
            // 테이블 연결
            dao = helper.getDao(Bbs.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 생성자에서 (1) 데이터베이스 연결과 (2) 테이블 연결
    // 을 해줬기 때문에 CRUD 메소드에서 중복해서 하지 않아도 된다.
    // CRUD에서는 dao를 가져다 쓰기만 하면 된다.

    public void create(Bbs bbs){
        try {
            dao.create(bbs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Bbs> read(){
        List<Bbs> datas = null;
        try {
            datas = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }

    public void update(Bbs bbs){

    }

    public void delete(Bbs bbs){

    }
}