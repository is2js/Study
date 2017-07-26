package com.mdy.android.airbnbsearch;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    Button btnCheckIn, btnCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnCheckIn = (Button) findViewById(R.id.btnCheckIn);
        String btnCheckInText = getString(R.string.hint_start_date)
                + "<br> <font color='#fd5a5f'>" + getString(R.string.hint_select_Date) + "</font>";
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            btnCheckIn.setText(Html.fromHtml(btnCheckInText));
        } else {
            btnCheckIn.setText(Html.fromHtml(btnCheckInText, Html.FROM_HTML_MODE_LEGACY));
        }

//        btnCheckIn.setText(Html.fromHtml(btnCheckInText));


        btnCheckOut = (Button) findViewById(R.id.btnCheckOut);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


}
