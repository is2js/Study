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
    private Date data;

    @DatabaseField()
    private String name;



    public Memo() {
        // OrmLite 는 기본생성자가 없으면 동작하지 않습니다.
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }


}
