# ThreadBasic
Thread에 대해서 알아본다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ThreadBasic/app/src/main/java/com/mdy/android/threadbasic/MainActivity.java)**

## Thread에 관한 설명

#### 프로세스와 스레드
- 메모리에 올라가서 실행되고 있는 것을 프로세스라고 한다.
- **운영체제에서는 실행 중인 하나의 애플리케이션을 프로세스(process)** 라고 부른다. 사용자가 애플리케이션을 실행하면 운영체제로부터 실행에 필요한 메모리를 할당받아 애플리케이션의 코드를 실행하는데 이것이 프로세스이다.
- 하나의 애플리케이션은 다중 프로세스를 만들기도 하는데, 예를 들어 Chrome 브라우저를 2개 실행했다면 2개의 Chrome 프로세스가 생성된 것이다.
- 멀티 태스킹(multi tasking)은 2가지 이상의 작업을 동시에 처리하는 것을 말하는데, 운영체제는 멀티 태스킹을 할 수 있도록 CPU 및 메모리 자원을 프로세스마다 적절히 할당해주고, 병렬로 실행시킨다.
- 예를 들어 워드로 문서 작업을 하면서 동시에 윈도우 미디어 플레이어로 음악을 들을 수 있다.
- 멀티 태스킹은 꼭 멀티 프로세스를 뜻하지는 않는다. 한 프로세스 내에서 멀티 태스킹을 할 수 있도록 만들어진 애플리케이션들도 있다. 대표적인 것이 미디어 플레이어와 메신저이다.
  - 미디어 플레이어는 동영상 재생과 음악 재생이라는 두 작업을 동시에 처리하고,
  - 메신저는 채팅 기능을 제공하면서 동시에 파일 전송 기능을 수행하기도 한다.
- 어떻게 하나의 프로세스가 2가지 이상의 작업을 처리할 수 있을까?
  - 그 비밀은 멀티 스레드(multi thread)에 있다.
- 스레드(thread)는 사전적 의미로 한 가닥의 실이라는 뜻인데, 한 가지 작업을 실행하기 위해 순차적으로 실행할 코드를 실처럼 이어 놓았다고 해서 유래된 이름이다. 하나의 스레드는 하나의 코드 실행 흐름이기 때문에 한 프로세스 내에 스레드가 2개라면 2개의 코드 실행 흐름이 생긴다는 의미이다. 멀티 프로세스가 애플리케이션 단위의 멀티 태스킹이라면 멀티 스레드는 애플리케이션 내부에서의 멀티 태스킹이라고 볼 수 있다.
- 멀티 프로세스들은 운영체제에서 할당받은 자신의 메모리를 가지고 실행하기 때문에 서로 독립적이다. 따라서 하나의 프로세스에서 오류가 발생해도 다른 프로세스에게 영향을 미치지 않는다.
- 하지만 멀티 스레드는 하나의 프로세스 내부에 생성되기 때문에 하나의 스레드가 예외를 발생시키면 프로세스 자체가 종료될 수 있어 다른 스레드에게 영향을 미치게 된다.
- 예를 들어 멀티 프로세스인 워드와 엑셀을 동시에 사용하던 도중, 워드에 오류가 생겨 먹통이 되더라도 엑셀은 여전히 사용 가능하다. 그러나 멀티 스레드로 동작하는 메신저의 경우 파일을 전송하는 스레드에서 예외가 발생되면 메신저 프로세스 자체가 종료되기 때문에 채팅 스레드도 같이 종료된다. 그렇기 때문에 멀티 스레드에서는 예외 처리에 만전을 기해야 한다.
- 멀티 스레드는 다양한 곳에서 사용된다.
  (1) 대용량 데이터의 처리 시간을 줄이기 위해 데이터를 분할해서 병렬로 처리하는 곳에서 사용
  (2) UI를 가지고 있는 애플리케이션에서 네트워크 통신을 하기 위해 사용
  (3) 다수 클라이언트의 요청을 처리하는 서버를 개발할 때에도 사용

