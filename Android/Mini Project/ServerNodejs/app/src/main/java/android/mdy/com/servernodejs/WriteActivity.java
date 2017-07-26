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


        /*
            그러나 여기에서 Back버튼에서 아무것도 하는게 없기 때문에 onBackPressed가 아예 없어도 된다.
            그리고 MainActivity에 있는 onActivityResult()에도 resultCode를 보내지 않고, onActivitiyResult에서 처리하지 않으면 된다.
         */

        setResult(BACK);
    }
}
