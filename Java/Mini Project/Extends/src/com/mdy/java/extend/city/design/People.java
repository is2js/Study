package com.mdy.java.extend.city.design;

public abstract class People {

	private String gender;
	private String name;
	private int age;
	private String skinColor;
	private String hairColor;
	private int height;
	private int weight;
	
	public void move(String direction){
		System.out.println(direction+"�������� �����δ�.");
	}
	
	
	public String getName(){
		return name;
	}
	
	public void setName(String value){
		name = value;
	}
	
}
