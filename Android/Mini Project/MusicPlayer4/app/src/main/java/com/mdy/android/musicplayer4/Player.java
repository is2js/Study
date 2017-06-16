package com.mdy.android.musicplayer4;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by MDY on 2017-06-16.
 */

public class Player {

    public static final int STOP = 0;
    public static final int PLAY = 1;
    public static final int PAUSE = 2;
    public static MediaPlayer player = null;
    public static int playerStatus = STOP;


    public static void play(Uri musicUri, Context context){
        // 1. 미디어 플레이어 사용하기

//        Uri musicUri = datas.get(position).musicUri;  // Uri를 직접 받으니까 없어져도 된다.
        if(player != null) {
            player.release();
        }
        player = MediaPlayer.create(context, musicUri);

        // 2. 설정
        player.setLooping(false);   // 반복여부

        // 3. 시작
        player.start();

        playerStatus = PLAY;
    }

    public static void pause(){
        player.pause();
        playerStatus = PAUSE;
    }

    public static void replay() {
        player.start();
    }

    // 음원의 길이를 리턴
    public static int getDuration() {
        if(player != null) {
            return player.getDuration();
        } else {
            // player가 아무것도 없으면 0을 반환
            return 0;
        }
    }


    // 매초마다 getCurrent() 메소드를 호출시켜서 현재포지션을 체크해서
    // seekBar를 이동시켜주면 된다.
    // 현재 음원의 실행 구간을 리턴해준다.
    public static int getCurrent() {
        if(player != null){
            return player.getCurrentPosition();
        } else {
            return 0;
        }
    }

}
