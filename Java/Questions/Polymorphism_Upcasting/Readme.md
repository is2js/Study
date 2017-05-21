# 다형성 질문
---
#### <질문내용>

다형성을 이용하여
자식 클래스를 사용해 인스턴스를 만들 때, 부모 클래스 타입으로 생성하면
그렇게 만들어진 인스턴스는 자식 클래스에 있는 메소드들은 가려진다(?)고 이해를 했었습니다.
그래서 생성된 인스턴스를 입력하고 점(.)을 찍으면 부모 클래스에 선언되어 있는 메소드들과 변수들만 보여진다고 생각했었습니다.

여기서 c3 인스턴스는 부모의 타입으로 생성되었기 때문에
goSomewhere( ); 메소드를 호출하면 자식 클래스에 있는 메소드가 가려지기 때문에
Parent에 있는 goSomewhere( ); 메소드가 호출되어
“회사에 갑니다.” 라는 문장이 출력되어야 한다고 생각했습니다.

그러나 Child에 있는 goSomewhere( ); 메소드가 호출되어 “학교에 갑니다.” 가 출력되어서 질문드립니다.

제가 다형성을 잘못 이해하고 있는 것 같은데 답변 부탁드리겠습니다!

---
#### <답변내용 정리>

상속받은 자식객체의 타입을 부모객체의 타입으로 만들어줄 수 있다.
타입을 대체함으로써 얻어지는 이익은 내가 쓰는 핵심기능만 사용할 수 있게 해준다.
그러나 자식클래스에서 오버라이드 한 것은 가려지지 않는다.

그래서 c3 인스턴스가 Parent 타입으로 선언되어있기는 하지만 Child 클래스에서 goSomewhere( ); 메소드가 Override 되어 있기 때문에 “학교에 갑니다.”가 출력되는 것이 맞다.

 Child 클래스에서 선언되어 있는 public String schoolName;은  Parent의 타입으로 생성된 c3 가 main에서 c3.schoolName = “수정한 학교이름” 으로 사용할 수 없다.
 억지로 사용하고 싶다면 ((Child) c3).schoolName = “수정한 학교이름”으로 사용해야 한다.
 타입을 다시 DownCasting해야 사용할 수 있다.

 그러므로 업캐스팅을 하더라도 속성 자체가 바뀌는 것은 아니다.

**(View.OnClickListener) this) 살펴보기**
 - View의 내부클래스(InnerClass)가 OnClickListener이고, this는 MainActivity이다.
 - View.OnClickListener는 인터페이스라 내부에 선언만 되어 있고 아무런 구현이 되어 있지 않다.
   그렇다면 (View.OnClickListener) this)에서 MainActivity가 아무것도 구현이 안되어 있는 interface로 덮어 씌워지게 되어지지만 MainActivity에서 Override한 내용이 그대로 유지되기에 문제가 안된다. 핵심이 그대로 유지가 된다.
 - View.OnClickListener 내부를 살펴보면 void onClick(View v);만 선언되어 있다.



```Java
package com.mdy.freetest;

public class FreeTestMain {

	public static void main(String[] args){

		Parent p1 = new Parent("father1", 61);
		Child c2 = new Child("child2", 22, "2-School");
		Parent c3 = new Child("child3", 23, "3-School");

		p1.showInformation();
		p1.goSomewhere();
		p1.liveWhere();

		System.out.println();

		c2.showInformation();
		c2.goSomewhere();
		c2.liveWhere();

		System.out.println();

		c3.showInformation();
		c3.goSomewhere();
		c3.liveWhere();

		((Child) c3).schoolName = "333-School";
		c3.showInformation();
		c3.goSomewhere();
		c3.liveWhere();
	}
}
```

```Java
package com.mdy.freetest;

public class Parent {

	public String name;
	public int age;

	public Parent(String name, int age){
		this.name = name;
		this.age = age;
	};

	public void showInformation(){
		System.out.println("이름은 "+ name + " 입니다.");
		System.out.println("나이는 "+ age + " 입니다.");
	}

	public void goSomewhere(){
		System.out.println("회사에 갑니다.");
	}

	public void liveWhere(){
		System.out.println("서울에 살고 있습니다.");
	}
}
```

```Java
package com.mdy.freetest;

public class Child extends Parent {

	public String schoolName;

	public Child(String name, int age, String schoolName){
		super(name, age);
		//super(name, age); 없이 아래처럼 사용하면 에러난다.
  	/*	this.age = age;
		this.name = name;*/
		this.schoolName = schoolName;
	}

	@Override
	public void goSomewhere(){
		System.out.println("학교에 갑니다.");
	}

	@Override
	public void showInformation(){
		System.out.println("이름은 "+ name + " 입니다.");
		System.out.println("나이는 "+ age + " 입니다.");
		System.out.println("학교는 "+ schoolName + " 입니다.");
	}
}
```
