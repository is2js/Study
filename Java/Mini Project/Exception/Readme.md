# 예외 처리
- **[소스코드](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/Exception/src/com/mdy/java/exception/ExceptionMain.java)**

## 예외와 예외 클래스
- 컴퓨터 하드웨어의 오동작 또는 고장으로 인해 응용프로그램 실행 오류가 발생하는 것을 자바에서는 **에러(Error)** 라고 한다. 에러는 JVM 실행에 문제가 생겼다는 것이므로 JVM 위에서 실행되는 프로그램을 아무리 견고하게 만들어도 결국 실행 불능이 된다. 개발자는 이런 에러에 대처할 방법이 전혀 없다.
- 자바에서는 에러 이외에 **예외(exception)** 이 라고 부르는 오류 가 있다.
- **예외** 란 **사용자의 잘못된 조작 또는 개발자의 잘못된 코딩으로 인해 발생하는 프로그램 오류를 말한다.** 예외가 발생되면 프로그램은 곧바로 종료된다는 점에서 에러와 동일하다. 그러나 예외는 예외 처리(Exception Handling)를 통해 프로그램을 종료하지 않고 정상 실행 상태가 유지되도록 할 수 있다.

#### 예외의 2가지 종류
> ##### (1) 일반 예외(Exception)
>>- 컴파일로 체크 예외라고도 하는데, 자바 소스를 컴파일하는 과정에서 **예외 처리 코드** 가 필요한지 검사하기 때문이다. 만약 예외 처리 코드가 없다면 컴파일 오류가 발생한다.
>##### (2) 실행 예외(Runtime Exception)
>> - 실행 예외는 컴파일하는 과정에서 예외 처리 코드를 검사하지 않는 예외를 말한다.

- 컴파일 시 예외 처리를 확인하는 차이일 뿐, 두 가지 예외는 모두 예외 처리가 필요하다.
- 자바에서는 예외를 클래스로 관리한다.
- JVM은 프로그램을 실행하는 도중에 예외가 발생하면 해당 예외 클래스로 객체를 생성한다.
- 그리고 나서 예외 처리 코드에서 예외 객체를 이용할 수 있도록 해준다.
- 모든 예외 클래스들은 다음과 같이 java.lang.Exception 클래스를 상속받는다.

> java.lang.Exception
>> java.lang.ClassNotFoundException  <일반 예외>
>> java.lang.InterruptedException  <일반 예외>
>> ...
>> java.lang.RuntimeException  <실행 예외>

- 일반 예외와 실행 예외 클래스를 구별하는 방법은 일반 예외는 Exception을 상속받지만 RuntimeException을 상속받지 않는 클래스들이고, 실행 예외는 다음과 같이 RuntimeException을 상속받은 클래스들이다.
- RuntimeException 역시 Exception을 상속받지만, JVM은 RuntimeException을 상속했는지 여부를 보고 실행 실행 예외를 판단한다.

> java.lang.RuntimeException
>> java.lang.NullPointerException  <실행 예외>
>> java.lang.ClassCastException  <실행 예외>
>> java.lang.NumberFormatException  <실행 예외>
>> ...


## 실행 예외
- 실행 예외는 자바 컴파일러가 체크를 하지 않기 때문에 오로지 개발자의 경험에 의해서 예외 처리 코드를 삽입해야 한다.
- 만약 개발자가 실행 예외에 대해 예외 처리 코드를 넣지 않았을 경우, 해당 예외가 발생하면 프로그램은 곧바로 종료된다.

#### 실행 예외의 4가지 종류
##### (1) NullPointerException
- 자바 프로그램에서 가장 빈번하게 발생하는 실행 예외는 java.lang.NullPointerException일 것이다.
- 이것은 객체 참조가 없는 상태, 즉 Null 값을 갖는 참조 변수로 객체 접근 연산자인 도트(.)를 사용했을 때 발생한다. 객체가 없는 상태에서 객체를 사용하려 했으니 예외가 발생하는 것이다.

##### (2) ArrayIndexOutOfBoundsException
- 배열에서 인덱스 범위를 초과하여 사용할 경우, 실행 예외인 java.lang.ArrayIndexOutOfBoundsException이 발생한다.
- 예를 들어, 길이가 3인 int[] arr = new int[3] 배열을 선언했다면, 배열 항목을 지정하기 위해 arr[0] ~ arr[2] 를 사용할 수 있다. 하지만 arr[3] 을 사용하면 인덱스 범위를 초과했기 때문에 ArrayIndexOutOfBoundsException이 발생한다.

