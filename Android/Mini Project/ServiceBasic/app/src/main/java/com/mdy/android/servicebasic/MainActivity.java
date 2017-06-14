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
                // bingdService를 호출하면 onCreate가 되어있으면 바로 onBind() 메소드를 호출한다.
                break;
            case R.id.btnUnbind :
                unbindService(connection);
                break;
        }
    }

    MyService service;
    // 액티비티가 서비스랑 통신을 해서 무엇인가를 주고 받고 싶을 때는 connection을 통해서 하라는 뜻
    // 그래서 bindService의 두번째 인자로 connection을 넣어줬다.
    // bindService()에 intent와 connection을 인자로 넘겨주면
    // 액티비티가 서비스와 연결되는 순간 onServiceConnected()이 호출된다.
    ServiceConnection connection = new ServiceConnection(){
        // 서비스와 연결되는 순간 호출
        // => onBind()가 호출되면서 리턴으로 Binder 객체를 onServiceConnected() 메소드에 넘겨준다.
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d("MainActivity", "onServiceConnected");

            // binder를 통해 서비스에 접근한다.
            MyService.MyBinder iBinder = (MyService.MyBinder) binder;
            service = iBinder.getService();   // iBinder를 통해서 서비스에 있는 기능을 꺼내서 쓸 수 있게 된다.
            service.print("연결되었습니다.");
        }

        /*  정리
        서비스와 액티비티간에 의존성이 전혀 없다.
        근데 액티비티에서 서비스를 띄웠다는 것은 서비스에서 무엇인가 결과처리된 값을 가져오거나 서비스의 값을 조회하기 위해 많이 사용한다.

        그래서 이 iBinder의 역할은 액티비티가 서비스에 의존성없이 값을 참조하기 위해서 iBinder라는 인터페이스를 사용하는 것이다.
        그럼 이제 무엇을 할 수 있느냐면

        서비스에 함수를 하나 만든다. [ex] print() 메소드
        얘는 서비스에 있는 것이다.
        이제 서비스의 함수를 통해서, 서비스의 리소스를 사용해서 무엇인가를 처리해줄 수 있게 된다.  */






        // 일반적인 상황에서는 호출되지 않는다. onDestory 에서는 호출되지 않는다.
        // 서비스가 도중에 끊기거나 연결이 중단되면 호출된다. (네트워크가 끊긴 것처럼)
        // 그래서 서비스를 2개 띄우면 안되니까 서비스가 살아있는지 체크하려고 onDestory에서 체크하려는 경우가 있는데
        // onServiceDisconnected()는 그런 용도가 아니기 때문에 onDestory에서 체크할 수 없다.
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MainActivity", "onServiceDisconnected");
        }
    };
}
