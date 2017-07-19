package com.mdy.android.animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnScale;
    private Button btnAlpha;
    private Button btnTrans;
    private Button btnRotate;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();
    }

    private void setViews() {
        btnScale = (Button) findViewById(R.id.btnScale);
        btnAlpha = (Button) findViewById(R.id.btnAlpha);
        btnTrans = (Button) findViewById(R.id.btnTrans);
        btnRotate = (Button) findViewById(R.id.btnRotate);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    private void setListeners(){
        btnTrans.setOnClickListener(this);
        btnScale.setOnClickListener(this);
        btnAlpha.setOnClickListener(this);
        btnRotate.setOnClickListener(this);

        // WindmillActivity로 이동
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 작성해준 애니메이션 설정파일 로드
        Animation animation = null;
        switch (view.getId()){
            case R.id.btnTrans :
                animation = AnimationUtils.loadAnimation(this, R.anim.trans);
                break;
            case R.id.btnRotate :
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
                break;
            case R.id.btnScale :
                animation = AnimationUtils.loadAnimation(this, R.anim.scale);
                break;
            case R.id.btnAlpha :
                animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
                break;
            case R.id.imageView :
                Intent intent = new Intent(MainActivity.this, WindmillActivity.class);
                startActivity(intent);
                break;
        }
        if(animation != null)
            imageView.startAnimation(animation);

    }
}