##### (3) NumberFormatException
- 프로그램을 개발하다 보면 문자열로 되어 있는 데이터를 숫자로 변경하는 경우가 자주 발생한다.
- 문자열을 숫자로 변환하는 방법은 여러 가지가 있지만 가장 많이 사용되는 코드는 다음과 같다.

  | 반환 타입 | 메소드명(매개 변수) | 설명 |
  | :----- | :----- | :----- |
  | int | Integer.parseInt(String s) | 주어진 문자열을 정수로 변환해서 리턴 |
  | double | Double.parseDouble(String s) | 주어진 문자열을 실수로 변환해서 리턴 |

- 이 메소드들은 매개값인 문자열이 숫자로 변환될 수 있다면 숫자를 리턴하지만, 숫자로 변환될 수 없는 문자가 포함되어 있다면 java.lang.NumberFormatException을 발생시킨다.

##### (4) ClassCastException
- 타입 변환(Casting)은 상위 클래스와 하위 클래스 간에 발생하고 구현 클래스와 인터페이스 간에도 발생한다. 이러한 관계가 아니라면 클래스는 다른 클래스로 타입 변환할 수 없다.
- 억지로 타입 변환을 시도할 경우 ClassCastException이 발생한다.
- 예를 들어 다음과 같이 상속 관계와 구현 관계가 있다고 가정해보자.

> **Animal** : 추상클래스
>> - Dog
>> - Cat
>
>**RemoteControl** : 인터페이스
>> - Television
>> - Audio

- 다음은 올바른 타입 변환을 보여준다. Animal 타입 변수에 대입된 객체가 Dog이므로 다시 Dog 타입으로 변환하는 것은 아무런 문제가 없다. 마찬가지로 RemoteControl 타입 변수에 대입된 객체가 Television이므로 다시 Television 타입으로 변환하는 것은 아무런 문제가 없다.
```java
  Animal animal = new Dong();
  Dong dog = (Dog) animal;

  RemoteControl rc = new Television();
  Television tv = (Television) rc;
```
- 그러나 다음과 같이 타입 변환을 하면 ClassCastException 이 발생한다.
- 대입된 객체가 아닌 다른 클래스 타입으로 타입 변환했기 떄문이다.
```java
  Animal animal = new Dog();
  Cat cat = (Cat) animal;

  RemoteControl rc = new Television();
  Audio audio = (Audio) rc;
```
- ClassCastException을 발생시키지 않으려면 타입 변환 전에 타입 변환이 가능한지 instanceof 연산자로 확인하는 것이 좋다. instanceof 연산의 결과가 true이면 좌항 객체를 우항 타입으로 변환이 가능하다는 뜻이다.
```java
  Animal animal = new Dog();
  if(animal instaceof Dog) {
    Dog dog = (Dog) animal;
  } else if (animal instaceof Cat) {
    Cat cat = (Cat) animal;
  }


  RemoteControl rc = new Audio();
  if(rc instaceof Television) {
    Television tv = (Television) rc;
  } else if (rc instaceof Audio) {
    Audio audio = (Audio) rc;
  }
```
- [예제]
```java
  public class ClassCastExceptionExample {
    public static void main(String[] args) {
      Dog dog = new Dog();
      changeDog(dog);

      Cat cat = new Cat();
      changeDog(cat);
    }

    public static void changeDog(Animal animal) {
      // if(animal instaceof Dog) {
      Dog dog = (Dog) animal;   // ClassCastException 발생 가능
    // }
    }
  }

  class Animal {}
  class Dog extends Animal {}
  class Cat extends Animal {}
```
- 예제를 실행하면 12번째줄에서 ClassCastException이 발생한다.
- 그 이유는 7라인에서 Cat 객체를 매개값으로 주었기 때문에 Dog 타입으로 변환할 수 없다.
- 이렇게 잘못된 매개값이 들어올 수 있기 때문에 changeDog() 메소드에서 11라인과 13라인의 주석을 풀어 타입 체크를 먼저 하는 것이 좋다.



