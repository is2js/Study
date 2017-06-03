# Fragment
Fragment에 대해서 알아본다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/WebView/app/src/main/java/com/mdy/android/fragment/MainActivity.java)**

### FrameLayout 만들기
- xml에 파일에 FrameLayout을 만들고, id값(container)을 지정해준다.
```xml
  <FrameLayout
      android:id="@+id/container"
      android:layout_width="0dp"
      android:layout_height="0dp"
      android:layout_marginBottom="8dp"
      android:layout_marginLeft="8dp"
      android:layout_marginRight="8dp"
      android:layout_marginTop="8dp"
      app:layout_constraintBottom_toBottomOf="parent"
      app:layout_constraintHorizontal_bias="0.501"
      app:layout_constraintLeft_toLeftOf="parent"
      app:layout_constraintRight_toRightOf="parent"
      app:layout_constraintTop_toBottomOf="@+id/textView2">
```

### Fragment 클래스의 onCreateView 메소드 작성
- onCreateView() 메소드를 작성한다.
```java
    // ViewHolder와 똑같은 역할을 해준다.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 프래그먼트의 메인 레이아웃을 inflate 하고
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        // 안의 위젯들을 코드에 연결한다.
        Button btnGoDetail = (Button) view.findViewById(R.id.btnGoDetail);
        btnGoDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addDetail();
            }
        });
        return view;
    }
```

### Fragment 화면에 넣기
- Fragment 화면에 넣어준다. (transaction 코드 작성)
```java
    /*
     프래그먼트 화면에 넣기
    */
    // 1. 프래그먼트 트랜잭션 시작하기
    // 하위버전 호환성을 감안해서 android.support.v4로 FragmentTransaction을 만든다.
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    // 2. 화면에 프래그먼트 넣기
    transaction.add(R.id.container, list);

    // 트랜잭션 처리 전체를 stack에 담아놨다가, 안드로이드의 back 버튼으로 뒤로가기를 할 수 있다.
    transaction.addToBackStack(null); // null로 해주면 순서대로 빠져나온다.

    // 3. 트랜잭션 완료
    transaction.commit();
    // transation을 사용하면 스택을 사용할 수 있다.
```
