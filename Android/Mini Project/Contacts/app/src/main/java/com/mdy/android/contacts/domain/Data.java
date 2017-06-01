package com.mdy.android.contacts.domain;

/**
 * Created by MDY on 2017-06-01.
 */

// 주소록 데이터 클래스 - POJO (Pure Old Java Object)
public class Data {
    private int id;
    private String name;
    private String tel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
