### BasicWidget

#####
- 모든 위젯은 뷰를 상속한다.
- **[1] Button**
  **[2] ToggleButton**
  **[3] RadioGroup**
  **[4] Rad**
---
##### <버튼에 한글 입력하는 방법>
- res - values - strings.xml 에서 수정
```xml
<resources>
    <string name="app_name">BasicWidget</string>

    <!-- 주석을 달고 -->
    <string name="word_dog">강아지</string>
    <string name="word_pig">돼지</string>
    <string name="word_horse">말</string>
</resources>
```
- text에 [@string/버튼id값]  입력
---

##### [1] Button
- 1. 위젯변수를 선언
```java
     Button btnDog, btnPig, btnHorse;
```
- 2. 위젯변수를 화면과 연결
```java
     btnDog = (Button)findViewById(R.id.btnDog);
     btnPig = (Button)findViewById(R.id.btnPig);
     btnHorse = (Button)findViewById(R.id.btnHorse);
```
- 3. 클릭리스너 연결   (implements View.OnClickListener)
```java
     btnDog.setOnClickListener(this);  // this는 MainActivity
     btnPig.setOnClickListener(this);  // 해당 이벤트가 발생시 this(뭔가)를 호출해준다.
     btnHorse.setOnClickListener(this);
```
- 4. onClick 메소드 작성
```java
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
```
---
##### [2] ToggleButton
- 1. 위젯 변수를 선언
```java
     ToggleButton toggleButton;
```
- 2. 위젯변수를 화면과 연결
```java
     toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
```
- 3. CheckedChangeListener 연결   (implements CompoundButton.OnCheckedChangeListener)
```java
     toggleButton.setOnCheckedChangeListener(this); //체크드체인지리스너 <- ! 클릭 리스너가 아님.
```
- 4. onCheckedChanged 메소드 작성
```java
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
```
---

##### [3] RadioGroup
- 1. 위젯 변수를 선언
```java
     RadioGroup radioGroup;
```
- 2. 위젯변수를 화면과 연결
```java
     radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
```
- 3. CheckedChangeListener 연결   (implements RadioGroup.OnCheckedChangeListener)
```java
     radioGroup.setOnCheckedChangeListener(this);
```
- 4. onCheckedChanged 메소드 작성
```java
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
```
---
