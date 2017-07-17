package com.mdy.android.studio3begin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button btnNoStream;
    private Button btnStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        String objectArray[] = {"A", "B", "C", "D123", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        Stream<String> data = Arrays.stream(objectArray);

        btnNoStream.setOnClickListener( view -> printOneWord(objectArray) );
        btnStream.setOnClickListener( view -> printStream(data) );

    }

    private void printOneWord(String arr[]) {
        for (String item : arr) {
            if (item.length() == 1) {
                System.out.println(item);
            }
        }
    }

    private void printStream(Stream<String> data){
        data
                .filter( item -> item.length() == 1)
                .forEach( item -> System.out.println(item) );
    }


    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        btnNoStream = (Button) findViewById(R.id.btnNoStream);
        btnStream = (Button) findViewById(R.id.btnStream);
    }
}

