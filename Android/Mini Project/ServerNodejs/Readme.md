# ServerNodejs (Activity)
### Nodejs 서버를 통해 받는 데이터(MySQl - 관계형DB)를 Retrofit을 이용해 화면에 출력해본다.
  - ##### `startActivityForResult()` 와 `onActivityResult()`
  - ##### Back버튼 `onBackPressed()` 와 `finish()`
  - ##### `Retrofit`
    - RequestBody/ResponseBody 이용
    - .addConverterFactory(GsonConverterFactory.create())를 사용하지 않고 통신


<br>
<br>
<br>

## Gradle 세팅 (레트로람다)
- #### 앱 gradle
```
apply plugin: 'com.android.application'
// 람다추가 2
apply plugin: 'me.tatarka.retrolambda'

android {
    compileSdkVersion 25
    buildToolsVersion "25.0.3"
    defaultConfig {
        applicationId "android.mdy.com.servernodejs"
        minSdkVersion 15
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    // 람다추가 3
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    // Rx 안드로이드
    // Retrofit
    // Retrofit json 컨버터
    // Retrofit Rx 아답터
    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'
    compile 'com.android.support:recyclerview-v7:25.3.1'
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
    compile 'com.squareup.retrofit2:retrofit:2.3.0'
    compile 'com.squareup.retrofit2:converter-gson:2.3.0'
    compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
    testCompile 'junit:junit:4.12'
}
```
- #### 빌드 gradle
```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.3'
        // 람다추가 1
        classpath 'me.tatarka:gradle-retrolambda:3.7.0'


        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

<br>
<br>
<br>

## `startActivityForResult()` 와 `onActivityResult()`
- #### MainActivity.java 에서 onActivityResult메소드
  - WriteActivity.java에서 돌려받는 경우가 1가지(작성을 했을 경우)이기 때문에 resultCode를 하나로 해줘도 된다.

```java
private final static int REQ_CODE = 101;

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // super.onActivityResult(requestCode, resultCode, data);
    if(resultCode == Activity.RESULT_OK){
        switch(requestCode){
            case REQ_CODE:
                this.data.clear(); // 기존에 있던 데이터를 삭제해준다...
                                   // 중복되는 데이터가 있다면 갱신하지 않는 방향으로...
                loader();
                break;
        }
    }
}
```

- #### MainActivity 에서 startActivityForResult를 통해 WriteActivity를 호출했는데 데이터를 넘겨받지 않기 때문에 다음과 같이 코딩을 한다.
  - setResult 메소드에 인자 1개
```java
setResult(MainActivity.SUCCESS);   // 값을 인텐트에 담아두기만 한다.
finish();
```

- #### 데이터를 보낼 경우
  - 이렇게 하면 MainActivity에 있는 onActivityResult()에 세번째 인자인 Intent data에 값이 넘어간다. (setResult 메소드에 인자 2개)
```java
Intent intent = getIntent();
intent.putExtra("result", 123);
setResult(RESULT_OK, intent);
```

- #### Back 버튼
```java
@Override
public void onBackPressed() {
    super.onBackPressed();  // Back버튼 실행

    /* 데이터를 보내지 않기 때문에 바로 setResult(Back);으로 작성한다. */
    // Intent intent = getIntent();
    // setResult(BACK, intent);
    setResult(BACK);  // BACK은 상수처리한 값
}
```
- 데이터를 보내지 않기 떄문에 바로 setResult(BACK); 으로 코딩을 하면 된다.
- 그러나 여기서는 데이터를 보내주는 것이 없기 때문에 onBackPressed() 코딩을 하지 않아도 Back버튼을 누르면 스택에서 WriteActivity가 사라진다. 즉, 굳이 코딩하지 않아도 되는 부분이다.

  ### [정리]
  #### 1) 데이터를 보낼 경우
  ```java
  Intent intent = getIntent();
  intent.putExtra("result", 123);
  setResult(RESULT_OK, intent); // setResult의 인자 2개
  ```
    - 이렇게 하면 MainActivity에 있는 onActivityResult()의 세번째 인자인 Intent data에 값이 넘어간다.

  #### 2) 데이터를 보내지 않을 경우
  ```java
  setResult(RESULT_OK); // setResult의 인자 1개
  ```


- #### startActivityForResult
  - startActivityOnResult를 통해 어떤 액티비티를 호출하고, 해당 액티비티에서 다시 호출했던 액티비티로 돌아오면 onActivityResult가 호출된다. (finish 혹은 Back버튼을 통해)
    - 그렇기 때문에 호출됐던 액티비티(WriteActivity)에서 new Intent로 호출한 액티비티로 다시 오면 당연히 onActivityResult가 호출이 안된다. 새롭게 MainActivity가 호출되어 스택에 쌓인다.

<br>
<br>

## Back버튼 `onBackPressed()` 와 `finish()`
- 둘의 기능과 동작방식은 동일하다.
- 둘다 호출 후, Activity의 생명주기인 onDestoryed() 메소드가 호출된다.
- onBackPressed() 메소드 와 finish(); 를 호출할 경우,
  - Back버튼 후 무엇인가 처리를 해줘야 한다면, ***onBackPressed()*** 또는 ***onDestoryed()*** 사용
  - finish() 메소드 후 무엇인가 처리를 해줘야 한다면, ***onDestoryed()*** 사용



<br>
<br>


<br>
<br>
<br>

## 소스 코드

- #### MainActivity.java
```java
public class MainActivity extends AppCompatActivity {

