# RxAndroidBasic07
#### RxBinding Library 에 대해서 알아본다.
#### Observable 결합 - `CombineLatest`에 대해서 알아본다.

<br>
<br>


## Observable 결합 - `CombineLatest`
- 두 개의 Observable 중 하나가 항목을 배출할 때 배출된 마지막 항목과 다른 한 Observable이 배출한 항목을 결합한 후 함수를 적용하여 실행 후 실행된 결과를 배출한다



<br>

## RxBinding Library Gradle 추가
```groovy
implementation 'com.jakewharton.rxbinding2:rxbinding-appcompat-v7:2.+'
```

<br>

## 예제 소스 코드
```java
public class MainActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private Button btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        Observable<TextViewTextChangeEvent> idEmitter = RxTextView.textChangeEvents(editEmail);
        Observable<TextViewTextChangeEvent> pwEmitter = RxTextView.textChangeEvents(editPassword);

        Observable.combineLatest(idEmitter, pwEmitter,
                (idEvent, pwEvent) -> {
                    boolean checkId = idEvent.text().length() >= 5;
                    boolean checkPw = pwEvent.text().length() >= 8;
                    return checkId && checkPw && isValidEmail(idEvent.text().toString());
                }
        ).subscribe(
            flag -> btnSign.setEnabled(flag)
        );
    }

    // 이메일 유효성 검사
    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    private void setViews() {
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnSign = (Button) findViewById(R.id.btnSign);
        btnSign.setEnabled(false);
    }
}
```

<br>

## 동작화면

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic07/graphics/ScreenShot1.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic07/graphics/ScreenShot2.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic07/graphics/ScreenShot3.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic07/graphics/ScreenShot4.png' width='210' height='350' />
