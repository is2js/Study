package com.mdy.android.soundplayer.util;

/**
 * Created by pc on 3/2/2017.
 */

public class TimeUtil {
    // 시간 포맷 변경 00:00
    public static String covertMiliToTime(long mili){
        long min = mili / 1000 / 60;
        long sec = mili / 1000 % 60;

        return String.format("%02d", min) + ":" + String.format("%02d", sec);
    }
}
