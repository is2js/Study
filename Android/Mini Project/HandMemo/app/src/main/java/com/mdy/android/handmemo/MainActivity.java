package com.mdy.android.handmemo;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 보드를 새로 생성한다.

    }

    class Board extends View {  // 뷰를 만들었으면 onDraw() 메소드를 만들어준다.

        public Board(Context context) {
            super(context); // super를 들어가봐서 하는 것이 많이 있으면 주석처리하면 안된다.
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return super.onTouchEvent(event);
        }
    }
}
