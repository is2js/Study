package com.mdy.android.firebasebbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mdy.android.firebasebbs.util.PermissionControl;

public class AuthActivity extends AppCompatActivity implements PermissionControl.CallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        PermissionControl.checkVersion(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionControl.onResult(this, requestCode, grantResults);
    }

    @Override
    public void init() {
        // 초기화 처리
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // 스플래쉬 화면은 backstack에 남아 있으면 안되므로 종료
        finish();
    }

    @Override
    public void cancel() {
        Toast.makeText(this, "권한을 요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
