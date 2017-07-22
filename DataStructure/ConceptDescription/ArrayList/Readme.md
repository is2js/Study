# ArrayList
#### ArrayList에 대해서 알아본다.


<br>
<br>

## ArrayList란?
### 소개
- ArrayList는 List를 만들때 내부적으로 배열을 이용해 구현한 것을 의미한다.
- 즉, List라는 큰 완제품 안에 배여이라는 부품을 사용하는 것이라고 볼 수 있다.
  - [참고] LinkedList의 경우에는, 배열말고 다른 방법을 사용한다.

<br>

### 단점
- 데이터를 추가하거나 삭제할 때, 그 뒤에 있는 데이터를 하나씩 뒤로 옮기거나 또는 하나씩 일일이 앞으로 당겨야 하기 때문에 시간이 많이 걸린다.

<br>

### 장점
- Index를 이용해서 데이터를 가져오는데 굉장히 빠르다. (이것이 ArrayList의 중요한 장점이다!)
- 또한 데이터의 크기를 가져올 때도 size()라는 연산자를 통해 바로 알아낼 수 있다.

<br>
<br>


## ArrayList 사용법

### 생성
```java
ArrayList<Integer> numbers = new ArrayList<>();
```
- 사용하고자 하는 ArrayList가 내부적으로 element들이 Integer, 즉 숫자라는 것을 미리 지정해놓은 것이다.

<br>

### 추가 `add`
```java
numbers.add(10);
numbers.add(20);
numbers.add(30);
numbers.add(40);


// Index 1에 50이라는 값을 추가한다.
numbers.add(1, 50);
```
- ArrayList는 내부적으로 데이터를 배열에 저장한다.
- 배열의 특성상 데이터를 리스트의 처음이나 중간에 저장하면 이후의 데이터들이 한칸씩 뒤로 물러나야 한다.


<br>

### 삭제 `remove`
```java
numbers.remove(2);
```
- Index가 2인 값을 삭제
- 삭제도 추가와 비슷하다. 빈자리가 생기면 빈자리를 채우기 위해서 순차적으로 한칸씩 땡겨야 한다.

<br>

### 데이터 가져오기 `get`
```java
numbers.get(2);
```
- Index가 2인 값을 가져온다.
- Index를 이용해서 데이터를 가져오고 싶을 때 Array로 구현한 리스트를 매우 빠르다.
- 메모리 상의 주소를 정확하게 참조해서 가져오기 때문이다.

<br>

### 데이터의 크기를 가져오기 `size`
```java
numbers.size();
```
- numbers 객체의 크기를 반환한다.


<br>
<br>


## Iterator
- 자바에서는 ArrayList를 탐색하기 위한 방법으로 iterator를 제공한다.
  (Iterator는 자바에 있는 인터페이스이다.)
- 이것은 주로 객체지향 프로그래밍에서 사용하는 반복기법이다.

```java
Iterator it<Integer> = numbers.iterator();

while(it.hasNext()){
  System.out.println(it.next());
}
```

- it.next() 메소드를 호출할 때마다 엘리먼트를 순서대로 리턴한다.
- 만약 더이상 순회할 엘리먼트가 없다면 it.hasNext()의 값은 false가 되면서 while문이 종료된다.


```java
while(it.hasNext()){
  int value = it.next();
  if(value == 30){
    it.remove();  // 또는 it.add(15);
  }
}
```
- 순회 과정에서 필요에 따라서는 엘리먼트를 삭제/추가하는 작업을 해야할 것이다.
- it.remove()는 it.next()를 통해서 반환된 numbers의 엘리먼트를 삭제하는 명령이다.
