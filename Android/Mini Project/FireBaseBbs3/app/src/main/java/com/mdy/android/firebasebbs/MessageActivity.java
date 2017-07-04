package com.mdy.android.firebasebbs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // 토큰을 만들어주는 함수 - FirebaseInstanceId.getInstance().getToken();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Message", "Refreshed token:" + refreshedToken);
    }
}
