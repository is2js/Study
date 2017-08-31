# CustomToolbarExam
#### 1. Custom Toolbar에 대해서 알아본다.
#### 2. OptionMenu에 대해서 알아본다.
  - onCreateOptionMenu()
  - onOptionItemSelected(MenuItem item)

<br>
<br>

## 1. Custom Toolbar
### 예제 코드

#### 1.1. custom_actionbar.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:gravity="center_vertical"
    android:background="#a0dbc7"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageButton
        android:id="@+id/btnBack"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_marginLeft="19dp"
        android:layout_marginStart="19dp"
        android:background="@drawable/left_arrow_black"
        android:layout_centerVertical="true"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true" />

    <TextView
        android:id="@+id/title"
        android:layout_width="wrap_content"
        android:layout_height="56dp"
        android:layout_marginLeft="24dp"
        android:gravity="center_vertical"
        android:text="커스텀 툴바"
        android:textColor="#000000"
        android:textSize="20sp"
        android:layout_marginStart="24dp"
        android:layout_centerVertical="true"
        android:layout_toRightOf="@+id/btnBack"
        android:layout_toEndOf="@+id/btnBack" />

    <ImageButton
        android:id="@+id/btnAlarm"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_alignParentEnd="true"
        android:layout_alignParentRight="true"
        android:layout_alignTop="@+id/btnBack"
        android:layout_centerVertical="true"
        android:layout_marginEnd="14dp"
        android:layout_marginRight="14dp"
        android:background="@drawable/bell" />
</RelativeLayout>
```

#### 1.2. MainActivity.java
```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnAlarm, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void setViews(){
        btnAlarm = (ImageButton) findViewById(R.id.btnAlarm);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }

    private void setListeners(){
        btnAlarm.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAlarm:
                Toast.makeText(this, "알람 버튼이 클릭되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnBack:
                Toast.makeText(this, "Back 버튼이 클릭되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(actionbar);

        //액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar)actionbar.getParent();
        parent.setContentInsetsAbsolute(0,0);

        setViews();
        setListeners();

        return true;
    }
}
```

<br>
<br>

## 2. OptionMenu
### 예제 코드
#### 2.1. res - menu - menu.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:title="배경색 변경" >
        <menu>
            <item
                android:title="빨간색"
                android:id="@+id/mnuRed"/>
            <item
                android:title="파란색"
                android:id="@+id/mnuBlue"/>
            <item
                android:title="노란색"
                android:id="@+id/mnuYellow"/>
            <item
                android:title="하얀색"
                android:id="@+id/mnuWhite"/>
        </menu>
    </item>

    <item
        android:title="그림 60도 회전"
        android:id="@+id/item_rotation">
    </item>

    <item
        android:checkable="true"
        android:checked="true"
        android:id="@+id/item_title"
        android:title="제목 보이기"></item>
    <item
        android:checkable="true"
        android:title="그림 2배 확대"
        android:id="@+id/item_expansion"></item>

    <group android:checkableBehavior="single">
        <item
            android:checked="true"
            android:id="@+id/chicken"
            android:title="치킨선택"></item>
        <item
            android:title="스파게티"
            android:id="@+id/spaghetti"></item>
    </group>
</menu>
```

<br>

#### 2.2. MenuActivity.java
```java
public class MenuActivity extends AppCompatActivity {

    int rotation = 0;
    TextView textView;
    ImageView imageView;
    ConstraintLayout layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setTitle("메뉴를 눌러보세요.");
        textView = (TextView) findViewById(R.id.textView1);
        imageView = (ImageView) findViewById(R.id.image1);
        layout1 = (ConstraintLayout) findViewById(R.id.constraintLayout);

        Button btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuRed:
                layout1.setBackgroundColor(Color.rgb(255,0,0));
                break;

            case R.id.mnuBlue:
                layout1.setBackgroundColor(Color.rgb(0,0,255));
                break;

            case R.id.mnuYellow:
                layout1.setBackgroundColor(Color.rgb(255,255,0));
                break;
            case R.id.mnuWhite:
                layout1.setBackgroundColor(Color.rgb(255,255,255));
                break;

            case R.id.item_rotation:
                rotation+=60;
                if(rotation >=360) {
                    rotation-=360;
                }
                imageView.setRotation(rotation);
                break;

            case R.id.item_title:
                if(item.isChecked()){
                    item.setChecked(false);
                    textView.setVisibility(View.GONE);
                }else{
                    item.setChecked(true);
                    textView.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.item_expansion:
                if(item.isChecked()){
                    item.setChecked(false);
                    imageView.setScaleX(1);
                    imageView.setScaleY(1);
                }else{
                    item.setChecked(true);
                    imageView.setScaleX(2);
                    imageView.setScaleY(2);
                }
                break;

            case R.id.chicken:
                item.setChecked(true);
                textView.setText("치킨");
                imageView.setImageResource(R.drawable.chicken);
                break;

            case R.id.spaghetti:
                item.setChecked(true);
                textView.setText("스파게티");
                imageView.setImageResource(R.drawable.spaghetti);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
```

<br>

#### 2.3. activity_menu.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/constraintLayout"
    tools:context="android.mdy.com.customtoolbarexam.MenuActivity">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Menu Activity"
        android:textColor="@android:color/black"
        android:textSize="30sp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintHorizontal_bias="0.502"
        android:layout_marginTop="16dp" />

    <Button
        android:id="@+id/btnPrevious"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="16dp"
        android:text="Previous"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent" />

    <ImageView
        android:id="@+id/image1"
        android:layout_width="221dp"
        android:layout_height="192dp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@drawable/chicken"
        android:layout_marginTop="159dp"
        app:layout_constraintHorizontal_bias="0.503" />

    <TextView
        android:id="@+id/textView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="15dp"
        android:text="맛있는 치킨"
        android:textColor="@android:color/black"
        android:textSize="20sp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/image1" />
</android.support.constraint.ConstraintLayout>
```
