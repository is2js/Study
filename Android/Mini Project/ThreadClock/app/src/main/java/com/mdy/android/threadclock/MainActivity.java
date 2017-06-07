package com.mdy.android.threadclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int deviceHeight;
    int deviceWidth;

    int center_x, center_y;

    int LINE = 0;

    double angle = 0;
    double end_x, end_y;

    CustomView stage;

    // while문에 플래그를 줘야 앱을 종료했을때 Thread가 종료된다.
    // 그렇지 않고, run() 안에 while(true)를 해주면 앱을 종료해도 2시간동안 동작한다.
    boolean runFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        // 화면 세로길이
        deviceHeight = metrics.heightPixels;
        // 화면 가로넓이
        deviceWidth = metrics.widthPixels;
        // 중심점 가로
        center_x = deviceWidth / 2;
        // 중심점 세로
        center_y = deviceHeight / 2;
        // 선의길이
        LINE = center_x - 30;

        stage = new CustomView(getBaseContext());
        setContentView(stage);  // 화면 전체사이즈로 뷰를 그려준다.

        // 화면을 그려주는 Thread를 동작시킨다.
        new DrawStage().start();

        SecondHand hand1 = new SecondHand(15, center_x, center_y, (LINE-120) , 1000, Color.BLUE);
        stage.addHand(hand1);

        SecondHand hand2 = new SecondHand(10, center_x, center_y, (LINE-50) , 10, Color.RED);
        stage.addHand(hand2);

        SecondHand hand3 = new SecondHand(5, center_x, center_y, LINE , 1, Color.GREEN);
        stage.addHand(hand3);
    }

    class SecondHand extends Thread {
        Paint paint = new Paint();

        float start_x;
        float start_y;

        double angle;
        double line;

        double end_x;
        double end_y;

        int interval = 0;

        public SecondHand(int stroke, int x, int y, int length, int interval, int color){
            paint.setColor(color);
            paint.setStrokeWidth(stroke);

            start_x = x;
            start_y = y;
            line = length;
            angle = 0;
            this.interval = interval; // 쉬는 시간
        }

        @Override
        public void run() {
            while(runFlag){
                angle = angle + 1;
                // 화면의 중앙부터 12시방향으로 직선을 긋는다
                double angle_temp = angle - 90;
                end_x = Math.cos(angle_temp * Math.PI / 180) * line + center_x; // x좌표 구하는 식
                end_y = Math.sin(angle_temp * Math.PI / 180) * line + center_y; // y좌표 구하는 식

                if(interval > 0){
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 뷰만 다시 그려주는 역할 (onDraw 를 호출)
    class DrawStage extends Thread {
        @Override
        public void run() {
//            super.run();
            while(runFlag){
                stage.postInvalidate(); // postInvalidate()는 onDraw()를 호출해준다.
            }
        }
    }

    // View를 상속받은 클래스(CustomView)를 만들면 기본적으로 onDraw() 메소드는 가지고 있어야 한다
    class CustomView extends View {
        List<SecondHand> hands = new ArrayList<>();

        public CustomView(Context context) {
            super(context);
        }

        public void addHand(SecondHand hand){
            hands.add(hand);
            hand.start();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //super.onDraw(canvas);
            if( hands.size() > 0 ) {
                for(SecondHand hand :hands) {
                    canvas.drawLine( (float) hand.start_x, (float) hand.start_y, (float) hand.end_x, (float) hand.end_y, hand.paint );
                    // drawLine() 메소드의 첫번째 인자는 x좌표 시작하는 점이고,
                    //                     두번째 인자는 y좌표 시작하는 점이고,
                    //                     세번째 인자는 x좌표 끝나는 점이고,
                    //                     네번째 인자는 y좌표 끝나는 점이다.
                    //                     다섯번째 인자는 Paint 객체 이다.
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        runFlag = false; // Thread 를 종료시켜준다.
    }

}