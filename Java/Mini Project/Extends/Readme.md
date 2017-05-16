### 2017.05.16(화) 오전 Mini Test

##### <문제>

###### 병원을 1개 만들고,
###### 의사를 3명, 간호사 6명, 환자 21명을 만든다.

###### 간호사 6명은 각각 다른 방향으로 한번씩 움직이고,
###### 각 의사는 환자를 7명씩 진찰하는 프로그램을 작성하세요.
---

```java
package com.mdy.java.extend;

import com.mdy.java.extend.city.hospital.Doctor;
import com.mdy.java.extend.city.hospital.Hospital;
import com.mdy.java.extend.city.hospital.Nurse;
import com.mdy.java.extend.city.hospital.Patient;

public class ExtendMain {

	public static void main(String[] args) {

		Hospital hospital = new Hospital();
		hospital.color = "하얀색";
		hospital.floor = "5F";
		hospital.door = 2;
		hospital.size = 100;
		hospital.price = 50000;

		Doctor doctors[] = new Doctor[3];
		for(int i=0; i<3; i++){
			doctors[i]  = new Doctor();
		}

		Nurse nurses[] = new Nurse[6];
		for(int i=0; i<6; i++){
			nurses[i]  = new Nurse();
		}

		Patient patients[] = new Patient[21];
		for(int i=0; i<21; i++){
			patients[i]  = new Patient();
		}

		// 의사 3명이 환자 7명씩을 진찰한다.

		/* 이렇게 하면 안된다.
		for(int i=0; i<3 ; i++) {
			if(i==0) {
				for(int j=0; j<7; j++)
					doctors[i].diagnosis(patients[j]);
			} else if(i==1) {
				for(int j=7; j<14; j++)
					doctors[i].diagnosis(patients[j]);
			} else {
				for(j=14; j<21; j++)
					doctors[i].diagnosis(patients[j]);
			}
		}
		 */
		int p_num=0;
		for(int i=0; i<3; i++) {
			for(int j=0; j<7; j++){
				doctors[i].diagnosis(patients[p_num]);
				p_num++;
			}
		}

		// 간호사 6명 - 각각 다른 방향으로 한번씩 움직인다.

		for(int i=0; i<6; i++){
			if(i%4==0){
				nurses[i].move("동쪽");
			} else if(i%4==1){
				nurses[i].move("서쪽");
			} else if(i%4==2){
				nurses[i].move("남쪽");
			} else {
				nurses[i].move("북쪽");
			}
		}
	}
}
```
