package com.mdy.android.musicplayer6;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mdy.android.musicplayer6.util.PermissionControl;

public class MainActivity extends AppCompatActivity implements ListFragment.OnListFragmentInteractionListener, DetailFragment.PlayerInterface, PermissionControl.CallBack {

    FrameLayout layout;
    ListFragment list;
    DetailFragment detail;

    Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = new Intent(this, PlayerService.class);


        // 볼륨 조절 버튼으로 미디어 음량만 조절하기 위한 설정
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        list = ListFragment.newInstance(1); // 1이면 1줄 , 2이면 2줄, 3이면 3줄
        detail = DetailFragment.newInstance();

        PermissionControl.checkVersion(this);

    }


    // requestPermissions() 메소드의 응답으로 호출
    // 3. 사용자가 권한체크 팝업에서 권한을 승인 또는 거절하면 아래 메서드가 호출된다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionControl.onResult(this, requestCode, grantResults);
    }

    @Override
    public void init(){
        setViews();
        setFragment(list); // 목록 프래그먼트

    }

    @Override
    public void cancel() {
        Toast.makeText(this, "권한을 요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }



    private void setViews(){
        layout = (FrameLayout) findViewById(R.id.layout);
    }

    private void setFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.layout, fragment);
        transaction.commit();
    }

    private void addFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }


    // Fragment를 통해 Adapter까지 interface를 전달하고
    // Adapter에서 interface를 직접 호출해서 사용한다.
    @Override
    public void goDetailInteraction(int position) {
        detail.setPosition(position);
        addFragment(detail);
    }

    @Override
    protected void onDestroy() {
        detail.setDestroy();
        super.onDestroy();
    }

    @Override
    public void initPlayer(){

    }
    @Override
    public void playPlayer(){
        // 1. 서비스를 생성하고
        // 서비스에 명령어를 담아서 넘긴다
        service.setAction(Const.Action.PLAY);
        startService(service);
    }
    @Override
    public void stopPlayer(){
        service.setAction(Const.Action.STOP);
        startService(service);
    }
    @Override
    public void pausePlayer(){
        service.setAction(Const.Action.PAUSE);
        startService(service);
    }
}