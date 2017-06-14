package com.mdy.android.servicebasic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStart, btnStop, btnBind, btnUnbind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnBind = (Button) findViewById(R.id.btnBind);
        btnUnbind = (Button) findViewById(R.id.btnUnbind);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnBind.setOnClickListener(this);
        btnUnbind.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        switch (v.getId()){
            case R.id.btnStart :
                startService(intent);
                break;
            case R.id.btnStop :
                stopService(intent);
                break;
            case R.id.btnBind :
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbind :
                break;
        }
    }


    // 액티비티가 서비스랑 통신을 할때는 connection을 통해서 하라는 뜻
    ServiceConnection connection = new ServiceConnection(){
        // 서비스와 연결되는 순간 호출
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d("MainActivity", "onServiceConnected");

            // binder를 통해 서비스에 접근한다.
            MyService.MyBinder iBinder = (MyService.MyBinder) binder;
            MyService service = iBinder.getService();
            service.print("연결되었습니다.");
        }

        // 일반적인 상황에서는 호출되지 않는다. onDestory 에서는 호출되지 않는다.
        // 서비스가 도중에 끊기거나 연결이 중단되면 호출된다.
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MainActivity", "onServiceDisconnected");
        }
    };
}
