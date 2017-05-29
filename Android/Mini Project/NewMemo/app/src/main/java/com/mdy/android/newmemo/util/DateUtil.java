package com.mdy.android.newmemo.util;

import java.text.SimpleDateFormat;

/**
 * Created by MDY on 2017-05-29.
 */

public class DateUtil {
    public static String convertLongToString(long date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        return sdf.format(date);
    }
}