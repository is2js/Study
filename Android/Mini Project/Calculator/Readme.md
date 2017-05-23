### 계산기 만들기 프로젝트2 (Calculator)
---
##### 학습내용들
- Log(로그)찍는 방법
- 계산하는 로직 (정규표현식)
- 코드 작성의 다양한 방법
---
### <Log(로그)찍는 방법>
```java

public static final String TAG = "TAG";


private void setPreview(int number){
        String current = txtPreview.getText().toString();
        Log.i(TAG, "로그의 내용=" + number);
        Log.d(TAG, "로그의 내용=" + number);
        Log.w(TAG, "로그의 내용=" + number);
        Log.e(TAG, "로그의 내용=" + number);
        txtPreview.setText(current + number);
    }

```

에러 아래는 실행이 안되기 때문에 로그는 에러가 난 것의 위쪽에 찍어야 한다.
로그 Class는 간단하게 구성되어 있기 때문에 많이 찍어도 부하가 걸리지 않는다.


#### [Logger 클래스 만들기]
 - util 패키지를 만들고, 그 안에 Logger라는 클래스를 만든다.
```java
package com.mdy.android.calculator.util;

import android.util.Log;

/**
 * Created by MDY on 2017-05-23.
 */

public class Logger {
    public static final boolean DEBUG = BuildConfig.DEBUG_MODE;

    public static void i(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.e(tag, msg);
        }
    }

}

```
---
### <계산하는 로직>
```java
private String calculate(String preview){
        String result = "";

        // 문자열을 쪼갠 후 우선순위에 따라 연산하시오.
        // 1. 문자열을 정규식으로 * / + - 을 이용해서 배열로 자른다
        String splitted[] = preview.split("(?<=[*/+-])|(?=[*/+-])");
        // 예) 123 * 45 + 67 / 89
        // 결과값 : splitted[0] = 123
        //          splitted[1] = *
        //          splitted[2] = 45
        //          splitted[3] = +
        //          splitted[4] = 67
        //          splitted[5] = /
        //          splitted[6] = 89

        // 배열을 중간에 삭제하기 위해서 컬렉션을 사용한다.
        ArrayList<String> saveList = new ArrayList<>();

        for(int i=0 ; i<splitted.length ; i++){
            saveList.add(splitted[i]);
        }

        // 이렇게 해도 된다. (향상된 for문)
        /*for(String temp : splitted){
            saveList.add(temp);
        }*/


//        int saveListSize = saveList.size(); // 컬렉션의 크기는 밖에서 선언해주는 것을 권장한다.


        // 반복문이 splitted 을 돌면서 * 와 / 만 먼저 연산해준다
        for(int i=0 ; i<saveList.size(); i++ ){
            String temp = saveList.get(i);
            int resultTemp = 0;
            if(temp.equals("*") || temp.equals("/")) {
                int before = Integer.parseInt(saveList.get(i-1));
                int after = Integer.parseInt(saveList.get(i+1));
                if(temp.equals("*"))
                    resultTemp = before * after;
                else
                    resultTemp = before / after;

                //결과값 저장
//                saveList.set(i, Integer.toString(resultTemp));
                saveList.set(i, resultTemp+"");
                //필요없는 배열 뒤, 앞 2개 삭제
                saveList.remove(i+1);
                saveList.remove(i-1);
                i--;
            }
        }

        // 반복문이 splitted 을 돌면서 + 와 - 만 먼저 연산해준다
        for(int i=0 ; i<saveList.size(); i++){
            String temp = saveList.get(i);
            int resultTemp = 0;
            if(temp.equals("+") || temp.equals("-")){
                int before = Integer.parseInt(saveList.get(i-1));
                int after = Integer.parseInt(saveList.get(i+1));
                if(temp.equals("+"))
                    resultTemp = before + after;
                else
                    resultTemp = before - after;

                // 결과값 저장
//                saveList.set( i, Integer.toString(resultTemp) );
                saveList.set(i, resultTemp+"");

                // 필요없는 배열 뒤, 앞 2개 삭제
                saveList.remove(i+1);
                saveList.remove(i-1);
                i--;
            }
        }
        return saveList.get(0);
    }
```

##### 코드 작성의 다양한 방법
```java
// 첫번째 방법
Button btnPlus;

btnPlus = (Button) findViewById(R.id.btnPlus);
btnPlus.setOnClickListener(this);     // implements View.OnClickListener

// 두번째 방법
Button btnPlus;

findViewById(R.id.btnPlus).setOnClickListener(this);
```

##### 문자열로 가져오기
```java
// 첫번째 방법
findViewById(R.id.btn0).setOnClickListener(this);
findViewById(R.id.btn1).setOnClickListener(this);
findViewById(R.id.btn2).setOnClickListener(this);
findViewById(R.id.btn3).setOnClickListener(this);
findViewById(R.id.btn4).setOnClickListener(this);
findViewById(R.id.btn5).setOnClickListener(this);
findViewById(R.id.btn6).setOnClickListener(this);
findViewById(R.id.btn7).setOnClickListener(this);
findViewById(R.id.btn8).setOnClickListener(this);
findViewById(R.id.btn9).setOnClickListener(this);

// 두번째 방법
for(int i=0; i < 10 ; i ++) {
    // 문자열로 id 가져오기
    int id = getResources().getIdentifier("btn" + i, "id", getPackageName());
    findViewById(id).setOnClickListener(this);
}
```
