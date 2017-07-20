package com.mdy.android.rxandroidbasic06;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnRandom;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        // 발행자가 된다.
        RxTextView.textChangeEvents((TextView) findViewById(R.id.editText))
                .subscribe(
                        item -> Log.i("WORD", "String = " + item.text().toString())
                );

        RxView.clicks(btnRandom)
                .map(event -> new Random().nextInt())
                .subscribe( // 옵저버를 달아준다.
                        number -> textView.setText("random number = " + number)
                );
    }

    private void initView() {
        btnRandom = (Button) findViewById(R.id.btnRandom);
        textView = (TextView) findViewById(R.id.textView);
    }
}
