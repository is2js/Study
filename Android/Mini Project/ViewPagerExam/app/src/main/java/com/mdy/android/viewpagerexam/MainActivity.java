package com.mdy.android.viewpagerexam;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager1, viewPager2;
    TextView txtTitle1, txtTitle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        viewPager1.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        viewPager1.setCurrentItem(0);
    }




    private void setViews(){
        viewPager1 = (ViewPager) findViewById(R.id.viewPager1);
        viewPager2 = (ViewPager) findViewById(R.id.viewPager2);
        txtTitle1 = (TextView) findViewById(R.id.txtTitle1);
        txtTitle2 = (TextView) findViewById(R.id.txtTitle2);
    }
}
