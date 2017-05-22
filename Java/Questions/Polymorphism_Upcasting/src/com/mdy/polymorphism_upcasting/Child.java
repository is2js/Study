package com.mdy.polymorphism_upcasting;

public class Child extends Parent {
	
	public String schoolName = null;
	
	public Child(String name1, int age1, String schoolName1){
		name = name1;
		age = age1;
		schoolName = schoolName1;
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
