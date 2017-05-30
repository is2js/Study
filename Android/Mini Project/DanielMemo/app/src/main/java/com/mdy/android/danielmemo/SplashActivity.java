package com.mdy.android.danielmemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity
{
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loading();
    }

    private void loading()
    {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
                finish();
            }
        }, 6000);           // 3초 동안 Loading Activity 화면 보임.
    }
}