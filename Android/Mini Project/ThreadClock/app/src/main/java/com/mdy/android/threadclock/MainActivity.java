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
        LINE = center_x - 50;

        stage = new CustomView(getBaseContext());
        setContentView(stage);

        // 화면을 그려주는 Thread를 동작시킨다.
        new DrawStage().start();

        SecondHand hand1 = new SecondHand(5, center_x, center_y, LINE, 1000/360);
        stage.addHand(hand1);

        SecondHand hand2 = new SecondHand(20, center_x, center_y, LINE, 1000);
        stage.addHand(hand2);
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

        public SecondHand(int stroke, int x, int y, int length, int interval){
            paint.setColor(Color.BLUE);
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
                end_x = Math.cos(angle_temp * Math.PI / 180) * LINE + center_x; // x좌표 구하는 식
                end_y = Math.sin(angle_temp * Math.PI / 180) * LINE + center_y; // y좌표 구하는 식

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

    // 뷰만 다시 그려주는 역할
    class DrawStage extends Thread {
        @Override
        public void run() {
//            super.run();
            while(runFlag){
                stage.postInvalidate();
            }
        }
    }

    // View클래스(CustomView)를 만들면 기본적으로 onDraw() 메소드는 가지고 있어야 한다
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
            super.onDraw(canvas);
            if( hands.size() > 0) {
                for(SecondHand hand :hands) {
                    canvas.drawLine(hand.start_x, hand.start_y, (float) hand.end_x, (float) hand.end_y, hand.paint);
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