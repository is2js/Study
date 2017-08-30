# SwipeRefreshLayoutExam
#### SwipeRefreshLayout에 대해서 알아본다.

<br>
<br>

## 순서
### 1. Gradle 추가
```
dependencies {
    ...

  compile 'com.android.support:support-v4:26.+'

    ...
}
```

<br>

### 2. layout
  - 새로고침을 적용할 뷰를 SwipeRefreshLayout으로 감싸준다.
```xml
<android.support.v4.widget.SwipeRefreshLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/swipe_layout">

    ...

</android.support.v4.widget.SwipeRefreshLayout>
```

<br>

### 3. Java 코드
3.1. SwipeRefreshLayout 객체 생성 및 OnRefreshListener 인터페이스 등록
```java
// 인터페이스 등록
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

...

SwipeRefreshLayout swipeRefreshLayout;

...

swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
swipeRefreshLayout.setOnRefreshListener(this);
```
- OnRefreshListener 인터페이스는 onRefresh 메소드를 가지고 있다.
onRefresh()는 화면을 당겼다 놓았을때 호출되는 부분이다.
```java
@Override
public void onRefresh() {
  // 화면을 당겼다 놓았을 때 호출되는 코드
  ...

  // 새로고침 완료
  swipeRefreshLayout.setRefreshing(false);
}
```
- 새로고침이 완료되었다면 반드시 setRefreshing(false)를 호출해야 한다. 그렇지 않으면 새로고침 아이콘이 사라지지 않는다.

3.2. 새로고침 아이콘 색상 설정
- 새로고침 아이콘의 색상을 바꾸려면 setColorSchemaResources 메소드를 사용한다.
```java
swipeRefreshLayout.setColorSchemaResources(
    android.R.color.holo_blue_bright,
    android.R.color.holo_green_light,
    android.R.color.holo_orange_light,
    android.R.color.holo_red_light
)
```
- 새로고침의 화살표가 한바퀴 돌때마다 각각의 색으로 변경된다.

<br>
<br>

## 전체 소스 코드
```java
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();
    }

    private void setViews(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        textView = (TextView) findViewById(R.id.textView);
    }

    private void setListeners(){
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        count++;
        textView.setText(count + "");

        swipeRefreshLayout.setRefreshing(false);
    }
}
```
