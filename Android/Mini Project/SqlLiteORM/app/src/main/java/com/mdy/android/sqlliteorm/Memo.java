package com.mdy.android.sqlliteorm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by MDY on 2017-06-09.
 */
@DatabaseTable(tableName =  "memo")
public class Memo {
    @DatabaseField(generatedId = true)  // 자동증가값으로 생성
    private int id;
    @DatabaseField()
    private String title;
    @DatabaseField()
    private String content;
    @DatabaseField()
    private Date date;

    @DatabaseField
    private String name;



    public Memo() {
        // OrmLite 는 기본생성자가 없으면 동작하지 않습니다.
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

    private void setDate() {
        Date date = new Date(System.currentTimeMillis());   // 생성자 안에 넣어주면 현재 시간값을 데이터 타입으로 바꿔준다.
        this.date = date;
    }


}
