package com.mdy.android.basicwidget;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener{

    // 1. 위젯 변수를 선언
    Button btnDog, btnPig, btnHorse;
    ToggleButton toggleButton;
    RadioGroup radioGroup;
    SeekBar seekBar;
    TextView seekCount;

    EditText editText;

    Button btnNum, btnText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. 위젯변수를 화면과 연결
        btnDog = (Button)findViewById(R.id.btnDog);
        btnPig = (Button)findViewById(R.id.btnPig);
        btnHorse = (Button)findViewById(R.id.btnHorse);
//        btnNum = (Button) findViewById(R.id.btnNum);
//        btnText = (Button) findViewById(R.id.btnText);

        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekCount = (TextView) findViewById(R.id.seekCount);

        // 3. 클릭리스너 연결   (implements View.OnClickListener)
            btnDog.setOnClickListener(this);  // this는 MainActivity
            btnPig.setOnClickListener(this);  // 해당 이벤트가 발생시 this(뭔가)를 호출해준다.
        btnHorse.setOnClickListener(this);

        toggleButton.setOnCheckedChangeListener(this); //체크드체인지리스너 <- ! 클릭 리스너가 아님.

        radioGroup.setOnCheckedChangeListener(this);

        seekBar.setOnSeekBarChangeListener(listener);

    }

    @Override
    public void onClick(View v) {  // 시스템의 이벤트 리스너를 통해 호출된다.
        switch(v.getId()){
            case R.id.btnDog:
                Toast.makeText(this, "멍멍~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnPig:
                Toast.makeText(this, "꿀꿀~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnHorse:
                Toast.makeText(this, "히잉~", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.toggleButton:
                if(isChecked){
                    Toast.makeText(this, "스위치가 켜졌습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "꺼졌습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (group.getId() == R.id.radioGroup) {
            switch (checkedId) {
                case R.id.radioRed:
                    Toast.makeText(this, "빨간불~~.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radioGreen:
                    Toast.makeText(this, "초록불~~", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radioBlue:
                    Toast.makeText(this, "파란불~~", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekCount.setText(progress + "");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
//            Toast.makeText(this, "터치를 ").show();

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
