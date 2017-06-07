# HowToStudy
학습 방법들

## Log를 이용한 방법
- () 가로가 있는 모든 곳(명령문인 메소드들) 에 Log를 찍어서 인자값들을 확인해본다.
```java
  public Counter(int index, Handler handler){
      Log.i("Counter", "handler======================"+handler+", index="+index);
      this.textViewIndex = index;
      this.handler = handler;
  }
```
- 그러면 아래와 같이 Log가 찍힌다.
```java
06-07 10:14:32.713 2992-2992/com.mdy.android.multiplecounter I/Counter: handler======================Handler (com.mdy.android.multiplecounter.MainActivity$1) {1e2ce1d}, index=0
06-07 10:14:32.713 2992-2992/com.mdy.android.multiplecounter I/Counter: handler======================Handler (com.mdy.android.multiplecounter.MainActivity$1) {1e2ce1d}, index=1
06-07 10:14:32.713 2992-2992/com.mdy.android.multiplecounter I/Counter: handler======================Handler (com.mdy.android.multiplecounter.MainActivity$1) {1e2ce1d}, index=2
06-07 10:14:32.713 2992-2992/com.mdy.android.multiplecounter I/Counter: handler======================Handler (com.mdy.android.multiplecounter.MainActivity$1) {1e2ce1d}, index=3
```
- 이것을 토대로 공부하면 된다.
