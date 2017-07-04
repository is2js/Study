package com.mdy.android.firebasebbs.domain;

/**
 * Created by MDY on 2017-07-03.
 */

public class Bbs {
    public String id;   // 파이어베이스의 push로 자동생성된다.
    public String title;
    public String author;
    public String content;
    public String date;
    public long count; // 조회수

    public Bbs(){

    }

    public Bbs(String title, String author, String content){
        this.title = title;
        this.author = author;
        this.content = content;
    }
}
