package com.mdy.android.handmemo3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {


    FrameLayout layout;
    RadioGroup color;

    Path current_path;
    SeekBar seekBar;
    TextView seekCount;

    Board board;

    Paint paint;

    ImageView imageView;               // 캡쳐한 이미지를 썸네일로 화면에 표시

    // 캡쳐한 이미지를 저장하는 변수
    Bitmap captured = null;

    int colorType = Color.BLACK;    // 초기값(색깔: 검정색)
    int thickType = 10; // 초기값(두께: 10)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        layout = (FrameLayout) findViewById(R.id.layout);
        color = (RadioGroup) findViewById(R.id.color);

        color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch(checkedId) {
                    case R.id.radioBlack :
                        setBrush(Color.BLACK, thickType);
                        colorType = Color.BLACK;
                        Toast.makeText(MainActivity.this , "검정색 붓이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioGreen :
                        colorType = Color.GREEN;
                        setBrush(colorType, thickType);
                        Toast.makeText(MainActivity.this , "초록색 붓이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioBlue :
                        setBrush(Color.BLUE, thickType);
                        colorType = Color.BLUE;
                        Toast.makeText(MainActivity.this , "파란색 붓이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioRed :
                        setBrush(Color.RED, thickType);
                        colorType = Color.RED;
                        Toast.makeText(MainActivity.this , "빨간색 붓이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        seekCount = (TextView) findViewById(R.id.seekCount);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        findViewById(R.id.btnNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.removeBrush();
                Toast.makeText(MainActivity.this , "초기화 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        // 썸네일 이미지뷰
        imageView = (ImageView) findViewById(R.id.imageView);
        // 캡쳐를 할 뷰의 캐쉬를 사용한다.

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 드로잉 캐쉬를 지워주고
                layout.destroyDrawingCache();
                // 다시 만들고
                layout.buildDrawingCache();
                // 레이아웃의 그려진 내용을 Bitmap 형태로 가져온다.
                captured = layout.getDrawingCache();
                // 캡쳐한 이미지를 썸네일에 보여준다.
                imageView.setImageBitmap(captured);

                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/DCIM/capture_"+System.currentTimeMillis()+".jpeg");
                    captured.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Captured!", Toast.LENGTH_LONG).show();


            }
        });



        // 1. 보드를 새로 생성한다.
        board = new Board(getBaseContext());

        // 2. 생성된 보드를 화면에 세팅한다.
        layout.addView(board);

        // 3. 기본 브러쉬 세팅
        setBrush(colorType, thickType);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekCount.setText(progress+"");
                setBrush(colorType , progress);
                thickType = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




    }





    private void setBrush(int color, int thick) {
        // 2. 붓을 만들어서 보드에 담는다.
        Paint paint = new Paint();
        paint.setColor(color);  // ColorType으로 넘겨받는 인자를 색깔로 설정
        paint.setStyle(Paint.Style.STROKE); // 선을 채우지 않고, 굵기를 지정
        paint.setAntiAlias(true);   // 디자인쪽에서 나오는 용어인데
        // 선을 만들어줄때 그 사이를 부드럽게 (경계점이 모호하게 만들어서)


        // SeekBar에서 처리
        paint.setStrokeJoin(Paint.Join.ROUND);  // 연결되었을때 연결점을 둥글게 연결
        paint.setStrokeCap(Paint.Cap.ROUND);  // 닫히는 부분
        paint.setDither(true); // 옆으로 깨지는 것을 보정, 선을 긋게 되면 선 사이에 튀어나오는 노이즈를 제거해준다.
        paint.setStrokeWidth(thick);
        board.setPaint(paint);
    }



    class Brush {
        Paint paint;
        Path path;
    }



    class Board extends View {  // 뷰를 만들었으면 onDraw() 메소드를 만들어준다.
        List<Brush> brushes = new ArrayList<>();
//        Paint paint;
        // Path path;  // 내가 터치를 하면서 움직이면 터치한 포인트들의 점을 찍은 다음에 연결해준다.


        public void removeBrush(){
            brushes.removeAll(brushes);
            invalidate();
        }
        public Board(Context context) {
            super(context); // super를 들어가봐서 하는 것이 많이 있으면 주석처리하면 안된다.
        }

        public void setPaint(Paint paintValue) {
            paint = paintValue;
        }

        @Override
        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
            for(Brush brush : brushes) {
                canvas.drawPath(brush.path, brush.paint);
            }
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
                    // 새로운 붓을 생성 - path 와 paint를 포함한다.
                    Brush brush = new Brush();
                    // 가. Path 생성
                    current_path = new Path();
                    // 나. 생성한 패스와 현재 페인트를 브러쉬에 담는다.
                    brush.path = current_path;
                    brush.paint = paint;
                    // 다. 완성된 브러쉬를 저장소에 담는다.
                    brushes.add(brush);

                    current_path.moveTo(x, y);  // 이전점과 현재점 사이를 그리지 않고 이동한다.

                    break;
                case MotionEvent.ACTION_MOVE :
                    Log.e("LOG", "onTouchEvent==================move");
                    current_path.lineTo(x, y);  // 바로 이전점과 현재점 사이에 줄을 그어준다.
                    break;
                case MotionEvent.ACTION_POINTER_UP :
                    Toast.makeText(getContext(), "언제찍히니?", Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_UP :
                    current_path.lineTo(x, y);
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