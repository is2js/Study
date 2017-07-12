package com.mdy.android.treee.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by MDY on 2017-07-12.
 */

public class PreferenceUtil {
    private static SharedPreferences sharedPreferences = null;

    private static void setSharedPreferences(Context context){
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences("settings", MODE_PRIVATE); // MODE_PRIVATE - 다른 앱이 접근하지 못하게
    }

    private static void setString(Context context, String key, String value){
        setSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(Context context, String key){
        setSharedPreferences(context);
        return sharedPreferences.getString(key, "해당 데이터 없음");
    }

    public static void setUid(Context context, String value){
        setString(context,"userUid",value);
    }

    public static String getUid(Context context) {
        return getString(context, "userUid");
    }
}
