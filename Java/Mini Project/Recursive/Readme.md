# Recursive Function(재귀함수)
#### 재귀함수(Recursive Function)
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/Recursive/src/RecursiveMain.java)**

## 재귀함수란?
- 재귀(Recursion)는 수학이나 컴퓨터 과학 등에서 자신을 정의할 때 자기 자신을 재참조하는 방법을 뜻한다.
- #### 재귀함수의 조건
  - (1) 끝나는 조건이 있다.
  - (2) 자기 자신을 호출하는 부분이 있다.

<br>

## 첫번째 예시 [Factorial]
#### (1) 반복문을 사용한 Factorial
```java
  int input = 5;
  int result = 1;

  for(int i=1; i<=input; i++) {
    result = result*i;
  }

  System.out.println(result); // 5*4*3*2*1 = 120 이 출력됨.
```
<br>
#### (2) 재귀함수를 이용한 Factorial
```java
  System.out.println("재귀함수 적용: " + Factorial(5))  // 출력값: 120
```


```java
  public static int Factorial(int n){
    if(n <= 1)
      return n;
    else
      return Factorial(n-1)*n;
}
```
<br>
<br>

## 두번째 예시 [피보나치 수열]
#### 재귀함수를 이용한 피보나치 수열
```java
  int input = 7;

  for(int i=1; i<=input; i++){
    System.out.print(fibo(i)+" ");  // 출력값: 1  1  2  3  5  8  13
  }
```

```java
public static int fibo(int n){
  if(n<=2) {
    return 1;
  }
  else if(n==3){
    return 2;
  } else {
    return fibo(n-1)+fibo(n-2);
  }
}
```

## 소제목3
- 소제목3에 대한 설명




조건

한글 설명
