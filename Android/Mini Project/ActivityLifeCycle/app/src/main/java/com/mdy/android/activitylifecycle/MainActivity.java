package com.mdy.android.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button btnGeneral, btnTransparent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "================ onCreate");
        setContentView(R.layout.activity_main);
        // 1. 액티비티를 2개 생성한다.
        // 가. GeneralActivity , 나. TransparentActivity
        //
        // 2. 현재 액티비티에 버튼 2개를 생성한 후에
        // 위에서 만든 각 Activity를 호출하세요.

        btnGeneral = (Button) findViewById(R.id.btnGeneral);
        btnGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GeneralActivity.class);
                startActivity(intent);
            }
        });

        btnTransparent = (Button) findViewById(R.id.btnTransparent);
        btnTransparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransparentActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "================ onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "================ onResume");
    }

    // Running


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "================ onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "================ onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "================ onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "================ onDestroy");
    }





}