    private Button btnWrite;
    private RecyclerView recyclerView;
    private List<Bbs> data;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();

        data = new ArrayList<>();
        adapter = new RecyclerAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loader();
    }


    private void setViews() {
        btnWrite = (Button) findViewById(R.id.btnPost);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }


    private void setListeners() {
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                    호출시 startActivity를 사용하면 onResume 처리를 따로 해줘야 된다.(생명주기처리)
                    그래서 다른것 사용

                    위의 두 가지를 구분하고
                    1번 또는 2번 결과값을 MainActivity로 넘겨서 처리
                 */

                Intent intent = new Intent(MainActivity.this, WriteActivity.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });
    }

    private static final int REQ_CODE = 77;
    public static final int SUCCESS = 1001;
    public static final int BACK = 1002;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("실행 순서 확인 3", "========================= 3 ");

        if(requestCode == REQ_CODE){
            // 정상적으로 Post를 한 경우
            if(resultCode == SUCCESS){
                Log.e("로그 확인", "===================== 작성 완료.");

                Log.e("실행 순서 확인 4", "========================= 4 ");
                this.data.clear();
                // 데이터를 갱신
                loader();

            // Back버튼으로 돌아온 경우
            } else if (resultCode == BACK){
                Log.e("로그 확인", "===================== 입력값들 중 입력하지 않은 값이 있음.");

                Log.e("실행 순서 확인 5", "========================= 5 ");
                this.data.clear();
                // 데이터를 갱신
                loader();
            }
        }
    }

    private void loader() {
        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(IBbs.SERVER)
              //  .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // -> 이렇게 하면 client가 생성된다.


        // 2. 서비스 연결
        IBbs myServer = client.create(IBbs.class);

        // 3. 서비스의 특정 함수 호출 -> Observable 생성
        Observable<ResponseBody> observable = myServer.read();

        // 4. subscribe 등록
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseBody -> {
                            // 1. 데이터를 꺼내고
                            String jsonString = responseBody.string();
                            Log.e("Retrofit", "data = " + jsonString);


                            Gson gson = new Gson();

                            /* 방법1 */   //  addConverterFactory(GsonConverterFactory.create())의 역할
//                            Type type = new TypeToken<List<Bbs>>(){}.getType(); //  컨버팅하기 위한 타입지정
//                            List<Bbs> data = gson.fromJson(jsonString, type);

                            Bbs data[] = gson.fromJson(jsonString, Bbs[].class);

                            // 2. 아답터에 세팅하고
                            for(Bbs bbs : data){
                                this.data.add(bbs);
//                                Log.e("Data", "title = " + bbs.title);
                            }
                            // 3. 아답터 갱신
                            adapter.notifyDataSetChanged();
                        }
                );
    }
}
```

<br>



- #### RecyclerAdapter.java
```java
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    LayoutInflater inflater;
    List<Bbs> data;

    public RecyclerAdapter(Context context, List<Bbs> data){
        this.data = data;
//        this.inflater = LayoutInflater.from(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = data.get(position);
        holder.setTitle(bbs.title);
        holder.setContent(bbs.content);
        holder.setAuthor(bbs.author);
        holder.setDate(bbs.date);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDate;
        TextView txtAuthor;
        TextView txtContent;

        public Holder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);
        }

        public void setTitle(String title){
            txtTitle.setText("제목 : " + title);
        }
        public void setDate(String date){
            txtDate.setText("날짜 : " + date);
        }
        public void setAuthor(String author){
            txtAuthor.setText("저자 : " + author);
        }
        public void setContent(String content){
            txtContent.setText("내용 : " + content);
        }
    }
}
```

<br>



- #### WriteActivity.java
```java
package android.mdy.com.servernodejs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static android.mdy.com.servernodejs.MainActivity.BACK;

