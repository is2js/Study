package com.mdy.android.handmemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    FrameLayout layout;
    RadioGroup radioGroup;
    SeekBar seekBar;
    TextView seekCount;

    Paint paint = new Paint();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        layout = (FrameLayout) findViewById(R.id.layout);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

//        radioGroup.setOnCheckedChangeListener(this);

        seekCount = (TextView) findViewById(R.id.seekCount);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

//        seekBar.setOnSeekBarChangeListener(listener);


        // 1. 보드를 새로 생성한다.
        Board board = new Board(getBaseContext());


        radioGroup.setOnCheckedChangeListener(this);
        seekBar.setOnSeekBarChangeListener(listener);


        // 2. 붓을 만들어서 보드에 담는다.
//        Paint paint = new Paint();
//        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE); // 이 놈의 역할은??
//        paint.setStrokeWidth(10);
        board.setPaint(paint);

        // 3. 생성된 보드를 화면에 세팅한다.
        layout.addView(board);
    }



    SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekCount.setText(progress + "");
            paint.setStrokeWidth(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if(group.getId() == R.id.radioGroup) {
            switch (checkedId) {
                case R.id.radioGreen :
                    paint.setColor(Color.GREEN);
                    Toast.makeText(this, "초록색 붓이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radioBlue :
                    paint.setColor(Color.BLUE);
                    Toast.makeText(this, "파란색 붓이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radioRed :
                    paint.setColor(Color.RED);
                    Toast.makeText(this, "빨간색 붓이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    class Board extends View {  // 뷰를 만들었으면 onDraw() 메소드를 만들어준다.
        Paint paint;
        Path path;  // 내가 터치를 하면서 움직이면 터치한 포인트들의 점을 찍은 다음에 연결해준다.

        public Board(Context context) {
            super(context); // super를 들어가봐서 하는 것이 많이 있으면 주석처리하면 안된다.
            path = new Path();
        }

        public void setPaint(Paint paint) {
            this.paint = paint;
        }

        @Override
        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // 내가 터치한 좌표를 꺼낸다.
            float x = event.getX();
            float y = event.getY();

            Log.e("LOG", "onTouchEvent=" + x);

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN :
                    Log.e("LOG", "onTouchEvent==================down");
                    path = new Path();
                    path.moveTo(x, y);  // 이전점과 현재점 사이를 그리지 않고 이동한다.
                    break;
                case MotionEvent.ACTION_MOVE :
                    Log.e("LOG", "onTouchEvent==================move");
                    path.lineTo(x, y);  // 바로 이전점과 현재점 사이에 줄을 그어준다.
                    break;
                case MotionEvent.ACTION_POINTER_UP :
                    Toast.makeText(getContext(), "언제찍히니?", Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_UP :
                    path.lineTo(x, y);
                    break;
            }

            // 메인스레드여서 postinvalidate()가 아닌 그냥 invalidate()를 사용한다.
            // Path를 그린후 화면을 갱신해서 반영해 준다.
            invalidate();

//            return super.onTouchEvent(event);
            // 리턴 false 일 경우 touch 이벤트를 연속해서 발생시키지 않는다.
            // 즉, 드래그시 onTouchEvent가 호출되지 않는다.
            return true;
        }
    }
}
