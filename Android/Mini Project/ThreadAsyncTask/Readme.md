# ThreadAsyncTask
#### AsyncTask에 대해서 알아본다.

 - sendEmptyMessage() 메소드
 - AsyncTask()
    - AsyncTask()의 3가지 인자
    - onPreExecute()
    - doInBackground()
    - onPostExecute()
    - onProgressUpdate()
 - ProgressDialog

 #### **[<전체소스코드>](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ThreadAsyncTask/app/src/main/java/com/mdy/android/threadasynctask/MainActivity.java)**

## sendEmptyMessage()
- Main UI에 현재 Thread가 접근할 수 없으므로 handler를 통해 호출해준다.
- 메세지객체를 직접 생성하지 않고, 값을 넘기지 않을 때는 what에 해당하는 메세지만 넘겨주면 된다.
- 그게 **sendEmptyMessage()** 이다.
```java
  public static final int SET_DONE = 1;

  // thread 에서 호출하기 위한 handler
  // Handler를 만들고 handleMessage() 메소드를 Override해준다.
  Handler handler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
          switch (msg.what) {
              case SET_DONE:
                  setDone();
                  break;
          }
      }
  };
```

```java
  @Override
  public void run() {
      try {
          Thread.sleep(1000);    // 1초 후에
          handler.sendEmptyMessage(MainActivity.SET_DONE);  // what에 해당하는 메세지
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
  }
```





## AsyncTask()에 대한 설명과 AsyncTask()의 제네릭 인자 3가지
- AsyncTask의 execute()는 Thread의 start()와 같은 역할을 한다.
- AsyncTask의 doInBackground는 Thread의 run()과 같은 역할을 한다..
<br>

- AsyncTask에서 Implement를 해줘야 하는데
  doInBackground() 메소드가 Thread의 run()과 같은 역할이다.
  AsyncTask의 doInBackground() 메소드는 데이터를 처리하는 것까지만 담당해준다.
<br>

- 데이터를 처리해주기 전에(doInBackground() 메소드가 호출되기 전에)
  자동으로 호출해주는 함수이름이 onPreExecute()이다.
<br>

- 데이터를 처리한 후에(doInBackground() 메소드가 처리되고 나서)
  자동으로 호출해주는 함수이름이 onPostExecute()이다.
<br>

- 내부에서 AsyncTask를 여러개 쓸 경우에는 ProgressDialog를 메인(onCreate)에 정의해놓고 쓰는게 좋고,
  AsyncTask를 1개만 사용할 경우에는 AsyncTask 내부에 ProgressDialog를 정의해놓고 써도 된다.
<br>

- doInBackground() 는 서브쓰레드에서 실행되고,
  onPreExecute() 와 onPostExecute() 는 메인쓰레드에서 실행된다.
  그래서 메인에 직접 엑세스해서 UI를 조작해줄 수 있는 것이다.

#### [ AsyncTask의 3가지 인자 설명 ]
##### 첫번째 인자
```java
  new AsyncTask<String, Integer, Float>(){

    @Override
    protected void doInBackground(String... params) { }

  }.execute("안녕", "하세요"); // <- doInBackground 를 실행시켜준다.
```

> doInBackground() 메소드에 인자를 String으로 넣어주면
> params[0], params[1], params[2] ... 이런 식으로 배열 형태로 값을 받게 된다.
> 인자를 넣어줄때는 execute를 통해 넣어준다.
> ex)  .execute("안녕", "하세요");
> 같은 타입인 여러개의 인자로 넘어가면 꺼냈을때 배열처럼 꺼내쓸 수 있다.
> 안녕하세요를 execute에 넘기면 execute가 doInBackground() 를 직접 호출해주는 것이다.
> 그런데 doInBackground()가 호출이 되기 전에, onPreExecute()가 먼저 자동으로 호출이 된다.


##### 두번째 인자
- onProgressUpdate의 타입
- onProgressUpdate의 용도는 주기적으로 doInBackground 에서 호출이 가능한 함수이다.
- progress bar의 내용에 10%, 20%, 30% ... 100% 보여줄때 사용된다.
```java
  new AsyncTask<String, Integer, Float>(){

    // 주기적으로 doInBackground 에서 호출이 가능한 함수
    @Override
    protected void onProgressUpdate(Integer... values) {
        progress.setMessage("진행율 = " + values[0] + "%");
    }

  }
```

