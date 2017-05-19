package com.mdy.android.basiclayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnRelative, btnLinear, btnGrid, btnSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRelative = (Button) findViewById(R.id.btnRelative);
        btnLinear = (Button) findViewById(R.id.btnLinear);
        btnGrid = (Button) findViewById(R.id.btnGrid);
        btnSpinner = (Button) findViewById(R.id.btnSpinner);

        btnRelative.setOnClickListener(this);

        btnRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // 이거는 implement 를 안해도 된다.
            }
        });
        btnLinear.setOnClickListener(this);
        btnGrid.setOnClickListener(this);
        btnSpinner.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRelative:
                Intent intent1 = new Intent(this, RelativeActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnLinear:
                Intent intent2 = new Intent(this, LinearActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnGrid:
                Intent intent3 = new Intent(this, GridActivity.class);
                startActivity(intent3);
                break;
            case R.id.btnSpinner:
                Intent intent4 = new Intent(this, Spinner.class);
                startActivity(intent4);
                break;
        }
    }
}