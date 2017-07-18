# RxAndroidBasic01
- #### 옵저버 패턴에 대해서 알아본다.

<br>
<br>

## 옵저버 패턴 (Observer Pattern)
- `옵저버 패턴(Observer Pattern)` 은 객체의 상태 변화를 관찰하는 관찰자들, 즉 옵저버들의 목록을 객체에 등록하여 상태 변화가 있을 떄마다 메서드 등을 통해 객체가 직접 목록의 각 옵저버에게 통지하도록 하는 `디자인 패턴`이다.
- 대상이 되는 객체를 1대 n의 의존성으로 묶을 수 있는 방법을 제공하고 대상 객체에 변경사항이 있으면 의존성이 있는 모든 객체에 자동으로 알림을 제공하는 디자인 패턴이다.
- `Observer Pattern` 은 Subject에 기반을 두고 Subject에 변경사항이 있으면 자신의 List에 존재하는 모든 객체에 알림을 전달한다.
- `Observer Pattern` 은 Sub Thread 에서 작동한다.

<br>
<br>

## 예제 코드
### 옵저버 패턴 이해하기
- ##### 1초에 한번씩 등록된 옵저버들에게 "Hello" 메시지를 날린다.
<br>

#### Subject 클래스
```java
public class Subject extends Thread{

    // Subject는 옵저버를 담아둘 수 있는(저장할) 공간이 필요
    List<Observer> observers = new ArrayList<>();   // 옵저버를 등록하는 저장소
    boolean run_flag = true;

    public Subject() {

    }

    @Override
    public void run() {
        while(run_flag) {
            try {
                Thread.sleep(1000);
                for (Observer observer : observers) {
                    observer.notification("Hello");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 옵저버를 등록하는 함수
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    // 옵저버를 삭제하는 함수
    public void deleteObserver(int count){
        observers.remove(count);
    }

    // 옵저버에 공지하는 함수
    public interface Observer {
        public void notification(String msg);
    }
}
```
<br>

#### MainActivity
```java
public class MainActivity extends AppCompatActivity {
    private Button btnAddObserver;
    private Button btnDeleteObserver;
    Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListeners();

        // subject 생성
        subject = new Subject();

        // 서브젝트 동작시작
        subject.start();    // Thread를 상속받아 만들었기 때문에 명령어를 날려주면 run이 계속 동작을 하게 된다.

    }

    int count = 0;

    private void initView() {
        btnAddObserver = (Button) findViewById(R.id.btnAddObserver);
        btnDeleteObserver = (Button) findViewById(R.id.btnDeleteObserver);
    }

    private void setListeners(){
        // 버튼이 클릭될 때마다 Subject에 옵저버를 추가한다.
        btnAddObserver.setOnClickListener(v -> {
            count++;
            subject.addObserver(new ObserverImpl("Observer" + count));
        });

        // 버튼이 클릭되면 옵저버를 삭제한다.
        btnDeleteObserver.setOnClickListener(v -> {
            count--;
            subject.deleteObserver(count);
        });
    }


    // 옵저버의 구현체
    public class ObserverImpl implements Subject.Observer {
        String myName = "";

        public ObserverImpl(String name) {
            myName = name;
        }

        @Override
        public void notification(String msg) {
            System.out.println(myName + ":" + msg);
        }
    }
}
```
