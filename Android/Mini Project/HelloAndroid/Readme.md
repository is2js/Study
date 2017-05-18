### 2017.05.18(목)
---
##### 클릭리스너 3가지 방법 / Travis 연결

###### 1. 클릭리스너 3가지 방법
```java
/**
 * 클릭리스너 구현하는 방법 세가지
 * 1. 위젯을 사용하는 객체가 상속받아서 구현
 * 2. 객체내에서 변수로 생성
 * 3. setOnclickListener 함수에 익명 객체로 전달
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.btnClick);

        // 1 번형태로 구현
        btn.setOnClickListener(this);

        // 2 번형태로 구현 - 아래에 구현된 리스너를 등록해준다.
        btn.setOnClickListener(listener);

        // 3 번형태로 구현
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("안녕 안드로이드!!!");
            }
        });
    }
    // 1번 형태
    @Override
    public void onClick(View v) {
        tv.setText("안녕 안드로이드!!!");
    }
    // 2번 형태
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tv.setText("안녕 안드로이드!!!");
        }
    };
}
```

---
###### 2. [.travis.yml]파일
```
language: android

jdk: oraclejdk7

android:
  components:
  - platform-tools             # ADB (디바이스 또는 에뮬레이터와 통신) 포함
  - tools                      # 실제 안드로이드 SDK
  - build-tools-25.0.3         # 빌드 툴 버전
  - android-25                 # 타겟 버전
  - extra-android-m2repository # 안드로이드에서 제공하는 라이브러리

before_install:
  - chmod +x gradlew

script: ./gradlew build
```
