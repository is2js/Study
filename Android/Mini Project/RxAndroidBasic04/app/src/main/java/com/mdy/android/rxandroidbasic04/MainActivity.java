package com.mdy.android.rxandroidbasic04;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button btnMap;
    private Button btnFlatMap;
    private Button btnZip;
    private RecyclerView recyclerView;

    // 데이터 정의
    List<String> data = new ArrayList<>();
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        adapter = new RecyclerAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emitData();
    }

    private void setViews() {
        btnMap = (Button) findViewById(R.id.btnMap);
        btnFlatMap = (Button) findViewById(R.id.btnFlatMap);
        btnZip = (Button) findViewById(R.id.btnZip);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }


    Observable<String> observableMap;
    Observable<Integer> observableFlatMap;
    Observable<String> observableZip;
    String[] months;
    public void emitData(){
        // 캘린더에서 1월 ~ 12월까지 텍스트를 추출
        DateFormatSymbols dfs = new DateFormatSymbols();
        months = dfs.getMonths();

        // 초당 1개씩 데이터 발행 - Map
        observableMap = Observable.create( emitter  -> {
            for(String month : months){
                emitter.onNext(month);
                Thread.sleep(1000);
            }
            emitter.onComplete();
        });

        // 초당 1개씩 데이터 발행 - FlatMap
        observableFlatMap = Observable.create( emitter  -> {
            for(int i=0 ; i<12 ; i++){
                emitter.onNext(i);
                Thread.sleep(1000);
            }
            emitter.onComplete();
        });

        // 초당 한번씩 출력하는 로직으로 변경 고민...
        observableZip = Observable.zip(
                Observable.just("MDY", "BeWhy"),
                Observable.just("Developer", "Rapper"),
                (item1, item2) -> "name : " + item1 + "  /  job : " + item2
        );
    }

    // doMap - onClick
    public void doMap(View view){
        observableMap
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter( item -> item.equals("May") ? false : true)
                .map(item -> "[ " + item + " ]")
                .subscribe(
                        item -> {
                            data.add(item);
                            adapter.notifyItemInserted(data.size()-1);
                        },
                        error -> Log.e("Error", error.getMessage()),
                        ( ) -> Log.i("Complete", "Successfully completed !")
                );
    }

    // doFlatMap - onClick
    public void doFlatMap(View view){
        observableFlatMap.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter( item -> item.equals("May") ? false : true)
                .flatMap(item -> Observable.fromArray(new String[] {
                        "name : " + months[item], "code : " + item
                }))
                .subscribe(
                        item -> {
                            data.add(item);
                            adapter.notifyItemInserted(data.size()-1);
                        },
                        error -> Log.e("Error", error.getMessage()),
                        ( ) -> Log.i("Complete", "Successfully completed !")
                );
    }

    // doZip - onClick
    public void doZip(View view){
        observableZip
                // .timeInterval(TimeUnit.SECONDS, Schedulers.computation())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            data.add(item + "");
                            adapter.notifyItemInserted(data.size()-1);
                        },
                        error -> Log.e("Error", error.getMessage()),
                        ( ) -> Log.i("Complete", "Successfully completed !")
                );
    }

}