```java
  publishProgress(i*10); // <- onProgressUpdate 를 주기적으로 업데이트 해준다.
```
- 이렇게 쓰레드 내부에서 메인 UI를 계속 만질 수 있게 해주는 장치들을 AsyncTask에는 많이 만들어놨다.

> AsyncTask를 사용하면 이런 것들을 편하게 사용할 수는 있는데 자주 사용하는 것은 아니고
> 처리하려고 하는 서브쓰레드에서 뭔가를 처리하는데 그게 Task가 한정적일때
> 1초에서 10초 안에 끝난다거나 혹은 10가지 일만 처리하고 끝난다거나 그렇게 루틴이 정해져있을때 쓴다.
> 채팅서버를 만들때 AsyncTask를 쓰지는 않는다.
> 채팅은 한정되어 있지 않고 내가 끌때까지 계속 돌아가는 것이니까.
> 소켓이 서버랑 만나서 소켓을 열어놓고, 계속 열려있어야 되니까 이런 경우에는 AsyncTask를 사용하지 않는다.
> 대표적으로 AsyncTask를 많이 사용하는 경우는 네트워크를 통해 서버에서 데이터를 가져올때이다.
> (목록같은거 뿌려줄때)


##### 세번째 인자
- doInBackground 의 리턴타입 이다.
```java
  new AsyncTask<String, Integer, Float>(){

    @Override
    protected Float doInBackground(String... params)
    {
      ...
      return 1000.4f; // 리턴되는 값은 onPostExecute의 인자로 넘겨준다.
    }

    // doInBackground가 처리되고 나서 호출된다.
    @Override
    protected void onPostExecute(Float result) { }


  }
```
- > doInBackground 의 리턴타입은 onPostExecute의 인자랑 동일하다.
  > 이 말은 doInBackground에서 리턴하면 onPostExecute로 넘어간다는 말이다.
  > doInBackground에서 어떤 처리가 끝나고 나서 값을 메인쓰레드로 넘겨줄 수 있다는 말이다.
- > 예를 들어, doInBackground를 통해 네이버에서 오늘의 날씨가 맑음이라는 데이터를 가져온 다음에 return을 통해서 맑음을 onPostExecute(결과값)로 넘겨줄 수 있다.
  > (서브스레드에서 메인스레드로 값이 넘어간다.)
  > 그러면 onPostExecute()에서는 인자로 온 것을 꺼내 쓰면 된다.

##### execute는 인자값으로 doInBackground의 인자를 통해 메인쓰레드에서 서브쓰레드로 값을 넘겨주고,
##### doInBackground()의 리턴값을 onPostExecute()의 인자값으로 서브쓰레드에서 메인쓰레드로 값을 넘겨준다.
<br>



## ProgressDialog
- progress는 화면에 진행상태를 표시해준다.
- ProgressDialog 정의
```java
  ProgressDialog progress = new ProgressDialog(this);
```
> ProgressDialog 인자에 this가 아닌 getBaseContext()를 넘겨주면 오류가 난다.
> getBaseContext()는 Context의 리소스만 갖고 있는데
> 지금은 ProgressDialog가 Context말고 테마(theme)라는 것을 쓴다. (액티비티의 스타일 요소)

- progress 사용시 기본적으로 필요한 아래 3가지를 세팅해준다.
```java
  progress.setTitle("진행중...");
  progress.setMessage("진행중 표시되는 메시지입니다");
  progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);    // 빙글빙글 돌아가는거
```

- progress 창을 호출
```java
  progress.show();
```

- progress 창을 해제
```java
  progress.dismiss();
```

#### [ ProgressDialog를 메인(onCreate)에 정의하는 경우와 AsyncTask 내부에 정의하는 경우 ]
- 내부에서 AsyncTask를 여러개 쓸 경우에는 ProgressDialog를 메인(onCreate)에 정의해놓고 쓰는게 좋고,
  AsyncTask를 1개만 사용할 경우에는 AsyncTask 내부에 ProgressDialog를 정의해놓고 써도 된다.

