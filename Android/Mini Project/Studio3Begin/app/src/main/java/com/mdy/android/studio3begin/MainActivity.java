package com.mdy.android.studio3begin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private android.widget.TextView TextView;
    private Button button;
    private Button btnStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        String objectArray[] = {"A", "B", "C", "D123", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        button.setOnClickListener(view -> printOneWord(objectArray));
        /*button.setOnClickListener( view -> {
            LambdaFunction arg_original = new LambdaFunction() {
                @Override
                public int squareParameter(int p) {
                    return p * p;
                }
            };
            calc(arg_original);
        });

        button.setOnClickListener((view) -> {
            LambdaFunction arg = p -> p * p;
            calc(arg);
        });*/

        Stream<String> data = Arrays.stream(objectArray);

        button.setOnClickListener( view -> printOneWord(objectArray) );
        btnStream.setOnClickListener( view -> printStream(data) );


    }

    public void calc(LambdaFunction param) {
        int result = param.squareParameter(7);
        System.out.println(result);
    }

    @FunctionalInterface
    interface LambdaFunction {
        public abstract int squareParameter(int number);
    }

    private void initView() {
        TextView = (TextView) findViewById(R.id.TextView);
        button = (Button) findViewById(R.id.button);
        btnStream = (Button) findViewById(R.id.btnStream);
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


}

