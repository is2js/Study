# RxAndroidBasic05
#### RxJava의 Subject 에 대해 알아본다.

<br>
<br>

## Subject란?
Subject는 옵저버나 Observable처럼 행동하는 ReactiveX의 일부 구현체에서 사용 가능한 일종의 교각 혹은 프록시라고 볼 수 있는데, 그 이유는 주제는 옵저버이기 때문에 하나 이상의 Observable을 구독할 수 있으며 동시에 Observable이기도 하기 때문에 항목들을 하나 하나 거치면서 재배출하고 관찰하며 새로운 항목들을 배출할 수도 있다.

하나의 주제는 하나의 Observable을 구독하면서, (Observable이 "차가운" Observable인 경우 즉, 옵저버가 구독을 시작하기 전까지 항목들의 배출을 지연시키는 Observable일 경우)Observable이 항목들을 배출시키도록 동작시킨다. 그 결과로 인해 원래는 "차가운" Observable이었던 주제를 "뜨거운" Observable로 만들기도 한다.

[참조: ReactiveX]

<br>
<br>

## Subject의 종류

- 모두 4 종류의 `Subject`가 존재하며, 각각의 Subject는 특정 상황에 맞도록 설계되었다. 그렇기 때문에 모든 상황에서 `Subject`를 임의대로 사용할 수 없으며 일부 구현체는 `Subject`를 다른 이름으로 부르기도 한다.
  - #### PublishSubject
  - #### BehaviorSubject
  - #### ReplaySubject
  - #### AsyncSubject
- 예를 들어, RxScala는 "주제(Subject)"를 "발행주제(PublishSubject)"로 부른다).




<br>
<br>

## 1. PublishSubject

- `PublishSubject`는 구독 이후에 소스 Observable(들)이 배출한 항목들만 옵저버에게 배출한다.
- 주의할 점은, `PublishSubject`는 (이를 막지 않는 이상) 생성 시점에서 즉시 항목들을 배출하기 시작할 것이고 이런 특성 때문에 주제가 생성되는 시점과 옵저버가 이 주제를 구독하기 시작하는 그 사이에 배출되는 항목들을 잃어 버릴 수 있다는 단점이 있다.
- 따라서, 소스 Observable이 배출하는 모든 항목들의 배출을 보장해야 한다면 `Create`을 사용해서 명시적으로 "차가운" Observable(항목들을 배출하기 전에 모든 옵저버가 구독을 시작했는지 체크한다)을 생성하거나, PublishSubject 대신 `ReplaySubject`를 사용해야 한다.

<br>

#### PublishSubject 예제 코드

```java
// 구독 시점부터 발행 데이터를 읽어볼 수 있다.
PublishSubject<String> publishSubject = PublishSubject.create();

public void doPublish(View view) {
    new Thread() {
        public void run() {
            for (int i = 0; i < 10; i++) {
                Log.i("Publish", "A" + i);
                publishSubject.onNext("A" + i);     // 발행
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        }
    }.start();
}

public void getPublish(View view){
    publishSubject.subscribe(
        item -> Log.i("Subscribe", "item = " + item)
    );
}
```

#### 결과화면

![PublishSubject](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic05/graphics/PublishSubject.png)


<br>

## 2. BehaviorSubject

- 옵저버가 `BehaviorSubject`를 구독하기 시작하면, 옵저버는 소스 Observable이 가장 최근에 발행한 항목(또는 아직 아무 값도 발행되지 않았다면 맨 처음 값이나 기본 값)의 발행을 시작하며 그 이후 소스 Observable(들)에 의해 발행된 항목들을 계속 발행한다.

- 만약, 소스 Observable이 오류 때문에 종료되면 `BehaviorSubject`는 아무런 항목도 배출하지 않고 소스 Observable에서 발생한 오류를 그대로 전달한다.

  ##### ex) 날씨를 3시간마다 발행할 경우, 10분 전에 발행을 해버렸어도 그 정보를 읽을 수 있다.

<br>

#### BehaviorSubject 예제 코드
```java
// 가장 마지막 발행한 것부터 구독할 수 있다.
BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

public void doBehavior(View view) {
    new Thread() {
        public void run() {
            for (int i = 0; i < 10; i++) {
                Log.i("Behavior", "B" + i);
                behaviorSubject.onNext("B" + i);     // 발행
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        }
    }.start();
}

public void getBehavior(View view){
    behaviorSubject.subscribe(
            item -> Log.i("Behavior", "item = " + item)
    );
}
```

#### 결과화면

![BehaviorSubject](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic05/graphics/BehaviorSubject.png)



<br>


## 3. ReplaySubject
- ReplaySubject는 옵저버가 구독을 시작한 시점과 관계 없이 소스 Observable(들)이 배출한 모든 항목들을 모든 옵저버에게 배출한다.

- ReplaySubject는 몇 개의 생성자 오버로드를 제공하는데 이를 통해, 재생 버퍼의 크기가 특정 이상으로 증가할 경우, 또는 처음 배출 이후 지정한 시간이 경과할 경우 오래된 항목들을 제거한다.

- 만약, ReplaySubject을 옵저버로 사용할 경우, 멀티 스레드 환경에서는 Observable 계약 위반과 주제에서 어느 항목 또는 알림을 먼저 재생해야 하는지 알 수 없는 모호함이 동시에 발생할 수 있기 때문에 (비순차적) 호출을 유발시키는 onNext(또는 그 외 on) 메서드를 사용하지 않도록 주의해야 한다.

<br>

#### ReplaySubject 예제 코드
```java
// 처음 발행한 것부터 읽을 수 있다.
ReplaySubject<String> replaySubject = ReplaySubject.create();

public void doReplay(View view) {
    new Thread() {
        public void run() {
            for (int i = 0; i < 10; i++) {
                Log.i("Behavior", "C" + i);
                replaySubject.onNext("C" + i);     // 발행
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        }
    }.start();
}

public void getReplay(View view){
    replaySubject.subscribe(
            item -> Log.i("Behavior", "item = " + item)
    );
}
```

#### 결과화면

![ReplaySubject](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic05/graphics/ReplaySubject.png)



<br>



## 4. AsyncSubject

- `AsyncSubject`는 소스 Observable로부터 배출된 마지막 값(만)을 배출하고 소스 Observalbe의 동작이 완료된 후에야 동작한다. (만약, 소스 Observable이 아무 값도 배출하지 않으면 `AsyncSubject` 역시 아무 값도 배출하지 않는다.)

- 또한 `AsyncSubject`는 맨 마지막 값을 뒤 이어 오는 옵저버에 전달하는데, 만약 소스 Observable이 오류로 인해 종료될 경우 `AsyncSubject`는 아무 항목도 배출하지 않고 발생한 오류를 그대로 전달한다.

<br>

#### AsyncSubject 예제 코드
```java
// 완료(Complete)된 시점에서 가장 마지막 데이터만 읽을 수 있다.
AsyncSubject<String> asyncSubject = AsyncSubject.create();

public void doAsync(View view) {
    new Thread() {
        public void run() {
            for (int i = 0; i < 10; i++) {
                Log.i("Async", "D" + i);
                asyncSubject.onNext("D" + i);     // 발행
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
            asyncSubject.onComplete();
        }
    }.start();
}

public void getAsync(View view){
    asyncSubject.subscribe(
            item -> Log.i("Async", "item = " + item)
    );
}
```

#### 결과화면

![AsyncSubject](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic05/graphics/AsyncSubject.png)

<br>
