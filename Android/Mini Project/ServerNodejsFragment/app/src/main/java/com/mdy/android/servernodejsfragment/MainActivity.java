package com.mdy.android.servernodejsfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

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
    private List<Bbs> data = new ArrayList<>();
    ListFragment listFragment;
    DetailFragment detailFragment;

    // loader를 실행한 위치를 알려주기 위해서 상수 선언
    private static final int FROM_CREATE = 1;   // onCreate에서 loader호출할 경우
    private static final int FROM_DETAIL = 2;   // DetailFragment에서 loader호출할 경우


    // List<Bbs> data를 private으로 해놨기 때문에 data를 가져오는 함수를 getData로 만들었고,
    // onAttach()로 MainActivity를 넘길 경우, ListFragment에서 사용할 수 있다.
    public List<Bbs> getData(){
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loader(FROM_CREATE);

    }

    private void setFragment(){
        listFragment = new ListFragment();
        detailFragment = new DetailFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, listFragment)      // 첫번째 Fragment는 BackStack하면 앱이 종료되어야 하기 때문에 .addToBackStack()를 해주지 않는다.
                .commit();
    }

    private void addFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("detailFragment")
                .commit();
    }

    // 제일 위에 있는 Fragment를 꺼내는 메소드
    private void popFragment(){
        onBackPressed();
    }

    private void loader(int from) {
        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(IBbs.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. 서비스 연결
        IBbs myServer = client.create(IBbs.class);

        // 3. 서비스의 특정 함수 호출 -> Observable 생성
        Observable<ResponseBody> observable = myServer.read();

        // 4. subscribe 등록
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseBody -> {
                            // 1. 데이터를 꺼내고
                            String jsonString = responseBody.string();
                            Gson gson = new Gson();
                            Bbs data[] = gson.fromJson(jsonString, Bbs[].class);
                            // 2. 데이터를 아답터에 세팅하고
                            for(Bbs bbs : data){
                                MainActivity.this.data.add(bbs);
                            }
                            // 3. 아답터 갱신
                            // 호출된 곳에 따라 처리가 달라진다.
                            switch (from){
                                case FROM_CREATE:   // onCreate에서 loader호출할 경우
                                    setFragment();
                                    break;
                                case FROM_DETAIL:   // DetailFragment에서 loader호출할 경우
                                    listFragment.refresh();
                                    break;
                            }
                        }
                );
    }

    public void goDetail() {
        addFragment(detailFragment);
    }

    public void goList() {
        popFragment();  // 제일 위에 있는 Fragment를 꺼낸다.
        data.clear();
        loader(FROM_DETAIL);
    }
}