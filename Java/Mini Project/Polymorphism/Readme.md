### 다형성 (Polymorphism)

##### <설명>
- 상속받은 자식객체의 타입을 부모객체의 타입으로 만들어줄 수 있다.
- 타입을 대체함으로써 얻어지는 이익은 내가 쓰는 핵심기능만 사용할 수 있게 해준다.
- 그러나 자식클래스에서 오버라이드 한 것은 가려지지 않는다.

##### <예제>

```java
package com.mdy.java.polymorphism;

public class PolyMain {

	public static void main(String[] args) {
		Father son = new Son();
		//타입을 부모로 대체하는 순간 자식의 메소드 및 변수들이 가려진다.
		son.setName("쥬니어");
		System.out.println(son.getName());

		Father daughter = new Daughter();
	}
}
```

```java
package com.mdy.java.polymorphism;

public class Father {
	public String name;
	private int age;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = "p"+name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
```

```java
package com.mdy.java.polymorphism;

public class Son extends Father{
	@Override
	public void setName(String name) {
		super.name = "s" + name;
	}
}
```
