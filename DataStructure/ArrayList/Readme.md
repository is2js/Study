# Java로 ArrayList 구현하기
#### Java로 ArrayList를 직접 구현해본다.

- ### [전체소스코드](https://github.com/mdy0501/Study/blob/master/DataStructure/ArrayList/src/list/arraylist/implementation/Main.java)
<br>
<br>

## 객체생성
- ArrayList라는 이름의 객체를 만든다.

`ArrayList.java`
```java
package list.arraylist.implementation;

public class ArrayList {
    private int size = 0;
    private Object[] elementData = new Object[100];
    // Object 데이터 타입을 생성을 해서 elementData라고 하는 비공개 접근제한자를 갖고 있는 인스턴스 변수에 할당
    // 그리고 그 변수에 배열에 수용할 수 있는 값의 숫자는 100개 이다.

    // elementData는 고정된 크기(100)를 갖는다.
}
```

`Main.java`
```java
package list.arraylist.implementation;

public class Main {
    public static void main(String[] args) {

        ArrayList numbers = new ArrayList();
    }
}
```

<br>
<br>

## 데이터의 추가
#### 데이터를 마지막 위치에 추가한다.
```java
public boolean addLast(Object element) {
    elementData[size] = element;
    size++;
    return true;
}
```

`Main.java`
```java
public static void main(String[] args) {
    ArrayList numbers = new ArrayList();
    numbers.addLast(10);
    numbers.addLast(20);
    numbers.addLast(30);
    numbers.addLast(40);
}
```

<br>

#### 데이터를 중간에 추가
```java
public boolean add(int index, Object element) {
    // 엘리먼트 중간에 데이터를 추가하기 위해서는 끝의 엘리먼트부터 index의 노드까지 뒤로 한칸씩 이동
    for (int i = size-1 ; i >= index ; i--) {
        elementData[i+1] = elementData[i];
    }
    // index에 노드를 추가
    elementData[index] = element;
    // 엘리먼트의 숫자를 1 증가
    size++;
    return true;
}
```

`Main.java`
```java
ArrayList numbers = new ArrayList();
numbers.addLast(10);
numbers.addLast(20);
numbers.addLast(30);
numbers.addLast(40);
numbers.add(1, 15);
```

<br>

#### 데이터를 처음에 추가
```java
public boolean addFirst(Object element){
    return add(0, element);
}
```

`Main.java`
```java
ArrayList numbers = new ArrayList();
numbers.addLast(10);
numbers.addLast(20);
numbers.addLast(30);
numbers.addLast(40);
numbers.add(1, 15);
numbers.addFirst(5);
```

<br>

#### 데이터를 확인하기 위해 toString 구현
```java
public String toString(){
		String str = "[";
		for(int i=0; i<size; i++){
			str += elementData[i];
			if(i < size-1){
				str += ", ";
			}
		}
		return str + "]";
	}
```

`Main.java`
```java
public static void main(String[] args) {
    ArrayList numbers = new ArrayList();
    numbers.addLast(10);
    numbers.addLast(20);
    numbers.addLast(30);
    numbers.addLast(40);
    numbers.add(1, 15);
    numbers.addFirst(5);
    System.out.println(numbers);
}
```

`출력값`
```java
[5, 10, 15, 20, 30, 40]
```

<br>
<br>

## 데이터의 삭제
```java
public Object remove(int index) {
    // 엘리먼트를 삭제하기 전에 삭제할 데이터를 removed 변수에 저장
    Object removed = elementData[index];
    // 삭제된 엘리먼트 다음 엘리먼트부터 마지막 엘리먼트까지 순차적으로 이동해서 빈자리를 채움
    for (int i = index+1 ; i <= size-1 ; i++) {
        elementData[i-1] = elementData[i];
    }
    // 크기를 줄임
    size--;

    // 한칸씩 앞으로 이동후, 가장 마지막에 저장되어 있는 데이터를 null로 만들어줌
    elementData[size] = null;
    return removed; // 삭제했던 값을 알려주기 위해 반환
}   
```

`Main.java`
```java
ArrayList numbers = new ArrayList();
numbers.addLast(10);
numbers.addLast(15);
numbers.addLast(20);
numbers.addLast(30);
numbers.remove(1);     // Index가 1인 데이터를 삭제
System.out.println(numbers);
```

`출력값`
```java
[10, 20, 30]
```

<br>

#### 처음과 끝의 엘리먼트 삭제
```java
public Object removeFirst(){
    return remove(0);
}

public Object removeLast(){
    return remove(size-1);
}
```