public class WriteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextAuthor;
    private EditText editTextContent;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();

        btnPost.setOnClickListener(
               v -> {
                   String title = editTextTitle.getText().toString();
                   String author = editTextAuthor.getText().toString();
                   String content = editTextContent.getText().toString();

                   postData(title, author, content);
            }
        );
    }

    private void postData(String title, String author, String content){
        // 0. 입력할 객체 생성
        Bbs bbs = new Bbs();
        bbs.title = title;
        bbs.author = author;
        bbs.content = content;


        // 예외처리 - 텍스트 모두 입력하지 않을 경우
        if(bbs.title.equals("") || bbs.author.equals("") || bbs.content.equals("")) {
            Toast.makeText(WriteActivity.this, "텍스트를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;

        } else {

        // 텍스트를 모두 입력한 경우

            // 1. 레트로핏 생성
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(IBbs.SERVER)
                    //  .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            // -> 이렇게 하면 client가 생성된다.


            // 2. 서비스 연결
            IBbs myServer = client.create(IBbs.class);

            // 3. 서비스의 특정 함수 호출 -> Observable 생성
            Gson gson = new Gson();

            // bbs 객체를 수동으로 전송하기 위해서는 bbs 객체를 jsonString으로 변환하고
            // RequestBody에 미디어타입과 String으로 변환된 데이터를 담아서 전송
            RequestBody requestBody = RequestBody.create(
                    MediaType.parse("application/json"), gson.toJson(bbs)
            );

            Observable<ResponseBody> observable = myServer.write(requestBody);

            // 4. subscribe 등록
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            responseBody -> {
                                String result = responseBody.string();  // 결과코드를 넘겨서 처리


                                /* 데이터를 보낼 경우 */

                                /*
                                Intent intent = getIntent();
                                intent.putExtra("result", 123);
                                setResult(RESULT_OK, intent);

                                이렇게 하면 MainActivity에 있는 onActivityResult()에 세번째 인자인 Intent data에 값이 넘어간다.

                                */

                                setResult(MainActivity.SUCCESS);   // 값을 인텐트에 담아두기만 한다.
                                finish();
                            }
                    );
        }
    }

    private void initView() {
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextAuthor = (EditText) findViewById(R.id.editTextAuthor);
        editTextContent = (EditText) findViewById(R.id.editTextContent);
        btnPost = (Button) findViewById(R.id.btnPost);
    }

    // Back 버튼
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // Back버튼 실행

        /* 데이터를 보내지 않기 때문에 바로 setResult(Back);으로 작성한다. */
        // Intent intent = getIntent();
        // setResult(BACK, intent);
        setResult(BACK);
    }
}
```

<br>



- #### IBbs.java (인터페이스)
```java
public interface IBbs {
    public static final String SERVER = "http://192.168.10.79/";    // Retrofit을 사용할 때, 반드시 주소 뒤에 슬래시(/)를 붙여줘야 한다.
    @GET("bbs")
    public Observable<ResponseBody> read();

    @POST("bbs")
    public Observable<ResponseBody> write(@Body RequestBody body);

    @PUT("bbs")
    public Observable<ResponseBody> update(Bbs bbs);

    @DELETE("bbs")
    public Observable<ResponseBody> delete(Bbs bbs);
}
```

<br>



- #### Bbs.java
```java
public class Bbs {
    int id;
    String title;
    String content;
    String author;
    String date;
}
```

<br>
<br>
<br>






## 동작화면

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ServerNodejs/graphics/write1.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ServerNodejs/graphics/list1.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ServerNodejs/graphics/write2.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ServerNodejs/graphics/list2.png' width='210' height='350' />


##### MySql 결과화면

![mysql](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ServerNodejs/graphics/mysql.png)
