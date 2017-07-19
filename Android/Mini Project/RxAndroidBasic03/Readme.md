# RxAndroidBasic03
#### 1. Observable의 유틸리티 연산자 `SubscribeOn` , `ObserveOn` 에 대해 알아본다.
#### 2. ListView, RecyclerView의 효율적으로 사용하는 방법에 대해 알아본다.
<br>
<br>


## 1. Observable 유틸리티 연산자 `SubscribeOn` , `ObserveOn` 예제
### 1초마다 옵저버블이 발행하는 데이터를 옵저버가 안드로이드 화면에 뿌려준다.

<br>

- #### 전역변수 선언
```java
Observable<String> observable;
List<String> data = new ArrayList<>();
RecyclerAdapter adapter;
```
<br>


- #### onCreate
```java
adapter = new RecyclerAdapter(this, data);
recyclerView.setAdapter(adapter);
recyclerView.setLayoutManager(new LinearLayoutManager(this));

// 캘린더에서 1월 ~ 12월까지 텍스트를 추출
DateFormatSymbols dfs = new DateFormatSymbols();
String[] months = dfs.getMonths();

// 옵저버블 생성
observable = Observable.create( emitter  -> {
    for(String month : months){
        emitter.onNext(month);
        Thread.sleep(1000);
    }
    emitter.onComplete();
});
```
<br>


- #### 옵저버블과 옵저버가 각각 다른 쓰레드에서 실행 (Do Async 버튼 클릭시)
```java
btnAsync.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        observable.subscribeOn(Schedulers.io()) // 옵저버블의 Thread 를 지정 (데이터를 모으는 것)
                .observeOn(AndroidSchedulers.mainThread())  // 옵저버의 Thread 를 지정  - 데이터를 화면에 뿌려주는 것 (UI는 메인쓰레드에서만 핸들링할 수 있으니까)
                .subscribe( // 옵저버 생성
                str -> {
                    data.add(str);
                    // adapter.notifyItemChanged(포지션) : // 변경된 데이터만 갱신해준다.
                    // adapter.notifyItemInserted(data.dize()-1); // 추가된 데이터만 갱신해준다.
                    adapter.notifyDataSetChanged(); // 매번 데이터를 가져올 때마다 출력하기 위해 설정
                    },   // onNext
                error -> Log.e("Error", error.getMessage()),    // onError
                ( ) -> {    // onComplete
                    data.add(" === Complete !!! === ");
                    adapter.notifyDataSetChanged();
                }
        );
    }
});
```
- `subscribeOn(Schedulers.io())` 과 `observeOn(AndroidSchedulers.mainThread())` 가 Set로 많이 쓰인다.
- `subscribeOn` 은 옵저버블(Observable) 의 Thread를 지정해준다.
- `observeOn` 은 옵저버(Observer) 의 Thread를 지정해준다.

<br>

## 동작화면
<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic03/graphics/RxJavaAsync_01.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic03/graphics/RxJavaAsync_02.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic03/graphics/RxJavaAsync_03.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic03/graphics/RxJavaAsync_04.png' width='210' height='350' />




<br>
<br>
<br>

## 2. ListView, RecyclerView의 효율적인 사용
#### 1) ListView 사용시,
  - 아래와 같이 사용하면 이전 자료들까지 호출되어 리소스가 낭비되는 것을 막을 수 있다.
```java
private int lastPosition = -1;
@Override
public View getView(int position, View convertView, ViewGroup parent) {

    if(position > lastPosition){
        // 아래 동작..
    } else {
        return convertView;
    }

    if(convertView == null){
        convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
    }


    TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
    textView.setText(data.get(position));

    Log.i("Refresh", "~~~~~~~~~~~~ position = " + position);

    return convertView;
}
```

<br>


#### RecyclerView 사용시,
  - 아래와 같이 사용하면 변경된 데이터만 갱신해주거나 추가된 데이터만 갱신해주기 때문에 리소스 낭비를 막을 수 있다.

```java
@Override
public void onClick(View view) {
    observable.subscribeOn(Schedulers.io()) // 옵저버블의 Thread 를 지정 (데이터를 모으는 것)
            .observeOn(AndroidSchedulers.mainThread())  // 옵저버의 Thread 를 지정  - 데이터를 화면에 뿌려주는 것 (UI는 메인쓰레드에서만 핸들링할 수 있으니까)
            .subscribe(
            str -> {
                data.add(str);
                // adapter.notifyItemChanged(포지션) : // 변경된 데이터만 갱신해준다.
                // adapter.notifyItemInserted(data.dize()-1); // 추가된 데이터만 갱신해준다.
                adapter.notifyDataSetChanged(); // 매번 데이터를 가져올 때마다 출력하기 위해 설정
                },   // onNext
            error -> Log.e("Error", error.getMessage()),    // onError
            ( ) -> {    // onComplete
                data.add(" === Complete !!! === ");
                adapter.notifyDataSetChanged();
            }
    );
}
```

- `adapter.notifyItemChanged(포지션);` - 변경된 데이터만 갱신해준다.
- `adapter.notifyItemInserted(data.dize()-1);` - 추가된 데이터만 갱신해준다.
- `adapter.notifyDataSetChanged();` - 매번 데이터를 가져올 때마다 출력하기 위해 설정
