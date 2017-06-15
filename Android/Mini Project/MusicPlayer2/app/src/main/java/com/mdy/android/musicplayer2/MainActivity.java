package com.mdy.android.musicplayer2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.mdy.android.musicplayer2.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements ListFragment.OnListFragmentInteractionListener, PermissionControl.CallBack {



    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionControl.checkVersion(this);


    }


    // 3. 사용자가 권한체크 팝업에서 권한을 승인 또는 거절하면 아래 메서드가 호출된다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionControl.onResult(this, requestCode, grantResults);
    }

    @Override
    public void init(){
        setViews();
        setFragment(ListFragment.newInstance(1));    // 목록 프래그컨트
        // 1이면 1줄 , 2이면 2줄, 3이면 3줄
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

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}