##### (1) ProgressDialog를 메인(onCreate)에 정의하는 경우
```java
public class MainActivity extends AppCompatActivity {
    public static final int SET_DONE = 1;
    TextView textView;
    ProgressDialog progress;

    // thread 에서 호출하기 위한 handler
    // Handler를 만들고 handleMessage() 메소드를 Override해준다.
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SET_DONE:
                    setDone();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAsync();
            }
        });


        /* ProgressDialog에 관한 주석 설명 */
        // 메인(onCreate)에 놓고 사용하려면 ProgressDialog progress;를 위에 선언해야 한다.
        // 그러나 AsyncTask 내부에 ProgressDialog를 사용할 경우에는 AsyncTask 내부에 ProgressDialog progress;를 선언하고 사용한다.

        // progress는 화면에 진행상태를 표시
        // ProgressDialog 정의
        progress = new ProgressDialog(this);
        // 여기에는 getBaseContext()를 넘겨주면 오류가 난다.
        // getBaseContext()는 Context의 리소스만 갖고 있기 때문에
        // 그러나 지금은 Dialog가 Context말고 theme라는 것을 쓴다. (액티비티의 스타일 요소)

        // progress 사용시 기본적으로 필요한 아래 3가지를 세팅해주면 된다.
        progress.setTitle("진행중...");
        progress.setMessage("진행중 표시되는 메시지입니다");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);    // 빙글빙글 돌아가는거


    }

    private void setDone() {
        textView.setText("Done!!!");
        // 프로그래스 창을 해제
        progress.dismiss();
    }


    private void runAsync(){
        new AsyncTask<String, Integer, Float>(){

            // 제네릭 타입 1 - doInBackground 의 인자
            //            2 - onProgressUpdate 의 인자
            //            3 - doInBackground 의 리턴타입 ,
            // doInBackground 가 호출되기 전에 먼저 호출된다.
            @Override
            protected void onPreExecute() {
                // 프로그래스 창을 호출
                progress.show();
            }

            // AsyncTask의 doInBackground 메소드는 Thread의 run과 같은 역할이다.
            @Override
            protected Float doInBackground(String... params) { // 데이터를 처리...
                // 10초 후에
                try {
                    for(int i=0 ; i<10 ; i++) {
                        publishProgress(i*10); // <- onProgressUpdate 를 주기적으로 업데이트 해준다.
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 1000.4f;
            }

            // doInBackground 가 처리되고 나서 호출된다.
            @Override
            protected void onPostExecute(Float result) {
                Log.e("AsyncTask","doInBackground의 결과값="+result);
                // 결과값을 메인 UI 에 세팅하는 로직을 여기에 작성한다.
                setDone();
            }

            // 주기적으로 doInBackground 에서 호출이 가능한 함수
            @Override
            protected void onProgressUpdate(Integer... values) {
                progress.setMessage("진행율 = " +values[0]+ "%");
            }
        }.execute("안녕", "하세요"); // <- doInBackground 를 실행

    }

/*    private void runThread() {
        // 프로그래스 창을 호출
        progress.show();
        CustomThread thread = new CustomThread(handler);
        thread.start();
    }*/


}



// 생성자를 통해 MainActivity의 handler를 넘겨준다.
class CustomThread extends Thread {
    Handler handler;

    public CustomThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);    // 10초 후에
            // Main UI 에 현재 thread 가 접근할 수 없으므로
            // handler 를 통해 호출해준다.
            // 메세지객체를 직접 생성하지 않고, 값을 넘기지 않을 때는 what에 해당하는 메세지만 넘겨주면 된다.
            // 그게 sendEmptyMessage()이다.
            handler.sendEmptyMessage(MainActivity.SET_DONE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

##### (2) AsyncTask 내부에 정의하는 경우
```java
public class MainActivity extends AppCompatActivity {
    public static final int SET_DONE = 1;
    TextView textView;

