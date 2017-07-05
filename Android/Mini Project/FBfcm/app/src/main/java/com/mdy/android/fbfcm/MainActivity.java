package com.mdy.android.fbfcm;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // 파이어베이스 데이터베이스 연결
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference uidRef = database.getReference("uid");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. UUID 생성 후 저장 객체에 담기
        String deviceUid = android.provider.Settings.Secure.getString(
                getContentResolver(), Settings.Secure.ANDROID_ID
        );

        Log.e("UUID---", deviceUid+"");

        Uid uid = new Uid();
        uid.deviceUid = deviceUid;
        uid.name = "손님";
        uid.token = "";

        // 2. 데이터베이스에 저장
        // 데이터베이스에 동일한 uuid 가 있으면 생략
        uidRef.child(deviceUid).setValue(uid);

//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.e("Token-------", refreshedToken+"");
    }
}

class Uid {
    String deviceUid;
    String name;
    String token;

    public Uid(){
        // 파이어베이스 데이터베잉스에서 사용하기 위해서는 public 생성자가 필요하다.
    }
}