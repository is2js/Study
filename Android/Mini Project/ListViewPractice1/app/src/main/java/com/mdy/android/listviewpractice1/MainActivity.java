package com.mdy.android.listviewpractice1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    String datas[] = {"선택하세요.", "ListView", "CustomList"};
    final int LIST = 1;
    final int CUSTOM = 2;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch(position){
                    case LIST:
                        intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        break;
                    case CUSTOM:
                        intent = new Intent(MainActivity.this, CustomListActivity.class);
                        startActivity(intent);
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // ListView 에 데이터 연결하기
        // 1. 데이터 정의 - datas 정의하였음.
        // 2. 아답터 생성  - 뷰에 넣을 데이터를 조작해주는게 아답터이다. 데이터의 변경 사항이 있어도 아답터가 처리를 해준다. (레이아웃 등)



        // 3. 뷰에 아답터 연결

    }
}