#### 메인 스레드
- 모든 자바 애플리케이션은 메인 스레드(main thread)가 main() 메소드를 실행하면서 시작된다.
- 메인 스레드는 main() 메소드의 첫 코드부터 아래로 순차적으로 실행하고, main() 메소드의 마지막 코드를 실행하거나 return문을 만나면 실행이 종료된다.
- 메인 스레드는 필요에 따라 작업 스레드들을 만들어서 병렬로 코드를 실행할 수 있다. 즉 멀티 스레드를 생성해서 멀티 태스킹을 수행한다.
- 싱글 스레드 애플리케이션에서는 메인 스레드가 종료하면 프로세스도 종료된다. 하지만 멀티 스레드 애플리케이션에서는 실행 중인 스레드가 종료되더라도 작업 스레드가 계속 실행 중이라면 프로세스는 종료되지 않는다.

#### 스레드 우선순위
- 멀티 스레드는 동시성(Concurrency) 또는 병렬성(Parallelism)으로 실행된다.
  - 동시성은 멀티 작업을 위해 하나의 코어에서 멀티 스레드가 번갈아가며 실행하는 성질이다.
  - 병렬성은 멀티 작업을 위해 멀티 코어에서 개별 스레드를 동시에 실행하는 성질을 말한다.
- 스레드의 개수가 코어의 수보다 많을 경우, 스레드를 어떤 순서에 의해 동시성으로 실행할 것인가를 결정해야 하는데, 이것을 **스레드 스케줄링** 이라고 한다.
  - 스레드 스케줄링에 의해 스레드들은 아주 짧은 시간에 번갈아가면서 그들의 run() 메소드를 조금씩 실행한다.
- 자바의 스레드 스케줄링은 우선순위(Priority) 방식과 순환 할당(Round-Robin) 방식을 사용한다.
  - 우선순위 방식은 우선순위가 높은 스레드가 실행 상태를 더 많이 가지도록 스케줄링하는 것을 말한다.
  - 순환 할당 방식은 시간 할당량(Time Slice)을 정해서 하나의 스레드를 정해진 시간만큼 실행하고 다시 다른 스레드를 실행하는 방식을 말한다.
  - 스레드 우선순위 방식은 스레드 객체에 우선순위 번호를 부여할 수 있기 때문에 개발자가 코드로 제어할 수 있지만 순환 할당 방식은 자바 갓아 기계에 의해서 정해지기 때문에 코드로 제어할 수 없다.
    - 우선순위 방식에서 우선순위는 1 에서부터 10 까지 부여되는데, 1이 가장 낮고, 10이 가장 높다.
    - 우선순위를 부여하지 않으면 기본적으로 5의 우선순위를 할당받는다.
    ```java
    // Thread클래스가 제공하는 우선순위 변경 메소드
    thread.setPriority(우선순위);
    ```

#### 동기화 메소드 및 동기화 블록
- 스레드가 사용 중인 객체를 다른 스레드가 변경할 수 없도록 하려면 스레드 작업이 끝날 때까지 객체에 잠금을 걸어서 달느 스레드가 사용할 수 없도록 해야 한다.
- 멀티 스레드 프로그램에서 단 하나의 스레드만 실행할 수 있는 코드 영역을 임계 영역(critical section)이라고 한다.
- 자바는 임계 영역을 지정하기 위해 **동기화(synchronized) 메소드** 와 **동기화 블록** 을 제공한다.
- 스레드가 객체 내부의 동기화 메소드 또는 블록에 들어가면 즉시 객체에 잠금을 걸어 다른 스레드가 임계 영역 코드를 실행하지 못하도록 한다.
- 동기화 메소드 선언에 synchronized 키워드를 붙이면 된다.
- synchronized 키워드는 인스턴스와 정적 메소드 어디든 붙일 수 있다.
```java
public synchronized void method() {
  임계 영역;  // 단 하나의 스레드만 실행
}
```
- 동기화 메소드는 메소드 전체 내용이 임계 영역이므로 스레드가 동기화 메소드를 실행하는 즉시 객체에는 잠금이 일어나고, 스레드가 동기화 메소드를 실행 종료하면 잠금이 풀린다.

