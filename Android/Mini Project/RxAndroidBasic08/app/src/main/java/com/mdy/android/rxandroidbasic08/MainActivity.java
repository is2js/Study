package com.mdy.android.rxandroidbasic08;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mdy.android.rxandroidbasic08.domain.Data;
import com.mdy.android.rxandroidbasic08.domain.Row;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {

    // http://openAPI.seoul.go.kr:8088/(인증키)/json/RealtimeWeatherStation/1/5/중구
    // 인증키 : 5043654c666d647935386866617047

    public static final String SERVER = "http://openAPI.seoul.go.kr:8088/";
    public static final String SERVER_KEY = "5043654c666d647935386866617047";
    private EditText editTextName;
    private Button btnSearch;
    private RecyclerView recycler;

    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();
    }

    private void setViews() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        adapter = new CustomAdapter(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    // Search
    public void setListeners() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. Retrofit 생성
                Retrofit client = new Retrofit.Builder()
                        .baseUrl(SERVER)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

                // 2. 서비스 생성
                IWeather service = client.create(IWeather.class);

                // 3. 옵저버블 생성 ( addCallAdapterFactory(RxJava2CallAdapterFactory.create()) 이걸 해줬기 때문에 가능 )
//                Observable<Data> observable = service.getData(SERVER_KEY, 1, 10, editTextName.getText().toString() + "");
                Observable<Data> observable = service.getData(SERVER_KEY, 1, 10, "서초");

                // 4. 발행 시작
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        // 구독 시작
                        .subscribe(
                                item -> {
                                    // 예외 처리
                                    if (item.getRealtimeWeatherStation() == null) {
                                        Toast.makeText(getApplicationContext(), "데이터 형식이 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                                        return;     // return; 을 해줘야 null 일때 아래 코드가 실행되지 않는다.
                                    }

                                    Toast.makeText(MainActivity.this, item.getRealtimeWeatherStation().getRESULT().getMESSAGE(), Toast.LENGTH_SHORT).show();
                                    List<String> content = new ArrayList<>();
                                    Row rows[] = item.getRealtimeWeatherStation().getRow();

                                    for (Row row : rows) {
                                        content.add(row.getSAWS_OBS_TM());  // 측정일시
                                        content.add(row.getSTN_NM());       // 지점명
                                        content.add(row.getSTN_ID());       // 지점코드
                                        content.add(row.getSAWS_TA_AVG());  // 기온
                                        content.add(row.getSAWS_HD());      // 습도
                                        content.add(row.getCODE());         // 풍향1
                                        content.add(row.getNAME());         // 풍향2
                                        content.add(row.getSAWS_WS_AVG());  // 풍속
                                        content.add(row.getSAWS_RN_SUM());  // 강수
                                        content.add(row.getSAWS_SOLAR());   // 일사
                                        content.add(row.getSAWS_SHINE());   // 일조
                                    }
                                    adapter.setData(content);
                                    adapter.notifyDataSetChanged();
                                }
                        );
            }
        });
    }
}

interface IWeather {
    // @GET("인증키/xml/RealtimeWeatherStation/시작인덱스/가져올개수/구이름")
    @GET("{key}/json/RealtimeWeatherStation/{start}/{count}/{name}")
    Observable<Data> getData(@Path("key") String server_key
            , @Path("start") int begin_index
            , @Path("count") int offset
            , @Path("name") String gu);
}