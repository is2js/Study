package com.mdy.android.airbnbsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private Button btnCheckIn, btnCheckOut;
    private CalendarView calendarView;

    private Search search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

    private static final int CHECK_IN = 10;
    private static final int CHECK_OUT = 20;
    private int checkStatus = CHECK_IN;


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
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }
}