- 메소드 전체 내용이 아니라, 일부 내용만 임계 영역으로 만들고 싶다면 다음과 같이 동기화(synchronized) 블록을 만들면 된다.
```java
public void method() {
  // 여러 스레드가 실행 가능 영역

  ...

    // 여기부터 동기화 블록
    synchronized(공유객체) {  // 공유 객체가 객체 자신이면 this를 넣을 수 있다.
      임계 영역 // 단 하나의 스레드만 실행
    }
    // 여기까지 동기화 블록

  // 여러 스레드가 실행 가능 영역

  ...
}
```
- 동기화 블록의 외부 코드들은 여러 스레드가 동시에 실행할 수 있지만, 동기화 블록의 내부 코드는 임계 영역이므로 한 번에 한 스레드만 실행할 수 있고 다른 스레드는 실행할 수 없다.
- 만약 동기화 메소드와 동기화 블록이 여러개 있을 경우, 스레드가 이들 중 하나를 실행할 때 다른 스레드는 해당 메소드는 물론이고 다른 동기화 메소드 및 블록도 실행할 수 없다. 하지만 일반 메소드는 실행이 가능하다.


#### 스레드 상태
- 스레드 객체를 생성하고, start() 메소드를 호출하면 곧바로 스레드가 실행되는 것처럼 보이지만 사실은 실행대기상태가 된다.
- **실행대기상태** 란 **아직 스케줄링이 되지 않아서 실행을 기다리고 있는 상태** 를 말한다.
- 실행대기상태에 있는 스레드 중에서 스레드 스케줄링으로 선택된 스레드가 비로서 CPU를 점유하고 run() 메소드를 실행한다. 이 떄를 **실행(Running) 상태** 라고 한다.
- 실행 상태의 스레드는 run() 메소드를 모두 실행하기 전에 스레드 스케줄링에 의해 다시 실행대기상태로 돌아갈 수 있다. 그리고 실행대기상태에 있는 다른 스레드가 선택되어 실행대기상태가 된다.
 이렇게스레드는 실행대기상태와 실행상태를 번갈아가면서 자신의 run() 메소드를 조금씩 실행한다.
- 실행상태에서 run() 메소드가 종료되면, 더 이상 실행할 코드가 없기 때문에 스레드의 실행은 멈추게 된다. 이 상태를 **종료 상태** 라고 한다.
- 경우에 따라서는 실행상태에서 실행대기상태로 가지 않을 수도 있다. 실행 상태에서 일시정지상태로 가기도 하는데, **일시정지상태** 는 스레드가 실행할 수 없는 상태이다.
- 스레드가 다시 실행 상태로 가기 위해서는 일시정지상태에서 실행대기상태로 가야 한다.

#### 스레드 상태 제어
- 사용자는 미디어 플레이어에서 동영상을 보다가 일시 정지시킬 수도 있고, 종료시킬 수도 있다.
- 일시정지는 조금 후 다시 동영상을 보겠다는 의미이므로 미디어 플레이어는 동영상 스레드를 일시 정지 상태로 만들어야 한다. 그리고 종료는 더 이상 동영상을 보지 않겠다는 의미이므로 미디어 플레이어는 스레드를 종료 상태로 만들어야 한다.
- 이와 같이 **실행 중인 스레드의 상태를 변경하는 것** 을 **스레드 상태 제어** 라고 한다.   

#### 스레드 간 협업 ( wait(), notify(), notifyAll() )
- 경우에 따라서는 2개의 스레드를 교대로 번갈아가며 실행해야 할 경우가 있다. 정확한 교대 작업이 필요할 경우, 자신의 작업이 끝나면 상대방 스레드를 일시정지상태에서 풀어주고, 자신은 일시정지상태로 만드는 것이다. 이 방법의 핵심은 **공유 객체** 에 있다.
- 공유 객체는 두 스레드가 작업할 내용을 각각 동기화 메소드로 구분해 놓는다. 한 스레드가 작업을 완료하면 notify() 메소드를 호출해서 일시정지상태에 있는 다른 스레드를 실행대기상태로 만들고, 자신은 두 번 작업을 하지 않도록 wait() 메소드를 호출하여 일시정지상태로 만든다.
- 만약 waint() 대신 wait(long timeout)이나, wait(long timeout, int nanos)를 사용하면 notify()를 호출하지 않아도 지정된 시간이 지나면 스레드가 자동적으로 실행대기상태가 된다.
- notify() 메소드와 동일한 역할을 하는 notifyAll() 메소드로 있는데, nofity()는 wait()에 의해 일시 정지된 스레드 중 한 개를 실행대기상태로 만들고, notifyAll() 메소드는 wait()에 의해 일시 정지된 모든 스레드드을 실행대기상태로 만든다.
- 이 메소드들은 Thread 클래스가 아닌 Object클래스에 선언된 메소드이므로 모든 공유 객체에서 호출이 가능하다. 주의할 점은 이 메소드들은 동기화 메소드 또는 동기화 블록 내에서만 사용할 수 있다.

