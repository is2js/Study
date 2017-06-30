package com.mdy.android.serverconnection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mdy.android.serverconnection.domain.Bbs;
import com.mdy.android.serverconnection.domain.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mdy.android.serverconnection.BbsService.BASE_URL;

public class WriteActivity extends AppCompatActivity {
    EditText editTitle;
    EditText editAuthor;
    EditText editContent;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editContent = (EditText) findViewById(R.id.editContent);
        btnPost = (Button) findViewById(R.id.btnPost);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String author = editAuthor.getText().toString();
                String content = editContent.getText().toString();

                // 위젯에 입력된 값을 객체에 담고
                Bbs bbs = new Bbs();
                bbs.title = title;
                bbs.author = author;
                bbs.content = content;

//                // json 스트링으로 변환
//                Gson gson = new Gson();
//                String jsonString = gson.toJson(bbs);

                // 레트로핏 객체 정의
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BbsService.BASE_URL)  // retrofit을 사용할때 도메인에는 끝에 '/'을 꼭 써줘야 한다.
                        .addConverterFactory(GsonConverterFactory.create()) // bbs객체를 jsonStriong 문자열로 바꿔준다.
                        .build();

                // 실제 서비스 인터페이스 생성
                BbsService service = retrofit.create(BbsService.class);

                Call<Result> call = service.createBbs(bbs);
                // 서비스를 서브 thread 로 실행 - 비동기로 실행
                call.enqueue(new Callback<Result>() {
                    // 전송결과가 정상이면
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.e("Write","in ====== onResponse");
                        if(response.isSuccessful()){
                            // 응답 객체에서 body를 꺼내서 받는다.
                            Result result = response.body();
                            Log.e("Write", "결과처리 = " + result.result_status);
                            finish();
                        } else {
                            Log.e("Write", "not ok = " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e("Write", "error = " + t.getMessage());
                    }
                });
            }
        });
    }
}