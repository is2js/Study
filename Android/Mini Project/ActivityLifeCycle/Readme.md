## 2017.05.25.(목)

### Activity Life Cycle (액티비티 생명 주기)
![ActivityLifeCycle](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ActivityLifeCycle/graphics/ActivityLifeCycle(image).png)
- 현재 하나의 액티비티가 보여지고 있을때, 다른 액티비티를 실행시키더라도 기존에 실행되고 있던 액티비티가 조금이라도 화면에 보여지면 기존에 실행되고 있었던 액티비티의 Life Cycle은 onPause()가 된다.
 그리고 예를 들어 새로 실행하는 액티비티의 속성이 투명한 속성이어서 기존에 실행되던 액티비티가 계속 보이게 된다면 기존에 실행되면 액티비티의 생명주기는 계속 onPause()인 것이다.

- onStart(), onResume(), onPause(), onStop(), onRestart(), onDestroy(), onCreate() Activity LifeCycle메소드들을 Override해서 Log를 찍어볼 수 있다.

> <**Example**>
> MainActivity 클래스 안에 메소드들을 Override하면 된다.
  ```java
  @Override
  protected void onStart() {
      super.onStart();
      Log.d(TAG, "================ onStart");
  }
  @Override
  protected void onResume() {
      super.onResume();
      Log.d(TAG, "================ onResume");
  }
  ```




---
### Activity에 투명한 속성 주기
> 보통 디자인에서는 투명한 속성을 Transparent 라 하고, 소트프웨어에서는 Translucent라고 한다.
##### 1. AndroidManifest.xml 설정
```xml
<application
  <activity
    android:name=".TransparentActivity"                     // 이 2줄을
    android:theme="@style/Theme.AppCompat.Translucent">     // 추가해준다.
  </activity>
</application>
```
##### 2. res - values - styles.xml 설정
```xml
<resources>
    <style name="Theme.AppCompat.Translucent">
        <item name="android:windowBackground">@color/translucent</item>
        <item name="android:windowIsTranslucent">true</item>
    </style>
</resources>
```
##### 3. res - values - colors.xml 설정
```xml
<resources>
    <color name="colorPrimary">#3F51B5</color>
    <color name="colorPrimaryDark">#303F9F</color>
    <color name="colorAccent">#FF4081</color>

    <color name="translucent">#88000000</color> // styles.xml에서 'translucent'이름을 쓸 수 있게 설정해준다. (제일 앞의 숫자 2개가 투명도를 조절한다.)
</resources>
```
