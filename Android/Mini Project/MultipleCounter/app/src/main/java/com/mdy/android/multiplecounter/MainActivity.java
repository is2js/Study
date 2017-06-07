package com.mdy.android.multiplecounter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textViews[] = new TextView[4];

    public static final int SET_COUNT = 99;

    // 서브 thread로부터 메시지를 전달받을 Handler를 생성한다.... <메시지 통신>
    Handler handler = new Handler(){
        // 서브 thread에서 메시지를 전달하면 handleMessage 함수가 동작한다.
        @Override
        public void handleMessage(Message msg) {
            // handleMessage() -> 메세지를 컨트롤해주는 메소드
            // 내가 서브스레드에서 메세지를 핸들러로 날려주면 handleMessage 함수가 동작을 한다는 말이다.

            // super.handleMessage(msg);
            // super.handleMessage(msg); =>특별히 해주는게 없기 때문에 주석처리해줘도 된다.

            // 메세지 객체가 정해져있다.  msg
            // 핸들러를 통해서 일반적으로 자바에서 설계할때 핸들러를 통해서 메세지는 int로 주고 받는다.
            // msg.what에 메세지를 담아서 보내는데 what의 타입이 int이다.
            // 플래그값 상수 설계하듯이 핸들러로 메세지를 통신할때는 미리 상수로 다 정의해놓는다.
            // ex) public static final int SET_COUNT = 99;
            // 어떤 메세지를 핸들러를 통해 서브스레드에서 날려주면 switch를 통해서 msg.what을 꺼낸다.
            // what이 SET_COUNT 일 경우는, textViews[ 값1 ].setText( ""+값2 );
            // 이 handler는 Counter 인스턴스를 생성할 때(스레드가 new 될때), 인자로 handler를 넘겨줘야 한다.


            // 이렇게 서브스레드를 통해서 메인스레드로 핸들러에 메시지를 태워서 보내면 실행을 시킬 수 있다.
            switch (msg.what) {
                case SET_COUNT :
                    textViews[msg.arg1].setText(""+msg.arg2);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0 ; i<4; i++) {
            // 텍스트로 아이디 가져오기
            int resId = getResources().getIdentifier("textView"+(i+1), "id", getPackageName());
            textViews[i] = (TextView) findViewById(resId);
        }

        // 생성부
        ArrayList<Counter> counters = new ArrayList<>();
        for(int i=0; i<4; i++){
            counters.add(new Counter(i, handler));
        }

        // 실행부
        for(int i=0; i<counters.size(); i++){
            counters.get(i).start();
        }


        /* 생성부 2가지 방법 */
/*        Counter counter[] = new Counter[4];
        for(int i=0 ; i<4; i++) {
            counter[i] = new Counter(i, handler);
        }*/

/*        Counter counter1 = new Counter(0, handler); // 배열의 인덱스를 첫번째 인자로 넘겨준다.
        Counter counter2 = new Counter(1, handler);
        Counter counter3 = new Counter(2, handler);
        Counter counter4 = new Counter(3, handler);*/


        /* 실행부 2가지 방법 */
/*        for(int i=0; i<4; i++){
            counter[i].start();
        }*/

/*        counter1.start();
        counter2.start();
        counter3.start();
        counter4.start();*/

    }
}


// Counter라는 서브스레드는 핸들러를 받아서 일단 나한테 놔둔다. 나중에 쓰기 위해서(생성자에서)
class Counter extends Thread {
    Handler handler;
    int textViewIndex;
    int count = 0;

    public Counter(int index, Handler handler){
        Log.i("Counter", "handler======================"+handler+", index="+index);
        this.textViewIndex = index;
        // 텍스트뷰를 직접 받는 것이 아니라
        // 배열의 인덱스를 첫번째 인자로 넘겨준다.
        this.handler = handler;
    }

    @Override
    public void run(){

        for(int i=0; i<10; i++){

            // 서브 thread 에서 UI를 조작하기 위해 핸들러를 통해 메시지를 전달한다.
            count++;

            // 핸들러를 통해 메세지를 날릴때, Message객체를 생성해서 날릴 수 있다.
            Message msg = new Message();
            msg.what = MainActivity.SET_COUNT;
            msg.arg1 = textViewIndex;   // 텍스트뷰의 인덱스를 넘겨주게끔 설계했으므로
            msg.arg2 = count;   // 값을 직접 넘겨준다.
            handler.sendMessage(msg);
            // 핸들러를 통해 메세지 객체를 날려준다.
            // 그러면 handleMessage() 메소드가 호출된다.
            // 그러면 switch 문에서는 날라온 메세지에서 what을 꺼내서 SET_COUNT와 같으면 그에 해당하는 것을 실행해준다.
            // 그리고 메세지에 값을 2개까지 날려줄 수 있다. 메세지 객체가 그렇게 설계되어 있다.

            try {
                Thread.sleep(500);  // 0.5초 동안 쉬어준다.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
