package com.mdy.polymorphism_upcasting;

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
