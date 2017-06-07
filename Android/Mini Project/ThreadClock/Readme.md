# ThreadClock
#### Thread를 이용하여 아날로그 시계를 만들어본다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ThreadClock/app/src/main/java/com/mdy/android/threadclock/MainActivity.java)**


## 화면의 가로, 세로 길이 구하는 방법
```java
    DisplayMetrics metrics = getResources().getDisplayMetrics();
    // 화면 세로길이
    deviceHeight = metrics.heightPixels;
    // 화면 가로넓이
    deviceWidth = metrics.widthPixels;
```




## onDraw() 와 postInvalidate
- View를 상속받아 CustomView 클래스를 만들면, onDraw() 메소드를 꼭 만들어줘야 한다.
  (그래야 화면을 그려주니까)
- **postInvalidate는 onDraw() 메소드를 계속 실행시켜준다.**
```java
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
```




## drawLine() 메소드
- drawLine() 메소드의
  - 첫번째 인자는 x좌표 시작하는 점이고,
  - 두번째 인자는 y좌표 시작하는 점이고,
  - 세번째 인자는 x좌표 끝나는 점이고,
  - 네번째 인자는 y좌표 끝나는 점이다.
  - 다섯번째 인자는 Paint 객체 이다.

```java
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
```





## Thread 안의 run() 메소드 안에 while문 사용시 Flag를 이용해야 한다.
- while문에 플래그를 줘야 앱을 종료했을때 Thread가 종료된다.
- 그렇지 않고, run() 안에 while(true)를 해주면 앱을 종료해도 계속(2시간동안) 동작한다.

```java
  boolean runFlag = true;
```

```java
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
```
- 액티비티의 생명주기를 이용해 액티비티가 onDestroy될 때, 플래그를 false로 바꿔준다.
```java
  @Override
  protected void onDestroy() {
      super.onDestroy();
      runFlag = false; // Thread 를 종료시켜준다.
  }
```
