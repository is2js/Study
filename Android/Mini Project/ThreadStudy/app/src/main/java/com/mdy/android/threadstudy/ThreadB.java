package com.mdy.android.threadstudy;

import android.util.Log;

/**
 * Created by MDY on 2017-06-05.
 */

public class ThreadB extends Thread {
    public void run() {
        for(int i=0; i<2; i++){
            Log.i("getName() + ", getName() + "가 출력한 내용");
        }
    }
}