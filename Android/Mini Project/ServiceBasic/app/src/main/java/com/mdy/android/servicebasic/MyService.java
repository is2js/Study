package com.mdy.android.servicebasic;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    // 생성자
    // 이 중에 통틀어 생성자함수가 가장 먼저 호출된다.
    // 생성자가 호출되지 않으면 아래에 있는 생명주기 함수는 호출할 방법이 없다.
    public MyService() {
        Log.i("MyService", "Constructor");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyService", "onCreate");
    }


    // MainActivity에 있는 connection이랑 MyService랑 연결해주는 연결고리의 역할을 해준다.
    // 내가 생성한 서비스에 어떤 함수를 실행시켜서 결과값을 받는다거나 할 때,
    // 이 onBind()를 통해서 Binder객체를 만들어서 호출한 측으로 넘겨줄 수 있다.
    // 그래서 아래 Binder 객체를 만들었다.
    /* 정리 */
    // MainActivity에서 bindService()를 통해서 connection을 서비스로 넘겨주면
    // connection안으로 onServiceConnected()의 IBinder라는 놈이 넘어오게 된다.
    // 
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("MyService", "onBind");

        return new MyBinder();
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    // 이렇게 만들어진 Binder 객체의 주된 역할은 생성된 서비스를 넘겨주는 역할을 많이 한다.
    // Binder를 상속받아서 만든다.
    // 이 클래스 안에 함수들을 선언하면 이 함수들을 호출한 Activity에서 직접 호출할 수 있다.??? 어디서든???
    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }
    // Binder를 통해서 getService()를 하면 현재 생성된 서비스를 리턴해주겠다는 말이다.  ??? 현재 생성된 서비스 ???


    public void print(String string) {
        Log.i("MyService", string);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MyService", "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("MyService", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i("MyService", "onRebind");
    }

}
