package com.mdy.android.airbnbsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private Button btnCheckIn, btnCheckOut;
    private CalendarView calendarView;


    private Search search;
    private TextView txtGuest;
    private Button btnGuestMinus;
    private Button btnGuestPlus;

    private static final int CHECK_IN = 10;
    private static final int CHECK_OUT = 20;
    private int checkStatus = CHECK_IN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data.data = new ArrayList<>();

        setViews();
        setCalendarButtonText();
        setListeners();
        init();





    }



    private void init() {
        search = new Search();
    }

    private void setViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        btnCheckIn = (Button) findViewById(R.id.btnCheckIn);
        btnCheckOut = (Button) findViewById(R.id.btnCheckOut);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        txtGuest = (TextView) findViewById(R.id.txtGuest);
        btnGuestMinus = (Button) findViewById(R.id.btnGuestMinus);
        btnGuestPlus = (Button) findViewById(R.id.btnGuestPlus);
    }

    private void setListeners(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.i("Calendar","year:"+year+", month:"+month+", dayOfMonth:"+dayOfMonth);
                String theDay = String.format("%d-%02d-%02d", year, month, dayOfMonth);
//                String theDay = year + "-" + month + "-" + dayOfMonth;
                switch (checkStatus){
                    case CHECK_IN :
                        search.checkInDate = theDay;
                        setButtonText(btnCheckIn, getString(R.string.hint_start_date), search.checkInDate);
                        break;
                    case CHECK_OUT :
                        search.checkOutDate = theDay;
                        setButtonText(btnCheckOut, getString(R.string.hint_end_date), search.checkOutDate);
                        break;
                }
            }
        });

        btnCheckIn.setOnClickListener(this);
        btnCheckOut.setOnClickListener(this);
        fab.setOnClickListener(this);
        btnGuestMinus.setOnClickListener(this);
        btnGuestPlus.setOnClickListener(this);
    }

    private void setCalendarButtonText(){
        // 버튼에 다양한 색깔의 폰트 적용하기
        // 위젯의 android:textAllCaps="false" 적용 필요
        setButtonText(btnCheckIn, getString(R.string.hint_start_date), getString(R.string.hint_select_Date));
        setButtonText(btnCheckOut, getString(R.string.hint_end_date), "-");
    }

    private void setButtonText(Button btn, String upText, String downText){
        String inText = "<font color='#888888'>" + upText
                + "</font> <br> <font color=\"#fd5a5f\">" + downText + "</font>";
        StringUtil.setHtmlText(btn, inText);
    }


    private void search(){
        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(ISearch.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. 서비스 연결
        ISearch myServer = client.create(ISearch.class);

        // 3. 서비스의 특정 함수 호출 -> Observable 생성
        Observable<ResponseBody> observable = myServer.get(
                search.checkInDate,
                search.checkOutDate,
                search.getGuests(),
                -1,
                -1,
                -1,
                -1
        );

        // 4. subscribe 등록
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseBody -> {
                            // 1. 데이터를 꺼내고
                            String jsonString = responseBody.string();

                            Log.e("RESULT",""+jsonString);


                            Gson gson = new Gson();

                            Reservation data[] = gson.fromJson(jsonString, Reservation[].class);

                            // 2. 아답터에 세팅하고
                            if(Data.data != null){
                                Data.data.clear();
                            }

                            for(Reservation reservation : data){
                                Data.data.add(reservation);
                            }


                            Intent intent = new Intent(MainActivity.this, ListActivity.class);
                            startActivity(intent);
                        }
                );
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCheckIn:
                checkStatus = CHECK_IN;
                setButtonText(btnCheckIn, getString(R.string.hint_start_date), getString(R.string.hint_select_Date));
                setButtonText(btnCheckOut, getString(R.string.hint_end_date), search.checkOutDate);
                break;
            case R.id.btnCheckOut:
                checkStatus = CHECK_OUT;
                setButtonText(btnCheckOut, getString(R.string.hint_end_date), getString(R.string.hint_select_Date));
                setButtonText(btnCheckIn, getString(R.string.hint_start_date), search.checkInDate);
                break;
            case R.id.fab: // 검색 전송
                search();
                break;
            case R.id.btnGuestMinus:
                search.setGuests(search.getGuests()-1);
                txtGuest.setText(search.getGuests() + "");
                break;
            case R.id.btnGuestPlus:
                search.setGuests(search.getGuests()+1);
                txtGuest.setText(search.getGuests() + "");
                break;
        }
    }
}
