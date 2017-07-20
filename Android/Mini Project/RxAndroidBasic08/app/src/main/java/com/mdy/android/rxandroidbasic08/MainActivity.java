package com.mdy.android.rxandroidbasic08;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mdy.android.rxandroidbasic08.domain.Data;
import com.mdy.android.rxandroidbasic08.domain.Info;
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

    // 데이터 정의
    List<Info> data = new ArrayList<>();
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        /*

        // 1. Retrofit 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // 2. 서비스 생성
        IWeather service = client.create(IWeather.class);

        // 3. 옵저버블 생성 ( addCallAdapterFactory(RxJava2CallAdapterFactory.create()) 이걸 해줬기 때문에 가능 )
        Observable<Data> observable = service.getData(SERVER_KEY, 1, 10, "서초");

        // 4. 발행 시작
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                // 구독 시작
                .subscribe(
                        data -> {
                            Row rows[] = data.getRealtimeWeatherStation().getRow();
                            for (Row row : rows) {
                                Log.i("Weather", "지역명 = " + row.getSTN_NM());
                                Log.i("Weather", "기온 = " + row.getSAWS_TA_AVG() + "도");
                                Log.i("Weather", "습도 = " + row.getSAWS_HD() + "%");
                                Log.i("Weather", "일조 = " + row.getSAWS_SHINE() + "hour");
                            }

                        }
                );

        */









    }

    private void setViews() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        adapter = new CustomAdapter(this, data);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    // doSearch
    public void doSearch(View view){
        String gu = editTextName.getText().toString();

        // 1. Retrofit 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // 2. 서비스 생성
        IWeather service = client.create(IWeather.class);

        // 3. 옵저버블 생성 ( addCallAdapterFactory(RxJava2CallAdapterFactory.create()) 이걸 해줬기 때문에 가능 )
        Observable<Data> observable = service.getData(SERVER_KEY, 1, 10, gu);

        // 4. 발행 시작
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // 구독 시작
                .subscribe(
                        item -> {
                            Row rows[] = item.getRealtimeWeatherStation().getRow();
                            for(Row row : rows){
                                Info info = new Info();
                                info.name = row.getNAME();
                                info.temperature = row.getSAWS_TA_AVG();
                                info.humidity = row.getSAWS_HD();
                                info.rain = row.getSAWS_RN_SUM();

                                data.add(info);
                            }
                            adapter.notifyDataSetChanged();


//                                Log.i("Weather", "지역명 = " + row.getSTN_NM());
//                                Log.i("Weather", "기온 = " + row.getSAWS_TA_AVG() + "도");
//                                Log.i("Weather", "습도 = " + row.getSAWS_HD() + "%");
//                                Log.i("Weather", "일조 = " + row.getSAWS_SHINE() + "hour");


                            }
                );
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


































