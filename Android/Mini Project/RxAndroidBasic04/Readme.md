# RxAndroidBasic04
#### RxJava - Observable 연산자 중 `Observable 항목 변환`인 `Map` , `FlatMap`과 `Observable 필터`인 `Filter` 그리고 `Observable 결합`인 `Zip` 에 대해서 알아본다.

<br>
<br>

## 개념 설명 - `Map` , `FlatMap` , `Zip` , `Filter`
#### `Map` : Observable이 배출한 항목에 함수를 적용한다.
#### `FlatMap` : 하나의 Observable이 발행하는 항목들을 여러개의 Observable로 변환하고, 항목들의 배출을 차례차례 줄 세워 하나의 Observable로 전달한다.
#### `Zip` : 명시한 함수를 통해 여러 Observable들이 배출한 항목들을 결합하고 함수의 실행 결과를 배출한다.
#### `Filter` : 테스트 조건을 만족하는 항목들만 배출한다.

<br>
<br>

## 예제 소스코드

- ##### 전역변수 선언
```java
// 데이터 정의
List<String> data = new ArrayList<>();

RecyclerView recyclerView;
RecyclerAdapter adapter;
```

- ##### onCreate
```java
setViews();

adapter = new RecyclerAdapter(this, data);
recyclerView.setAdapter(adapter);
recyclerView.setLayoutManager(new LinearLayoutManager(this));

emitData();
```

- ##### Observable 생성 (`Map` , `FlatMap` , `Zip`)
```java
Observable<String> observableMap;
Observable<Integer> observableFlatMap;
Observable<String> observableZip;
String[] months;

public void emitData(){
    // 캘린더에서 1월 ~ 12월까지 텍스트를 추출
    DateFormatSymbols dfs = new DateFormatSymbols();
    months = dfs.getMonths();

    // 초당 1개씩 데이터 발행 - Map
    observableMap = Observable.create( emitter  -> {
        for(String month : months){
            emitter.onNext(month);
            Thread.sleep(1000);
        }
        emitter.onComplete();
    });

    // 초당 1개씩 데이터 발행 - FlatMap
    observableFlatMap = Observable.create( emitter  -> {
        for(int i=0 ; i<12 ; i++){
            emitter.onNext(i);
            Thread.sleep(1000);
        }
        emitter.onComplete();
    });

    //  - Zip
    observableZip = Observable.zip(
            Observable.just("MDY", "BeWhy"),
            Observable.just("Developer", "Rapper"),
            (item1, item2) -> "name : " + item1 + "  /  job : " + item2
    );
}
```

- ##### doMap 메소드
```java
// doMap - onClick
public void doMap(View view){
    observableMap
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter( item -> item.equals("May") ? false : true)
            .map(item -> "[ " + item + " ]")
            .subscribe(
                    item -> {
                        data.add(item);
                        adapter.notifyItemInserted(data.size()-1);
                    },
                    error -> Log.e("Error", error.getMessage()),
                    ( ) -> Log.i("Complete", "Successfully completed !")
            );
}
```

- ##### doFlatMap 메소드
```java
// doFlatMap - onClick
public void doFlatMap(View view){
    observableFlatMap.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter( item -> item.equals("May") ? false : true)
            .flatMap(item -> Observable.fromArray(new String[] {
                    "name : " + months[item], "code : " + item
            }))
            .subscribe(
                    item -> {
                        data.add(item);
                        adapter.notifyItemInserted(data.size()-1);
                    },
                    error -> Log.e("Error", error.getMessage()),
                    ( ) -> Log.i("Complete", "Successfully completed !")
            );
}
```

- ##### doZip 메소드
```java
// doZip - onClick
public void doZip(View view){
    observableZip
            // .timeInterval(TimeUnit.SECONDS, Schedulers.computation())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    item -> {
                        data.add(item + "");
                        adapter.notifyItemInserted(data.size()-1);
                    },
                    error -> Log.e("Error", error.getMessage()),
                    ( ) -> Log.i("Complete", "Successfully completed !")
            );
}
```

<br>
<br>


## 동작 화면
### 1. Map 동작화면
  - 데이터에 변형을 한다.
  - "May" 는 filter를 통해 걸러진다.

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic04/graphics/RxAndroidBasic04_01.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic04/graphics/RxAndroidBasic04_02.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic04/graphics/RxAndroidBasic04_03.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic04/graphics/RxAndroidBasic04_04.png' width='210' height='350' />

<br>


### 2. FlatMap 동작화면
  - `FlatMap`은 1:n 으로 데이터를 복제(clone)해준다.

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic04/graphics/RxAndroidBasic04_01.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic04/graphics/RxAndroidBasic04_05.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic04/graphics/RxAndroidBasic04_06.png' width='210' height='350' />

<br>


### 3. Zip 동작화면

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic04/graphics/RxAndroidBasic04_01.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic04/graphics/RxAndroidBasic04_07.png' width='210' height='350' />
