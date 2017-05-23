package com.mdy.android.calculator.util;

import android.util.Log;

import com.mdy.android.calculator.BuildConfig;

/**
 * Created by MDY on 2017-05-23.
 */

public class Logger {
    public static final boolean DEBUG = BuildConfig.DEBUG_MODE;

    public static void i(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.e(tag, msg);
        }
    }

}
