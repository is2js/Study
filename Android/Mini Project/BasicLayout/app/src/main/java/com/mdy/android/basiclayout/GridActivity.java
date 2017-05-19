package com.mdy.android.basiclayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        // olumnCount = 3으로
        // layout_columnSpan
        // layout_columnWeight
    }
}
