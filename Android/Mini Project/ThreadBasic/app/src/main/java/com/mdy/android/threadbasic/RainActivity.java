package com.mdy.android.threadbasic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

public class RainActivity extends AppCompatActivity {

    FrameLayout ground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain);

        ground = (FrameLayout) findViewById(R.id.stage);


        // 커스텀 뷰를 생성하고
        Stage stage = new Stage(getBaseContext());

        // 레이아웃에 담아주면 화면에 커스텀뷰의 내용이 출력된다.
        ground.addView(stage);
    }


    class Stage extends View {

        Paint paint;

        public Stage(Context context) { // 화면을 그릴 때 시스템 자원을 사용하겠다는 말.(context를 쓰려고 하니까)
            super(context);
            paint = new Paint();
            paint.setColor(Color.BLUE);
        }

        // 화면에 로드되는 순간 호출되는 함수
        @Override
        protected void onDraw(Canvas canvas) {  // canvas 내가 그릴 수 있는 영역을 시스템이 넘겨준다.
            super.onDraw(canvas);

            canvas.drawCircle(100, 100, 30, paint);
            // x좌표(단위: 픽셀), y좌표, 반지름, 컬러or굵기 등
        }
    }
}
