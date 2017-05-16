package com.mdy.java.extend.city.hospital;

import com.mdy.java.extend.city.design.People;

public class Doctor extends People{
	
	public void diagnosis(Patient patient){
		System.out.println(patient.getName() + "어떤 환자를 진찰한다.");
	}
}
