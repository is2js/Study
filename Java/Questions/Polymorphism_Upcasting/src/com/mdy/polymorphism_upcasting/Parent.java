package com.mdy.polymorphism_upcasting;

public class Parent {

	public String name = null;
	public int age = 0;
	 
	public Parent(){}   // 이 부분을 빼면 Child 클래스에서 생성자 정의할때 오류난다.
	
	public Parent(String name, int age){
		this.name = name;
		this.age = age;
	}
	
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