## 예외 처리 코드
- 프로그램에서 예외가 발생했을 경우 프로그램의 갑작스러운 종료를 막고, 정상 실행을 유지할 수 있도록 처리하는 코드를 **예외 처리 코드** 라고 한다.
- 자바 컴파일러는 소스 파일을 컴파일할 때 일반 예외가 발생할 가능성이 있는 코드를 발견하면 컴파일 오류를 발생시켜 개발자로 하여금 강제적으로 예외 처리 코드를 작성하도록 요구한다.
- 그러나 실행 예외는 컴파일러가 체크해주지 않기 때문에 예외 처리 코드를 개발자의 경험을 바탕으로 작성해야 한다.
- 예외 처리 코드는 try ~ catch ~ finally 블록을 이용한다.
- try ~ catch ~ finally 블록은 생성자 내부와 메소드 내부에서 작성되어 일반 예외와 실행 예외가 발생할 경우 예외 처리를 할 수 있도록 해준다.
- try 블록에는 예외 발생 가능 코드가 위치한다.
- try 블록의 코드가 예외 발생 없이 정상 실행되면 catch 블록의 코드는 실행되지 않고 finally 블록의 코드를 실행한다. 만약 try 블록의 코드에서 예외가 발생하면 즉시 실행을 멈추고 catch 블록으로 이동하여 예외 처리 코드를 실행한다. 그리고 finally 블록의 코드를 실행한다. finally 블록은 옵션으로 생략가능하다.
- 예외 발생 여부와 상관없이 항상 실행할 내용이 있을 경우에만 finally 블록을 작성해주면 된다. try 블록과 catch 블록에서 return 문을 사용하더라도 finally 블록은 항상 실행된다.

## 예외 종류에 따른 처리 코드

#### 다중 catch
- try 블록 내부는 다양한 종류의 예외가 발생할 수 있다.
- 이 경우, 다중 catch 블록을 작성하면 발생되는 예외별로 예외 처리 코드를 다르게 할 수 있다.
- catch 블록의 예외 클래스 타입은 try 블록에서 발생된 예외의 종류를 말하는데, try 블록에서 해당 타입의 예외가 발생하면 catch 블록을 실행하도록 되어 있다.
```java
  try {

    예외가 예상 되는 코드 작성

  } catch (ArrayIndexOutOfBoundsException e) {

    예외 처리 (1)

  } catch (NumberFormatException e) {

    예외 처리 (2)

  }
```
- catch 블록이 여러 개라 할지라도 단 하나의 catch 블록만 실행된다.
- 그 이유는 try 블록에서 동시다발적으로 예외가 발생하지 않고, 하나의 예외가 발생하면 즉시 실행을 멈추고 해당 catch 블록으로 이동하기 때문이다.

#### catch 순서
- 다중 catch 블록을 작성할 때 주의할 점은 상위 예외 클래스가 하위 예외 클래스보다 아래쪽에 위치해야 한다.
- try 블록에서 예외가 발생했을 때, 예외를 처리해줄 catch 블록은 위에서부터 차례대로 검색된다.
- 만약 상위 예외 클래스의 catch 블록이 위에 있다면, 하위 예외 클래스의 catch 블록은 실행되지 않는다.
- 왜냐하면 하위 예외는 상위 예외를 상속했기 때문에 상위 예외 타입도 되기 때문이다.

##### <잘못된 코딩>
```java
try {

  예외가 예상 되는 코드 작성

} catch (Exception e) {

  예외 처리 (1)

} catch (ArrayIndexOutOfBoundsException e) {

  예외 처리 (2)

}
```

##### <수정한 코딩>
```java
try {

  예외가 예상 되는 코드 작성

} catch (ArrayIndexOutOfBoundsException e) {

  예외 처리 (1)

} catch (Exception e) {

  예외 처리 (2)

}
```

#### 멀티 catch
- 자바 7부터 하나의 catch 블록에서 여러 개의 예외를 처리할 수 있도록 멀티(multi) catch 기능을 추가했다.
- 다음은 멀티 catch 블록을 작성하는 방법을 보여준다.
- catch 괄호() 안에 동일하게 처리하고 싶은 예외를 | 로 연결하면 된다.
```java
  try {

    예외가 예상 되는 코드 작성

  } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {

    예외 처리 (1)

  } catch (Exception e) {

    예외 처리 (2)

  }
```



