# RxAndroidBasic02
- #### RxJava의 `Observer`와 `Observable`에 대해서 알아본다.
- #### `Observable 생성 연산자`에 대해 알아본다.
<br>
<br>

## RxJava
- RxJava의 Main Player는 Emitter ( `Observable`, `Subject` ), Consumer ( `Observer`, `Subscriber` ) 이다.
- Subject는 Observable 과 Observer를 더한 것이다.
- Observable은 Observer를 등록할 수 있게 해준다.
- Observable에 Observer를 등록하는 메소드가 여러가지가 있는데 그 중에 `create`, `from`, `just`, `defer가` 있다.
<br>
<br>

## Observable 생성 연산자 `Create`
- #### 직접적인 코드 구현을 통해 옵저버 메서드를 호출하여 Observable을 생성한다.
- #### 사용하기 전에 gradle에 dependency 추가
  - `compile 'io.reactivex.rxjava2:rxandroid:2.0.1'`
- #### Observable 생성
```java
    // onNext로 데이터를 발행
    private void createObservable() {

        // Java
//        observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("Hello RxAndroid");
//                emitter.onNext("Good to see you");
//                emitter.onComplete();
//            }
//        });
//    }

        // Lambda
        observable = Observable.create(emitter -> {
            emitter.onNext("Hello RxAndroid");
            emitter.onNext("Good to see you");
            emitter.onComplete();
        });
    }
```
<br>

- #### Observer 등록
  - ##### 1번 형태 : 옵저버 안에 4개의 함수가 구현되어 있다.
  ```java
  @TargetApi(Build.VERSION_CODES.N)
  private void bindObserver() {
      // 1번 형태 : 옵저버안에 4개의 함수가 구현되어 있다
      Observer<String> observer = new Observer<String>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(String value) {
              // 데이터가 있으면 onNext가 자동으로 실행이 된다.
              Log.e("OnNext", "============= 1" + value);
          }

          @Override
          public void onError(Throwable e) {
              Log.e("OnError", "xxxxxxx 1" + e.getMessage());
          }

          @Override
          public void onComplete() {
              Log.w("OnComplete", "ooooooooooooooo 1 complete!");
          }
      };
      observable.subscribe(observer);
  }
  ```
  - ##### 2번 형태 : 람다식을 사용하기 전단계로, 옵저버 내에 있는 함수들을 하나씩 분리한다.
  ```java
  @TargetApi(Build.VERSION_CODES.N)
  private void bindObserver() {
      Consumer<String> onNext = new Consumer<String>() {
          @Override
          public void accept(String str) {
              Log.e("OnNext", "============= 2" + str);
          }
      };
      Consumer<Throwable> onError = new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) {
          }
      };
      observable.subscribe(onNext, onError);
  }
  ```
  - ##### 3번 형태 : 인터페이스 하나당 하나의 함수만 존재하므로 람다식으로 바로 구현
  ```java
  @TargetApi(Build.VERSION_CODES.N)
      private void bindObserver() {
        observable.subscribe(
            str -> Log.e("OnNext", "============= 3" + str),
            throwable -> Log.e("OnError", "xxxxxxx 3" + throwable.getMessage()),
            () -> Log.w("OnComplete", "ooooooooooooooo 3 complete!")
        );
  }
  ```

<br>
<br>

## Observable 생성 연산자 `From`
- #### From : 다른 객체나 자료 구조를 Observable로 변환한다.
```java
Observable<String> forFrom;


...


private void initObservable(){
    // from 생성
    String fromData[] = {"aaa", "bbb", "ccc", "ddd", "eee"};
    forFrom = Observable.fromArray(fromData);
}


...


public void doFrom(View view){
       // 원형
       forFrom.subscribe(
               new Consumer<String>() {        // onNext 가 대응
                   @Override
                   public void accept(String str) throws Exception {
                       data.add(str);
                   }
               },
               new Consumer<Throwable>() {     // onError 가 대응
                   @Override
                   public void accept(Throwable throwable) throws Exception {
                       /*일단 아무것도 안함*/
                   }
               },
               new Action() {                  // onComplete 가 대응
                   @Override
                   public void run() throws Exception {
                       adapter.notifyDataSetChanged();
                   }
               }
       );

       // 람다표현식
      //  forFrom.subscribe(
      //          (str) -> data.add(str),                  // 옵저버블(발행자:emitter)로 부터 데이터를 가져온다.
      //          t     -> { /* 에러 처리 */},
      //          ()    -> adapter.notifyDataSetChanged()  // 완료되면 리스트에 알린다.
      //  );
}
```
- CustomAdapter
```java
class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{

    List<String> data;
    LayoutInflater inflater;

    public CustomAdapter(List<String> data, Context context){
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
```
<br>
<br>

## Observable 생성 연산자 `Just`
- #### Just : 객체 하나 또는 객채집합을 Observable로 변환한다. 변환된 Observable은 원본 객체들을 발행한다.
```java
Observable<Memo> forJust;


...


// just 생성자를 위한 클래스
class Memo {
    String content;

    public Memo(String content){
        this.content = content;
    }
}


...


private void initObservable() {

  // just 생성
  Memo memo1 = new Memo("Hello");
  Memo memo2 = new Memo("Android");
  Memo memo3 = new Memo("with");
  Memo memo4 = new Memo("Reactive X");

  forJust = Observable.just(memo1, memo2, memo3, memo4);

}


...


public void doJust(View view){
    forJust.subscribe(
            obj -> data.add(obj.content),
          t     -> { /* 에러 처리 */},
            ()    -> adapter.notifyDataSetChanged()
    );
}
```

<br>
<br>

## Observable 생성 연산자 `Defer`
- #### Defer : 옵저버가 구독하기 전까지는 Observable 생성을 지연하고 구독이 시작되면 옵저버 별로 새로운 Observable을 생성한다.

```java
Observable<String> forDefer;


...


private void initObservable() {
    // defer 생성
    // 선언할때 메모리에 올라가지 않고, 호출해서 발행할때 메모리에 올라간다.
    forDefer = Observable.defer(new Callable<ObservableSource<? extends String>>() {
        @Override
        public ObservableSource<? extends String> call() throws Exception {
            return Observable.just("monday", "tuesday", "wednesday");
        }
    });
}


...


public void doDefer(View view){
    forDefer.subscribe(
            (str) -> data.add(str),         // 옵저버블(발행자:emitter)로 부터 데이터를 가져온다.
            t     -> { /* 에러 처리 */},
            ()    -> adapter.notifyDataSetChanged()     // 완료되면 리스트에 알린다.
    );
}

```
