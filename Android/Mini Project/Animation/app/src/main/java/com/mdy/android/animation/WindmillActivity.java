package com.mdy.android.animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;


public class WindmillActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout windmill;

    private Button btnWindmill;
    private Button btnRed;
    private Button btnGreen;
    private Button btnBlue;
    private Button btnYellow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windmill);
        setViews();
        setListeners();
    }

    private void setViews() {
        btnWindmill = (Button) findViewById(R.id.btnWindmill);
        btnRed = (Button) findViewById(R.id.btnRed);
        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnBlue = (Button) findViewById(R.id.btnBlue);
        btnYellow = (Button) findViewById(R.id.btnYellow);
        windmill = (RelativeLayout) findViewById(R.id.windmill);
    }

    private void setListeners() {
        btnWindmill.setOnClickListener(this);
        windmill.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnWindmill:
                Animation transRed = AnimationUtils.loadAnimation(this, R.anim.trans_red);
                Animation transGreen = AnimationUtils.loadAnimation(this, R.anim.trans_green);
                Animation transBlue = AnimationUtils.loadAnimation(this, R.anim.trans_blue);
                Animation transYellow = AnimationUtils.loadAnimation(this, R.anim.trans_yellow);

                Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_windmill);

                btnRed.startAnimation(transRed);
                btnGreen.startAnimation(transGreen);
                btnBlue.startAnimation(transBlue);
                btnYellow.startAnimation(transYellow);

                windmill.startAnimation(rotate);
                break;
            case R.id.windmill :
                Intent intent = new Intent(WindmillActivity.this, AnimationActivity.class);
                startActivity(intent);
                break;
        }
    }
}