## 예외 떠넘기기
- 메소드 내부에서 예외가 발생할 수 있는 코드를 작성할 때 try ~ catch 블록으로 예외를 처리하는 것이 기본이지만, 경우에 따라서는 메소드를 호출한 곳으로 예외를 떠넘길 수도 있다.
- 이때 사용하는 키워드가 throws이다.
- throws 키워드는 메소드 선언부 끝에 작성되어 메소드에서 처리하지 않은 예외를 호출한 곳으로 떠넘기는 역할을 한다. throws 키워드 뒤에는 떠넘길 예외 클래스를 쉼표로 구분해서 나열해주면 된다.
```java
  리턴타입 메소드명(매개변수, ...) throws 예외클래스1, 예외클래스2, ... {

  }
```
- 발생할 수 있는 예외의 종류별로 throws 뒤에 나열하는 것이 일반적이지만, 다음과 같이 throws Exception만으로 모든 예외를 간단히 떠넘길 수도 있다.
```java
  리턴타입 메소드명(매개변수, ...) throws Exception {

  }
```
- throws 키워드가 붙어있는 메소드는 반드시 try 블록 내에서 호출되어야 한다. 그리고 catch 블록에서 떠넘겨 받은 예외를 처리해야 한다. 다음 코드는 throws 키워드가 있는 method2()를 method1()에서 호출하는 방법을 보여준다.
```java
  public void method1() {
    try {
      method2();
    } catch(ClassNotFoundException e) {
      // 예외 처리 코드
      System.out.println("클래스가 존재하지 않습니다.");
    }
  }

  public void method2() throws ClassNotFoundException {
    Class clazz = Class.forName("java.lang.String2");
  }
```
- method1() 에서도 try ~ catch 블록으로 예외를 처리하지 않고 throws 키워드로 다시 예외를 떠넘길 수 있다. 그러면 method1()을 호출하는 곳에서 결국 try ~ catch 블록을 사용해서 예외를 처리해야 한다.
```java
  public void method1() throws ClassNotFoundException {
    method2();
  }
```

## 사용자 정의 예외와 예외 발생
- 프로그램을 개발하다 보면 자바 표준 API에서 제공하는 예외 클래스만으로는 다양한 종류의 예외를 표현할 수가 없다.
- 예를 들어 은행 업무를 처리하는 프로그램에서 잔고보다 더 많은 출금 요청이 들어왔을 경우 오류가 되며, 프로그램은 잔고 부족 예외를 발생시킬 필요가 있다. 그러나 잔고 부족 예외는 자바 표준 API에는 존재하지 않는다.
- 잔고 부족 예외와 같이 애플리케이션 서비스와 관련된 예외를 **애플리케이션 예외(Application Exception)** 라고 한다.
- **애플리케이션 예외** 는 개발자가 직접 정의해서 만들어야 하므로 **사용자 정의 예외** 라고도 한다.

#### 사용자 정의 예외 클래스 선언
- 사용자 정의 예외 클래스는 컴파일러가 체크하는 **일반 예외** 로 선언할 수도 있고, 컴파일러가 체크하지 않는 **실행 예외** 로 선언할 수도 있다.
- **일반 예외** 로 선언할 경우, Exception을 상속하면 되고, **실행 예외** 로 선언할 경우에는 RuntimeException을 상속하면 된다.
```java
  public class XXXException extends [ Exception | RuntimeException ] {

      public XXXException() {

      }

      public XXXException(String message) {
        super(message);
      }

  }
```
- 사용자 정의 예외 클래스 이름은 Exception으로 끝나는 것이 좋다.
- 사용자 정의 예외 클래스도 필드, 생성자, 메소드 선언들을 포함할 수 있지만 대부분 생성자 선언만을 포함한다.
- **생성자는 2개를 선언하는 것이 일반적인데, 하나는 매개 변수가 없는 기본 생성자이고, 다른 하나는 예외 발생 원인(예외 메세지)을 전달하기 위해 String 타입의 매개 변수를 갖는 생성자이다.**
- String 타입의 매개 변수를 갖는 생성자는 상위 클래스의 생성자를 호출하여 예외 메시지를 넘겨준다.
- 예외 메시지의 용도는 catch { } 블록의 예외 처리 코드에서 이용하기 위해서이다.
