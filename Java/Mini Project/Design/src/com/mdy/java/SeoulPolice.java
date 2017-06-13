package com.mdy.java;

import java.util.ArrayList;
import java.util.List;

public class SeoulPolice extends Building implement Prison.Callback{	// Prison으로부터 호출당할 interface를 구현
	List<Person> persons   = new ArrayList<>();
	List<Person> criminals = new ArrayList<>();
	// 경찰의 기본 업무
	public void arrest(Person person){
		persons.add(person);
	}
	
	// 오후 1시에 전원 조사
	public void investigate(){
		int count = 0;
		for(Person person : persons) {
			if(count%2 == 0){
				person.evidence = true;
			} else {
				person.evidence = false;
			}
		}
	}
	
	// 배치처리 ... 오후 2시에 현재 잡혀있는 용의자들을
	// 재판을 해서... 유죄인 경우만 교도로로 보낸다.
	public void judge(){
		for(Person person : persons){
			if(person.evidence){
				person.guilty = true;
				criminals.add(person);
			}
		}
	}
	
	
	// 오후 3시.. 이송
	public void transferTo(Prison prison){
		
//		prison.prisoners.add(criminals);
		prison.setPersons(criminals, this);	// this는 SeoulPolice가 넘어온다.
		
	}

	// 통지받은 사항을 출력한다.
	public void getMessage(String message){
		System.out.println(message);
	}
}










class Prison {
	List<Person> prisoners = new ArrayList<>();
	
	public void setPerson(Person person){
		prisoners.add(person);
	}
	
	public void setPersons(List<Person> criminals, SeoulPolice police){
		for(Person person : criminals){
			prisoners.add(person);
			// 시간이 걸리는 작업
			// 언제 끝날지 모르는 작업....
		}
	}
	
	public interface Callback{
		public void getMessage(String meassage);
	}	
	
	
	
}

class Person {
	boolean guilty = false;
	boolean evidence = false;
}





















