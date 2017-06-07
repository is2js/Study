# MultipleCounter
#### Handler객체 , handleMessage() 메소드, Message 객체에 대해서 알아본다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/MultipleCounter/app/src/main/java/com/mdy/android/multiplecounter/MainActivity.java)**


## 핸들러(Handler)
#### 안드로이드에서는 Sub Thread에서 메인에 있는 UI를 건들지 못하게 제한되어 있다.
#### 그래서 핸들러를 통해서 접근을 강제하는 것이다.
![Handler01](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/MultipleCounter/graphics/Handler01.JPG)
![Handler02](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/MultipleCounter/graphics/Handler02.JPG)
<br>

- 우리가 앱을 띄우게 되면 앱 안에서 Looper라는게 계속 돌고 있다.
루퍼는 메세지큐를 갖고 있다.
<br>

- Sub Thread를 생성해서
Sub Thread에서 핸들러를 통해서 sendMessage()로 메세지를 전달하면
Main Thread는 메세지를 메세지큐에 담는다.
그러면 루퍼가 돌다 메세지 큐에 메시지가 들어오면 핸들러에 있는 handleMessage()를 실행시켜준다.
<br>

- 마치 서버처럼 이벤트 리스너처럼 메세지를 받기 위해서 Thread가 돌고 있는 것이다.
계속 돌고 있다 메세지가 큐에 들어오면 꺼내서 이것 실행하라고 핸들러의 handleMessage()에 던져준다.
그럼 그것을 받아서 Main Thread가 동작을 한다.
<br>

- 그리고 메세지큐에 던질 수 있는게 메세지 객체도 있는데, Runnable 객체도 있다.
Runnable 객체가 메세지큐에 들어오면 루퍼는 꺼내서 실행만 시켜주면 된다.
Runnable이 들어오면 핸들러를 호출하지 않고, Runnable만 꺼내서 실행만 시켜주면 되니까.
<br>

- 그렇게 돌아가서 Sub Thread를 통해서 UI를 엑세스할 수 있게 되는 것이다.


## 핸들러(Handler) 객체 및 메소드 설명
#### (1) Handler 객체 , handleMessage() 메소드
```java
  Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case SET_COUNT :
          textViews[msg.arg1].setText(""+msg.arg2);
        break;
    }
  };
```

- Sub Thread로부터 메시지를 전달받을 **Handler** 를 생성한다.... <메시지 통신>

- Sub Thread에서 메시지를 전달하면 **handleMessage()** 메소드가 동작한다
  - handleMessage() -> 메세지를 컨트롤해주는 메소드
  - 내가 Sub Thread에서 메세지를 핸들러로 날려주면 handleMessage 함수가 동작을 한다는 말이다.
  - handleMessage() 메소드 안의 super.handleMessage(msg);는 안에 들어가보면 특별히 해주는 것이 없기 때문에 주석처리해줘도 된다.
<br>
- 메세지 객체가 정해져있다.  <msg>
- 핸들러를 통해서 일반적으로 자바에서 설계할때 핸들러를 통해서 메세지는 int로 주고 받는다.
- msg.what에 메세지를 담아서 보내는데 what의 타입이 int이다.
- 플래그값 상수 설계하듯이 핸들러로 메세지를 통신할때는 미리 상수로 다 정의해놓는다.
  - ex) public static final int SET_COUNT = 99;
- 어떤 메세지를 핸들러를 통해 Sub Thread에서 날려주면 switch를 통해서 msg.what을 꺼낸다.
- what이 SET_COUNT 일 경우는, textViews[ 값1 ].setText( ""+값2 );
- 이 handler는 Counter 인스턴스를 생성할 때(스레드가 new 될때), 인자로 handler를 넘겨줘야 한다.
<br>
- 이렇게 서브스레드를 통해서 메인스레드로 핸들러에 메시지를 태워서 보내면 실행을  시킬 수 있다.


#### (2) Message 객체
```java
// Counter라는 서브스레드는 핸들러를 받아서 일단 나한테 놔둔다. 나중에 쓰기 위해서(생성자에서)
class Counter extends Thread {
    Handler handler;
    int textViewIndex;
    int count = 0;

    public Counter(int index, Handler handler){
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

            // 핸들러를 통해 메세지를 날릴 때, Message객체를 생성해서 날릴 수 있다.
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
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
```



## 반복문 처리하는 3가지 방법

#### (1) 하나씩 선언하고, 호출한다.
```java
      // 생성부
       Counter counter1 = new Counter(0, handler); // 배열의 인덱스를 첫번째 인자로 넘겨준다.
       Counter counter2 = new Counter(1, handler);
       Counter counter3 = new Counter(2, handler);
       Counter counter4 = new Counter(3, handler);

      // 실행부
       counter1.start();
       counter2.start();
       counter3.start();
       counter4.start();
```

#### (2) 배열을 이용한다.
```java
      // 생성부
       Counter counter[] = new Counter[4];
       for(int i=0 ; i<4; i++) {
           counter[i] = new Counter(i, handler);
       }

      // 실행부
       for(int i=0; i<4; i++) {
           counter[i].start();
       }
```

#### (3) ArrayList를 이용한다.
```java
      // 생성부
       ArrayList<Counter> counters = new ArrayList<>();

       for(int i=0; i<4; i++) {
          counters.add(new Counter(i, handler));
       }

      // 실행부
       for(int i=0; i<counters.size(); i++){
           counters.get(i).start();
       }          
```
