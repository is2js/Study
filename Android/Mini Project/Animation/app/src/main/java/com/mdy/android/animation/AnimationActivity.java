package com.mdy.android.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnProp;
    private Button btnRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        setViews();
        setListeners();
    }

    private void setViews() {
        btnProp = (Button) findViewById(R.id.btnProp);
        btnRed = (Button) findViewById(R.id.btnRed);
    }

    private void setListeners() {
        btnProp.setOnClickListener(this);
        btnRed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AnimatorSet aniSet = new AnimatorSet();
        ObjectAnimator transY = null;
        ObjectAnimator transX = null;
        ObjectAnimator rotate = null;

        switch (view.getId()){
            case R.id.btnProp :
                transY = ObjectAnimator.ofFloat(
                        btnRed,             // 움직일 대상
                        "translationY",     // 애니메이션 속성
                        800                 // 속성값 (이동할 값)
                );
                transX = ObjectAnimator.ofFloat(
                        btnRed,             // 움직일 대상
                        "translationX",     // 애니메이션 속성
                        600                 // 속성값 (이동할 값)
                );
                rotate = ObjectAnimator.ofFloat(
                        btnRed,             // 움직일 대상
                        "rotation",     // 애니메이션 속성
                        14400                 // 속성값
                );
                // 애니메이터 셋을 구성해서 실행한다.
                aniSet.playTogether(transX, transY, rotate);    // 개수의 제한이 없음.
                aniSet.setDuration(3000);               // 애니메이터 셋의 실행 시간
                aniSet.setInterpolator(new AccelerateDecelerateInterpolator()); // 처음엔 빨리, 점점 느리게 하는 애니메이션 도구
                aniSet.start();
                break;
            case R.id.btnRed :
                // 돌아오는 것
                transY = ObjectAnimator.ofFloat(
                        btnRed,             // 움직일 대상
                        "translationY",     // 애니메이션 속성
                        0                 // 속성값 (이동할 값)
                );
                transX = ObjectAnimator.ofFloat(
                        btnRed,             // 움직일 대상
                        "translationX",     // 애니메이션 속성
                        0                 // 속성값 (이동할 값)
                );
                rotate = ObjectAnimator.ofFloat(
                        btnRed,             // 움직일 대상
                        "rotation",     // 애니메이션 속성
                        0                 // 속성값
                );
                // 애니메이터 셋을 구성해서 실행한다.
                aniSet.playTogether(transX, transY, rotate);    // 개수의 제한이 없음.
                aniSet.setDuration(3000);               // 애니메이터 셋의 실행 시간
                aniSet.setInterpolator(new AccelerateDecelerateInterpolator()); // 처음엔 빨리, 점점 느리게 하는 애니메이션 도구
                aniSet.start();
                break;
        }
    }
}
