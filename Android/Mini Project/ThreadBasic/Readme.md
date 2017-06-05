# ThreadBasic
Thread에 대해서 알아본다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ThreadBasic/app/src/main/java/com/mdy/android/threadbasic/MainActivity.java)**

## Thread에 관한 설명
- OS는 여러개의 프로그램을 돌릴때 각 프로그램마다 각각 프로세스를 만들어 실행시킨다.
   어플리케이션 하나가 여러개의 프로세스를 가질 수 있다. 그게 스레드(Thread)이다.
<br>

- 브라우저는 기본적으로 탭 하나하나 스레드가 생성된다.
   네이버를 실행시키면 100개 정도의 스레드가 한번에 실행되서
   이미지도 가져오고, 네트워크도 갖다오고 등등 여러가지의 일을 한번에 처리한다.
   그리고 스레드는 로직이 종료되면 자기 생명주기를 다하고 사라진다.
<br>

- 프로세스는 OS 자체가 가지는 명령어 실행단위 이다.
스레드(Thread)는 그 명령어 실행단위가 곧 앱 하나당 하나의 프로세스가 생성되는 것이다.
**스레드(Thread)는 앱 하나에 여러개의 명령어 실행단위가 있는 것과 같다.**
<br>

- 앱 외부의 실행단위가 프로세스이고, 앱 내부의 실행단위가 스레드(Thread)이다.
<br>

- **<프로세스와 스레드(Thread)의 큰 차이>**
  - 프로세스는 메모리 공유가 안되는데
    스레드는 메모리 공유가 된다.
  - 이유는 안드로이드와 상관없이 OS 보안때문에 설계된 것이다.
<br>

* 비동기 = 스레드


## Thread 생성 및 실행
#### 여러가지 방법들 (4가지)
- 1번째 방법
```java
// 1.1 Thread 생성 (방법1)
Thread thread = new Thread(){
    @Override
    public void run() {
        Log.i("Thread Test", "Hello Thread!");
    }
};

// 1.2 Thread 실행
thread.start(); // run() 함수를 실행시켜준다.
```


- 2번째 방법
```java
// 2.1. Thread 생성 (방법2)       // Thread가 implement Runnable을 하고 있기 때문에(인터페이스 사용)
Runnable thread2 = new Runnable() {
    @Override
    public void run() {
        Log.i("Thread Test", "Hello Runnable!");
    }
};

// 2.2. Threade 실행
new Thread(thread2).start();
```




- 3번째 방법
```java
// 3.1 Thread 생성 (방법3)
class CustomThread extends Thread {
  @Override
  public void run() {
      Log.i("Thread Test","Hello Custom Thread!");
  }
}

// 3.2 Thread 실행 3
CustomThread thread3 = new CustomThread();
thread3.start();
```




- 4번째 방법 (Thread를 많이 사용할 경우 사용하는 방법)
```java
// 4.1 Runnable 구현 (방법4)
class CustomRunnable implements Runnable {
  @Override
  public void run() {
      Log.i("Thread Test","Hello Custom Runnable!");
  }
}

// 4.2 Runnable 실행
Thread thread4 = new Thread(new CustomRunnable());
thread4.start();
```

## 빗방울 떨어지게 하는 코드 (Thread를 이용)
- Thread를 이용해 빗방울이 떨어지는 것과 같은 화면을 구현한다.

```java
package com.mdy.android.threadbasic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainActivity extends AppCompatActivity {

    FrameLayout ground;
    Stage stage;

    int deviceWidth, deviceHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain);
        ground = (FrameLayout) findViewById(R.id.stage);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels; // 스마트폰의 가로사이즈를 가져온다.
        deviceHeight = metrics.heightPixels;    // 스마트폰의 세로사이즈를 가져온다.


        // 커스텀 뷰를 생성하고
        stage = new Stage(getBaseContext());
        // 레이아웃에 담아주면 화면에 커스텀뷰의 내용이 출력된다.
        ground.addView(stage);

        findViewById(R.id.btnRun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTask();
            }
        });
    }

    private void runTask(){
        // 빗방울을 1초마다 1개씩 랜덤하게 생성
        Rain rain = new Rain();
        rain.start();
        // 화면을 1초마다 한번씩 갱신
        DrawCanvas drawCanvas = new DrawCanvas();
        drawCanvas.start();
    }

    // 화면을 1초에 한번씩 그려주는 클래스 (onDraw 를 호출)
    class DrawCanvas extends Thread {
        @Override
        public void run(){
            while(true){
                try {
                    Thread.sleep(10);   // 0.01초마다 한번씩 그려준다.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stage.postInvalidate();
            }
        }
    }

    // 빗방울을 만들어 주는 클래스
    class Rain extends Thread {
        @Override
        public void run() {
            // 특정 범위의 숫자를 랜덤하게 생성할 때 사용
            Random random = new Random();

            for(int j=0 ; j < 100 ; j++) {
                // 빗방울 하나를 생성해서 stage 에 더해준다.
                RainDrop rainDrop = new RainDrop();
                rainDrop.radius = random.nextInt(25)+5; // 5부터 ~29까지 크기로 생성

                rainDrop.x = random.nextInt(deviceWidth);  // 호출될 때마다 0부터 ~ 디바이스 가로사이즈 사이를 넣어준다.
                rainDrop.y = 0f;

                // 초당 이동하는 픽셀거리 (초당 3픽셀 이동)
                rainDrop.speed = random.nextInt(10) + 5; // 5부터 14까지를 랜덤으로 생성해서 넣어준다.

                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                rainDrop.paint = paint;

                // 생성한 빗방울을 stage 에 더해준다
                stage.addRainDrop(rainDrop);

                // 생성한 빗방울을 동작 시킨다.
                rainDrop.start();

                try {
                    Thread.sleep(100); // 0.1초에 1개씩 생성해준다.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 빗방울 클래스
    // 생성되는 순간 자신의 생명주기를 갖고 동작한다.
    class RainDrop extends Thread {
        Paint paint; // 색깔

        float radius;  // 크기
        float x; // x 좌표
        float y; // y 좌표

        int speed; // 속도

        boolean run = true;

        @Override
        public void run() {
            int count = 0;
            while(y < deviceHeight) {
                count++;
                y = count * speed;
                Log.i("RainDrop","y=============="+y);
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            run = false;
        }
    }

    // 화면을 그릴 수 있는 클래스
    class Stage extends View {
        Paint paint;
        List<RainDrop> rainDrops = new ArrayList<>();
        public Stage(Context context) { // 화면을 그릴 때 시스템 자원을 사용하겠다는 말.(context를 쓰려고 하니까)
            super(context);
            paint = new Paint();
            paint.setColor(Color.BLUE);
        }

        // 화면에 로드되는 순간 호출되는 함수
        @Override
        protected void onDraw(Canvas canvas) {    // canvas 내가 그릴 수 있는 영역을 시스템이 넘겨준다.
            super.onDraw(canvas);

            for(RainDrop drop : rainDrops) {
                canvas.drawCircle(drop.x, drop.y, drop.radius, drop.paint);
                // x좌표(단위: 픽셀), y좌표, 반지름, 컬러or굵기 등
            }
        }

        public void addRainDrop(RainDrop rainDrop){
            this.rainDrops.add(rainDrop);
        }
    }
}
```
