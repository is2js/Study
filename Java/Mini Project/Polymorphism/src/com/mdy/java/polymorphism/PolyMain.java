package com.mdy.java.polymorphism;

public class PolyMain {

	public static void main(String[] args) {
		Father son = new Son();
		//Ÿ���� �θ�� ��ü�ϴ� ���� �ڽ��� �޼ҵ� �� �������� ��������.
		son.setName("��Ͼ�");
		System.out.println(son.getName());
		
		Father daughter = new Daughter();
	}
}