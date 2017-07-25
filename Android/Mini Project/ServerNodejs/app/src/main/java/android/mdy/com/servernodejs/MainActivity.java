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
                startActivity(intent);
            }
        });
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

                            Log.e("로그 확인", "============================= test 1");
                            Bbs data[] = gson.fromJson(jsonString, Bbs[].class);

                            Log.e("로그 확인", "============================= test 2");
                            if(data == null){
                                Log.e("데이터", "data가 null 입니다.");
                            } else {
                                Log.e("데이터", "============= 데이터 넘어옴. ==============");
                            }

                            Log.e("로그 확인", "============================= test 3");
                            // 2. 아답터에 세팅하고
                            for(Bbs bbs : data){
                                Log.e("로그 확인", "============================= test 4");
                                this.data.add(bbs);
                                Log.e("로그 확인", "============================= test 5");
    //                            Log.e("Data", "title = " + bbs.title);
                            }
                            // 3. 아답터 갱신
                            Log.e("로그 확인", "============================= test 6");
                            adapter.notifyDataSetChanged();
                        }
                );
    }
}