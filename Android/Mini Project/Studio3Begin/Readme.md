# 람다식 (Lambda Expression)
#### 람다식(Lambda Expression)에 대해서 알아본다.
<br>
<br>

## 람다식 정리
#### 1. 람다식은 객체 안에 함수가 하나여야 한다.
#### 2. 함수의 파라미터 수는 상관없다.
#### 3. 람다식으로 사용하는 원본 인터페이스는 껍데기만 존재한다. (즉, 설계 도구)
- #### [전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Studio3Begin/app/src/main/java/com/mdy/android/studio3begin/MainActivity.java)
<br>
<br>

## Java8에서 람다란?
- 실행 가능한 코드블럭
```java
() -> {
  System.out.println("Lambda");
}
```
- 람다는 실행가능한 코드 블럭으로
```java
  ( Parameters ) -> { code }
```
형태로 저장되며 변수에 담았다가 필요시에 호출해서 실행할 수 있다.
```java
Runnable thread = () -> {
  System.out.pirntln("Lambda");
}
thread.run();
```

<br>
<br>

## 람다의 필요성
##### 1. 대용량 데이터를 다루기 위한 병렬화 기술의 필요성
- [데이터의 증가 / 빅데이터 처리] -> [대용량 컬렉션] -> [병렬처리] -> [객체스트림] -> [람다(Lambda)]
##### 2. 코드 경량화 및 생산성에 대한 활발한 연구
- [코드의 간소화] -> [함수형언어 대두] -> [람다(Lambda)]

<br>
<br>

## 람다표현식(Lambda Expression)
- 실행할 수 있도록 전달이 가능한 코드 블럭
<br>

##### 기본문법
```java
( Long val1, String val2 ) -> { val1 + val2.length(); }
```
<br>

##### 1) int 파라미터 a의 값을 콘솔에 출려갛기 위한 람다표현식
```java
( int a ) -> { System.out.println(a); }
```

##### 2) 파라미터 타입은 런타임 시에 대입되는 값에 따라 자동인식
```java
( a ) -> { System.out.println(a); }
```

##### 3) 하나의 파라미터만 있다면 괄호 생략, 하나의 실행문만 있다면 중괄호 생략 가능
```java
a -> System.out.println(a);
```

##### 4) 파라미터가 없다면 빈 괄호를 반드시 사용
```java
( ) -> System.out.println(a);
```

##### 5) 중괄호를 실행하고 결과값을 리턴하는 경우
```java
(x, y) -> { return x+y; }
```

##### 6) 중괄호에 return문만 있을 경우
```java
(x, y) -> { x + y }
```
<br>
<br>

## Runnable 인터페이스로 보는 람다
##### 1. 생성자와 method의 생략
```java
Runnable thread = new Runnable(){

  public void run(){
    System.out.println("익명객체 코드에서 실행");
  }

};
thread.run();
```
- 객체 내에 하나의 method만 존재할 경우 method를 생략할 수 있다. - new Runable()
- 객체 내에 하나의 method만 존재할 경우 객체자체를 생략할 수 있다. - public void run()
```java
// 위의 코드에 람다식 적용한 코드

Runnable lambda = ( ) -> {
  System.out.println("람다 코드에서 실행");
};

lambda.run();
```
<br>

##### 2. 코드 블럭의 생략
```java
Runnable lambda = ( ) -> {
  System.out.println("람다 코드에서 실행");
};

lambda.run();
```
- 코드블럭이 한줄일 경우 블럭을 생략할 수 있다.
```java
// 위의 코드에 람다식 적용한 코드

Runnable lambda = ( ) -> System.out.println("람다 코드에서 실행");

lambda.run();
```
<br>
<br>




## 람다식 예제 코드 (1)
```java
@FunctionalInterface
interface LambdaFunction {
    public abstract int squareParameter(int number);
}
```

- 람다식 안에 람다식을 사용안한 경우
```java
  button.setOnClickListener( view -> {
      LambdaFunction arg_original = new LambdaFunction() {
          @Override
          public int squareParameter(int p) {
              return p * p;
          }
      };
      calc(arg_original);
  });
```

- 람다식 안에 람다식을 사용한 경우
```java
  button.setOnClickListener((view) -> {
      LambdaFunction arg = p -> p * p;
      calc(arg);
  });
```
<br>
<br>


## 람다식 예제 코드 (2)
```java
String objectArray[] = {"A", "B", "C", "D123", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

Stream<String> data = Arrays.stream(objectArray);


...


btnNoStream.setOnClickListener( view -> printOneWord(objectArray) );
btnStream.setOnClickListener( view -> printStream(data) );


...


private void printOneWord(String arr[]) {
    for (String item : arr) {
        if (item.length() == 1) {
            System.out.println(item);
        }
    }
}

private void printStream(Stream<String> data){
    data
            .filter( item -> item.length() == 1)
            .forEach( item -> System.out.println(item) );
}
```
- printOneWord 은 한번에 String을 다 받아와놓고, 실행을 시킨다.
- printStream은 data가 objectArray[]를 가리키고 있는 것이고, forEach문이 동작하면 "A"를 먼저 가리키고, 그 다음으로 "B"를 가리키고 이런 식으로 동작한다.


<br>
<br>
<br>




## FindViewByMe
#### 설치
- https://plugins.jetbrains.com/plugin/8261-findviewbyme 사이트에서 .jar 파일 다운
- 안드로이드 프로젝트에서 File - Settings - Plugins - Install plugin from disk 클릭 후, 다운받은 .jar파일 선택
- 적용 후 restart
<br>

#### 사용
- layout에서 위젯들에 id값을 준다.
- onCreate 안에 있는
setContentView(R.layout.activity_main); 에서 activity_main 위에 마우스 커서를 두고 오른쪽 마우스를 클릭하고, Generate - FindViewByMe 선택하고, 사용
