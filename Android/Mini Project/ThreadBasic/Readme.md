# ThreadBasic
Thread에 대해서 알아본다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ThreadBasic/app/src/main/java/com/mdy/android/threadbasic/MainActivity.java)**

## Thread에 관한 설명
- 소제목1에 대한 설명
* 비동기 = 스레드


OS가 여러개의 프로세스를 가지듯이
(OS가 여러개의 프로그램을 돌릴때 각 프로그램마다 각각 프로세스를 만들어 실행시킨다.)


앱 하나가 여러개의 프로세스를 가질 수 있다. 그게 스레드이다.

브라우저는 기본적으로 탭 하나하나 스레드가 생성된다.
네이버를 실행시키면 100개 정도의 스레드가 한번에 실행되서
이미지도 가져오고, 네트워크도 갖다오고 등등
스레드는 로직이 종료되면 자기 생명주기를 다하고 사라진다.

프로세스는 OS 자체가 가지는 명령어 실행단위

스레드는 그 명령어 실행단위가 곧 앱 하나당 하나의 프로세스가 생성되는 것이다.

스레드는 앱 하나에 여러개의 명령어 실행단위가 있는 것과 같다.



앱 외부의 실행단위가 프로세스이고,
앱 내부의 실행단위가 스레드.


<큰 차이>
프로세스는 메모리 공유가 안되는데
스레드는 메모리 공유가 된다.
- 이유는 안드로이드와 상관없이 OS 보안때문에 설계된 것이다.



```java

```
## Thread 생성 및 실행
#### 여러가지 방법들 (4가지)
- 1번째 방법
```java
// 1.1 Thread 생성 (방법1)
Thread thread = new Thread(){
    @Override
    public void run() {
        Log.i("Thread Test", "Hello Thread!");
    }
};

// 1.2 Thread 실행
thread.start(); // run() 함수를 실행시켜준다.
```


- 2번째 방법
```java
// 2.1. Thread 생성 (방법2)       // Thread가 implement Runnable을 하고 있기 때문에(인터페이스 사용)
Runnable thread2 = new Runnable() {
    @Override
    public void run() {
        Log.i("Thread Test", "Hello Runnable!");
    }
};

// 2.2. Threade 실행
new Thread(thread2).start();
```




- 3번째 방법
```java
// 3.1 Thread 생성 (방법3)
class CustomThread extends Thread {
  @Override
  public void run() {
      Log.i("Thread Test","Hello Custom Thread!");
  }
}

// 3.2 Thread 실행 3
CustomThread thread3 = new CustomThread();
thread3.start();
```




- 4번째 방법 (Thread를 많이 사용할 경우 사용하는 방법)
```java
// 4.1 Runnable 구현 (방법4)
class CustomRunnable implements Runnable {
  @Override
  public void run() {
      Log.i("Thread Test","Hello Custom Runnable!");
  }
}

// 4.2 Runnable 실행
Thread thread4 = new Thread(new CustomRunnable());
thread4.start();
```

## 소제목3
- 소제목3에 대한 설명
```java

```

## 소제목3
- 소제목3에 대한 설명
```java

```