#### 데몬 스레드
- 데몬(daemon) 스레드는 주 스레드의 작업을 돕는 보조적인 역할을 수행하는 스레드이다.
- 주 스레드가 종료되면 데몬 스레드는 강제적으로 자동 종료되는데, 그 이유는 주 스레드의 보조 역할을 수행하므로 주 스레드가 종료되면 데몬 스레드의 존재 의미가 없어지기 때문이다. 이 점을 제외하면 데몬 스레드는 일반 스레드와 크게 차이가 없다.
- 데몬 스레드의 적용 예는 워드프로세서의 자동 저장, 미디어 플레이어의 동영상 및 음악 재생, 가비지 컬렉터 등이 있는데 이 기능들은 주 스레드(워드프로세스, 미디어 플레이어, JVM)가 종료되면 같이 종료된다.
- 스레드를 데몬으로 만들기 위해서는 주 스레드가 데몬이 될 스레드의 setDaemon(true)를 호출해주면 된다.
- 아래 코드를 보면 메인 스레드가 주 스레드가 되고, AutoSaveThread가 데몬 스레드가 된다.
```java
public static void main(String[] args) {
  AutoSaveThread thread = new AutoSaveThread();
  thread.setDaemon(true);
  thread.start();
  ...
}
```
- 현재 실행 중인 스레드가 데몬 스레드인지 아닌지를 구별하는 방법은 isDaemon() 메소드의 리턴값을 조사해보면 된다.

#### 스레드 그룹
- 스레드 그룹(ThreadGroup)은 관련된 스레드를 묶어서 관리할 목적으로 이용된다.
- JVM이 실행되면 system 스레드 그룹을 만들고, JVM 운영에 필요한 스레드들을 생성해서 system 스레드 그룹에 포함시킨다. 그리고 system의 하위 스레드 그룹으로 main을 만들고 메인 스레드를 main 스레드 그룹에 포함시킨다.
- 스레드는 반드시 하나의 스레드 그룹에 포함되는데, 명시적으로 스레드 그룹에 포함시키지 않으면 기본적으로 자신을 생성한 스레드와 같은 스레드 그룹에 속하게 된다.
- 우리가 생성하는 작업 스레드는 대부분 main 스레드가 생성하므로 기본적으로 main 스레드 그룹에 속하게 된다.

#### 스레드풀(ThreadPool)
- 병렬 작업 처리가 많아지면서 스레드 개수가 증가되고 그에 따른 스레드 생성과 스케줄링으로 인해 CPU가 바빠져 메모리 사용량이 늘어난다. 따라서 애플리케이션의 성능이 저하된다.
- 갑작스러운 병렬 작업의 폭증으로 인한 스레드의 폭증을 막으려면 **스레드풀(ThreadPool)** 을 사용해야 한다.
- **스레드풀은 작업 처리에 사용되는 스레드를 제한된 개수만큼 정해 놓고 작업 큐(Queue)에 들어오는 작업들을 하나씩 스레드가 맡아 처리한다.**
- 작업 처리가 끝난 스레드는 다시 작업 큐에서 새로운 작업을 가져와 처리한다. 그렇기 때문에 작업 처리 요청이 폭증되어도 스레드의 전체 개수가 늘어나지 않으므로 애플리케이션의 성능이 급격히 저하되지 않는다.
---
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
