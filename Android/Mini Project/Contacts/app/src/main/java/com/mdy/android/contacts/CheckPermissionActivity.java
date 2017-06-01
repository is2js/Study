package com.mdy.android.contacts;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class CheckPermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_permission);


        // 0. api level 이 23이상일 경우만 실행
        // 설치 안드로이드폰의 api level 가져오기  => Build.VERSION.SDK_INT
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // level 23은 마시멜로이다.  Build.VERSION_CODES.M : 마시멜로를 가리킨다.
            // 마시멜로부터는 앞에 대문자 한자만 써도 된다.
            checkPermission();
        } else {
            // 아니면 그냥 run() 메소드 실행
            run();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)       // @RequireApi 와 @TargetApi 는 동일하다고 생각해도 된다.
    private void checkPermission() {
    // 1. 권한체크 - 특정권한이 있는지 시스템에 물어본다.
    // checkSelfPermission 반환값이 true, false가 아니라 미리 정의된 상수로 반환한다.
        if( checkSelfPermission(Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
        run();
    } else {

        // 2. 권한이 없으면 사용자에게 권한을 달라고 요청
        String permissions[] = {Manifest.permission.READ_CONTACTS}; // 동시에 여러개 호출할 수 있으니까 복수로
        requestPermissions(permissions , REQ_PERMISSION);  // -> 권한을 요구하는 팝업이 사용자 화면에 노출된다.

        }
    }

    private final int REQ_PERMISSION = 100;

    // 3. 사용자가 권한체크 팝업에서 권한을 승인 또는 거절하면 아래 메서드가 호출된다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION) {
            // 3.1 사용자가 승인을 했음.
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                run();
            // 3.2 사용자가 거절 했음.
            } else {
                cancel();
            }
        }
    }

    public void run(){
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    public void cancel(){
        Toast.makeText(this, "권한을 요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
