package com.mdy.android.danielmemo.domain;

/**
 * Created by MDY on 2017-05-31.
 */

public class Memo {
    private String id;
    private String content;
    private String date;
    private boolean checkBox = false;

    public boolean getCheck() {
        return checkBox;
    }

    public void setCheck(boolean check) {
        this.checkBox = check;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}