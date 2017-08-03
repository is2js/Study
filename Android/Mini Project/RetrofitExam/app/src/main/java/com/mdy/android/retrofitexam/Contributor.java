package com.mdy.android.retrofitexam;

/**
 * Created by MDY on 2017-08-03.
 */

public class Contributor {

    public String login;
    public String html_url;

    public int contributions;

    @Override
    public String toString() {
        return login + "(" + contributions + ")";
    }
}
