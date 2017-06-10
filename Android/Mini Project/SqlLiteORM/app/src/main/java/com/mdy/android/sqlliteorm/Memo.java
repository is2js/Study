package com.mdy.android.sqlliteorm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by MDY on 2017-06-09.
 */

// 데이터베이스의 테이블로 사용할 것이라고 알려주는 annotation
@DatabaseTable(tableName =  "memo") // 이렇게 해주면 Memo 클래스를 'memo'라는 테이블로 생성해준다.
public class Memo {
    @DatabaseField(generatedId = true)  // 자동증가값으로 생성(generatedId = true)
    private int id;
    // 'memo'라는 테이블의 'id'라는 컬럼으로 생성이 된다.
    // @DatabaseField 라는 annotation이 붙은 애들만 테이블의 컬럼으로 사용된다.
    @DatabaseField
    private String title;   //
    @DatabaseField
    private String content;
    @DatabaseField
    private Date date;

    // 데이터베이스의 컬럼으로 사용하지 않으려면 annotation없이 선언하면 된다.
    // private String remark;



    // OrmLite 는 기본생성자가 없으면 동작하지 않습니다.
    public Memo() {
        setDate();
    }

    public Memo(String title, String content) {
        this.title = title;
        this.content = content;
        setDate();
    }


    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    private void setDate() {    // private으로 해주면 개발자 코드에서는 검색이 안되고, 생성자 호출을 통해서만 Date가 설정된다.
        Date date = new Date(System.currentTimeMillis());   // 생성자 안에 넣어주면 현재 시간값을 데이터 타입으로 바꿔준다.
        this.date = date;
    }


}
