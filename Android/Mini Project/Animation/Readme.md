# 안드로이드 Animation
#### 안드로이드 Animation에 대해 알아본다.
  - View Animation ( `Trans`, `Rotate`, `Scale`, `Alpha` )
  - Property Animation
<br>
<Br>

## View Animation  `Trans`, `Rotate`, `Scale`, `Alpha`
- #### `res` - `Android resource directory` - 폴더이름: `anim`으로 생성 - 그 안에 `Animation resource file` 생성
<br>

#### 1. Trans (이동)
- trans.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<translate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromXDelta="0"
    android:fromYDelta="0"
    android:toXDelta="100"
    android:toYDelta="300"
    android:duration="3000">
</translate>
```
- `duration`은 시간 설정

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Animation/graphics/ViewAnimation_trans.png' width='210' height='350' />
<br>

#### 2. Rotate (회전)
- rotate.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<rotate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromDegrees="0"
    android:toDegrees="720"
    android:pivotX="50%"
    android:pivotY="50%"
    android:duration="3000">
</rotate>
```
- `pivotX`, `pivotY` 값을 **50%** 로 줘야 제자리를 축으로 회전을 한다.

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Animation/graphics/ViewAnimation_rotate.png' width='210' height='350' />
<br>

#### 3. Scale (크기 조절)
- scale.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<scale
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromXScale="1.0"
    android:fromYScale="1.0"
    android:toXScale="2.0"
    android:toYScale="2.0"
    android:pivotX="50%"
    android:pivotY="50%"
    android:fillAfter="true"
    android:duration="3000">
</scale>
```
- `toXScale="2.0"`, `toYScale="2.0"` 으로 하면 가로, 세로가 2배로 확대된다.
- `pivotX = "50%"`, `pivotY = "50%"` 으로 해줘야 제자리에서 확대가 된다.
- `fillAfter = "true"` - 종료 후에 마지막 형태를 고정

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Animation/graphics/ViewAnimation_scale.png' width='210' height='350' />
<br>

#### 4. Alpha (투명도 조절)
- alpha.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<alpha
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromAlpha="1.0"
    android:toAlpha="0.0"
    android:fillAfter="true"
    android:duration="3000">
</alpha>
```
- `fromAlpha = "0.0"` - 투명해져서 결국에는 완전히 안보인다.

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Animation/graphics/ViewAnimation_alpha.png' width='210' height='350' />
<br>


#### 버튼 클릭에 따른 View Animation 실행
```java
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
```

<br>
<br>
## View Animation 응용
- #### `res` - `Android resource directory` - 폴더이름: `anim`으로 생성 - 그 안에 `Animation resource file` 생성
<br>
- rotate_windmill.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<rotate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromDegrees="0"
    android:toDegrees="1440"
    android:pivotX="50%"
    android:pivotY="50%"
    android:duration="10000">
</rotate>
```

- trans_red.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<translate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromXDelta="0"
    android:fromYDelta="0"
    android:toXDelta="-50%"
    android:toYDelta="-50%"
    android:fillAfter="true"
    android:duration="3000">
</translate>
```

- trans_green.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<translate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromXDelta="0"
    android:fromYDelta="0"
    android:toXDelta="50%"
    android:toYDelta="-50%"
    android:fillAfter="true"
    android:duration="3000">
</translate>
```

- trans_blue.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<translate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromXDelta="0"
    android:fromYDelta="0"
    android:toXDelta="-50%"
    android:toYDelta="50%"
    android:fillAfter="true"
    android:duration="3000">
</translate>
```

- trans_yellow.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<translate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromXDelta="0"
    android:fromYDelta="0"
    android:toXDelta="50%"
    android:toYDelta="50%"
    android:fillAfter="true"
    android:duration="3000">
</translate>
```
<br>

#### 버튼 클릭에 따른 View Animation 실행
```java
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
    }
}
```
<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Animation/graphics/ViewAnimation2_init.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Animation/graphics/ViewAnimation2_ing.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Animation/graphics/ViewAnimation2_finish.png' width='210' height='350' />
- [참고사항] 애니메이션을 마친후, `4가지 색깔이 가득찬 Layout`에 버튼을 눌러 리스너가 동작하게 하려면 `4가지 색깔이 가득찬 Layout`의 속성 중 `clickable` 을 체크해줘야 한다.
<br>
<br>
<br>


## Property Animation
- #### Property Animation은 언제 사용하는가?
  - `View Animation`을 해서 버튼이 이동을 하면 버튼에 리스너가 달려있을 경우, 버튼이 이동하기 전 위치를 클릭해야 동작한다. 이는 버튼에 리스너를 달아놓은 목적을 만족시키지 못한다. 이 때 사용하는 것이 `Property Animation`이다. `Property Animation`을 사용하면 버튼이 이동한 후에 해당 위치에서 버튼을 클릭해도 버튼의 리스너가 동작을 한다.
<br>
- `Property Animation`은 `View Animation`과는 다르게 xml로 만들어 줄 것이 없고, 소스코드로 동작이 가능하다.
<br>

### 예제코드
```
@Override
public void onClick(View view) {
    AnimatorSet aniSet = new AnimatorSet();
    ObjectAnimator transY = null;
    ObjectAnimator transX = null;
    ObjectAnimator rotate = null;

    switch (view.getId()){
        case R.id.btnProp : // Prop 버튼을 클릭
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
        case R.id.btnRed :  // 움직인 빨간색 Property Animation 버튼을 클릭
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
```
<br>

### 동작화면
- Prop 버튼을 누르면 `빨간색 Property Animation` 버튼이 Rotate하면서 이동을 한다.
- 이동을 마친후, Prop 버튼이 아닌 `빨간색 Property Animation` 버튼을 클릭하면 Rotate를 하면서 다시 원위치로 이동을 한다.
<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Animation/graphics/PropertyAnimation_init.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Animation/graphics/PropertyAnimation_ing.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Animation/graphics/PropertyAnimation_finish.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Animation/graphics/PropertyAnimation_reverse.png' width='210' height='350' />
