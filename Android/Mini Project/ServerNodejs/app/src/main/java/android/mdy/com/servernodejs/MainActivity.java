package android.mdy.com.servernodejs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

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

    private static final int REQ_CODE = 101;
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