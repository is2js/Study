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
		System.out.println("�б��� ���ϴ�.");
	}
	
	@Override
	public void showInformation(){
		System.out.println("�̸��� "+ name + " �Դϴ�.");
		System.out.println("���̴� "+ age + " �Դϴ�.");
		System.out.println("�б��� "+ schoolName + " �Դϴ�.");
	}
}
