package com.mdy.java.polymorphism;

public class Son extends Father{
	@Override
	public void setName(String name) {
		super.name = "s" + name;
	}
}