    // thread 에서 호출하기 위한 handler
    // Handler를 만들고 handleMessage() 메소드를 Override해준다.
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SET_DONE:
                    setDone();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAsync();
            }
        });


        /* ProgressDialog에 관한 주석 설명 */
        /* // 메인(onCreate)에 놓고 사용하려면 ProgressDialog progress;를 위에 선언해야 한다.
        // 그러나 AsyncTask 내부에 ProgressDialog를 사용할 경우에는 AsyncTask 내부에 ProgressDialog progress;를 선언하고 사용한다.

        // progress는 화면에 진행상태를 표시
        // ProgressDialog 정의
        progress = new ProgressDialog(this);
        // 여기에는 getBaseContext()를 넘겨주면 오류가 난다.
        // getBaseContext()는 Context의 리소스만 갖고 있기 때문에
        // 그러나 지금은 Dialog가 Context말고 theme라는 것을 쓴다. (액티비티의 스타일 요소)

        // progress 사용시 기본적으로 필요한 아래 3가지를 세팅해주면 된다.
        progress.setTitle("진행중...");
        progress.setMessage("진행중 표시되는 메시지입니다");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);    // 빙글빙글 돌아가는거 */


    }

    private void setDone() {
        textView.setText("Done!!!");
    }


    private void runAsync(){
        new AsyncTask<String, Integer, Float>(){

            ProgressDialog progress;

            // 제네릭 타입 1 - doInBackground 의 인자
            //             2 - onProgressUpdate 의 인자
            //             3 - doInBackground 의 리턴타입 ,
            // doInBackground 가 호출되기 전에 먼저 호출된다.
            @Override
            protected void onPreExecute() {
                // progress는 화면에 진행상태를 표시
                // ProgressDialog 정의
                progress = new ProgressDialog(MainActivity.this);
                progress.setTitle("진행중...");
                progress.setMessage("진행중 표시되는 메시지입니다");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                // 프로그래스 창을 호출
                progress.show();
            }

            // AsyncTask의 doInBackground 메소드는 Thread의 run과 같은 역할이다.
            @Override
            protected Float doInBackground(String... params) { // 데이터를 처리...
                // doInBackground() 메소드에 인자를 String으로 넣어주면
                // params[0], params[1], params[2] ... 이런 식으로 배열 형태로 값을 받게 된다.
                // 인자를 넣어줄때는 execute를 통해 넣어준다.
                // ex)  .execute("안녕", "하세요");
                // 같은 타입인 여러개의 인자로 넘어가면 꺼냈을때 배열처럼 꺼내쓸 수 있다.
                // 안녕하세요를 execute에 넘기면 execute가 doInBackground() 를 직접 호출해주는 것이다.
                // 그런데 doInBackground()가 호출이 되기 전에, onPreExecute()가 먼저 자동으로 호출이 된다.
                try {
                    Log.e("AsyncTask", "첫번째값" + params[0]);     // '안녕'  이 출력
                    Log.e("AsyncTask", "두번째값" + params[1]);     // '하세요'   가 출력
                    for(int i=0 ; i<10 ; i++) {
                        publishProgress(i*10); // <- onProgressUpdate 를 주기적으로 업데이트 해준다.
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 1000.4f; // 리턴되는 값은 onPostExecute의 인자로 넘겨준다.
            }

            // doInBackground 가 처리되고 나서 호출된다.
            @Override
            protected void onPostExecute(Float result) {
                Log.e("AsyncTask","doInBackground의 결과값="+result);
                // 결과값을 메인 UI 에 세팅하는 로직을 여기에 작성한다.
                setDone();
                // 프로그래스 창을 해제
                progress.dismiss();
            }

            // 주기적으로 doInBackground 에서 호출이 가능한 함수
            @Override
            protected void onProgressUpdate(Integer... values) {
                progress.setMessage("진행율 = " + values[0] + "%");
            }
        }.execute("안녕", "하세요"); // <- doInBackground 를 실행시켜준다.

    }

/*    private void runThread() {
        // 프로그래스 창을 호출
        progress.show();
        CustomThread thread = new CustomThread(handler);
        thread.start();
    }*/


}



// 생성자를 통해 MainActivity의 handler를 넘겨준다.
class CustomThread extends Thread {
    Handler handler;

    public CustomThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);    // 10초 후에
            // Main UI 에 현재 thread 가 접근할 수 없으므로
            // handler 를 통해 호출해준다.
            // 메세지객체를 직접 생성하지 않고, 값을 넘기지 않을 때는 what에 해당하는 메세지만 넘겨주면 된다.
            // 그게 sendEmptyMessage()이다.
            handler.sendEmptyMessage(MainActivity.SET_DONE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```