`Main.java`
```java
ArrayList numbers = new ArrayList();
numbers.addLast(5);
numbers.addLast(10);
numbers.addLast(20);
numbers.addLast(30);
numbers.addLast(40);
numbers.removeFirst();
numbers.removeLast();
System.out.println(numbers);
```

`출력값`
```java
[10, 20, 30]
```


<br>
<br>


## 데이터 가져오기
- 데이터를 가져오는 기능은 get이다.
- get은 인자로 전달된 인덱스 값을 그대로 배열로 전달한다.
- 배열은 메모리의 주소에 직접 접근하는 랜덤 액세스(random access)이기 때문에 매우 빠르게 처리된다.

```java
public Object get(int index) {
    return elementData[index];
}
```

`Main.java`
```java
ArrayList numbers = new ArrayList();
numbers.addLast(10);
numbers.addLast(20);
numbers.addLast(30);
numbers.addLast(40);
System.out.println(numbers.get(0));
System.out.println(numbers.get(1));
System.out.println(numbers.get(2));
```

`출력값`
```java
10
20
30
```


<br>
<br>


## 엘리먼트의 크기
- 변수로 직접 접근하지 않고, 메소드로 만드는 것은 외부에서 size값을 수정하지 못하게 하기 위해서이다.
- 그래서 size 전역변수는 private으로 선언했다.
```java
public int size(){
  return size;
}
```

`Main.java`
```java
ArrayList numbers = new ArrayList();
numbers.addLast(10);
numbers.addLast(20);
numbers.addLast(30);
System.out.println(numbers.size());
```

`출력값`
```java
3
```

<br>
<br>


## 탐색
- 특정한 값을 가진 엘리먼트의 인덱스 값을 알아내는 방법을 알아보자.
- 값이 있다면 그 값이 발견되는 첫번째 인덱스 값을 리턴하고 값이 없다면 -1을 리턴한다.

```java
// 해당 값이 몇번째 인덱스에 있는지 알려주는 메소드
public Object indexOf(Object o){
    for(int i=0; i < size; i++){
        if(o.equals(elementData[i])){
            return i;
        }
    }
    return -1;  // 찾는 값이 없을 경우 -1을 반환
}
```

`Main.java`
```java
ArrayList numbers = new ArrayList();
numbers.addLast(10);
numbers.addLast(20);
numbers.addLast(30);
System.out.println(numbers.indexOf(20));
System.out.println(numbers.indexOf(40));
```

`출력값`
```java
1       // Index 1에 20이라는 값이 있다는 의미
-1      // 40이라는 값이 없다는 의미
```



<br>
<br>


## 반복
#### 방법1 `for문`
```java
for(int i=0; i<numbers.size(); i++){
    System.out.println(numbers.get(i));
}
```
<br>

#### 방법2  `ListIterator`
```java
public ListIterator listIterator() {
    // ListIterator 인스턴스를 생성해서 리턴
    return new ListIterator();
}
```

<br>

`ListIterator 클래스`
```java
public class ListIterator {
    // 현재 탐색하고 있는 순서를 가르키는 인덱스 값
    private int nextIndex = 0;

    // next 메소르를 호출할 수 있는지를 체크
    public boolean hasNext() {
        // nextIndex가 엘리먼트의 숫자보다 적다면 next를 이용해서 탐색할 엘리먼트가 존재하는 것이기 때문에 true를 리턴
        return nextIndex < size();
    }

    // 순차적으로 엘리먼트를 탐색해서 리턴
    public Object next() {

        /*
        Object returnData = elementData[nextIndex];
        nextIndex++;
        return returnData;
        */

        // 위의 3줄짜리 코드를 아래 1줄로 작성할 수 있다.
        // nextIndex에 해당하는 엘리먼트를 리턴하고 nextIndex의 값을 1 증가
        return elementData[nextIndex++];
    }
}
```

<br>

#### `previous`, `hasPrevious`
```java
// previous 메소드를 호출해도 되는지를 체크
public boolean hasPrevious(){
    // nextIndex가 0보다 크다면 이전 엘리먼트가 존재한다는 의미
    return nextIndex > 0;
}

// 순차적으로 이전 노드를 리턴
public Object previous(){
    // 이전 엘리먼트를 리턴하고 nextIndex의 값을 1감소
    return elementData[--nextIndex];
}
```

<br>

#### Iterator  `add`, `remove`
```java
// 현재 엘리먼트를 추가
public void add(Object element){
    ArrayList.this.add(nextIndex++, element);
}

// 현재 엘리먼트를 삭제 - next()를 한번이라도 호출한 후에 실행해야 오류가 안난다.
public void remove(){
    ArrayList.this.remove(nextIndex-1);
    nextIndex--;
}
```
