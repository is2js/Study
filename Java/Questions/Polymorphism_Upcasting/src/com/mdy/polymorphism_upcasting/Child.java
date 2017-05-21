package com.mdy.polymorphism_upcasting;

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
