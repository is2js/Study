package com.mdy.java.extend;

import java.util.Scanner;

import com.mdy.java.extend.city.hospital.Doctor;
import com.mdy.java.extend.city.hospital.Hospital;
import com.mdy.java.extend.city.hospital.Nurse;
import com.mdy.java.extend.city.hospital.Patient;

public class ExtendMain {

	public static void main(String[] args) {
//		Father father = new Father();
//		father.process();
//		father.goDestination();
		
//		Police police = new Police();
//		police.color = "�Ķ���";
//		police.floor = "2F";
		
/*		
		������ �ϳ� �����,
		�ǻ縦 3��
		��ȣ�� 6�� - ���� �ٸ� �������� �ѹ��� �����δ�.
		ȯ�� 21��
		�� �ǻ簡 ȯ�ڸ� 7���� �����ϴ� ���α׷��� �ۼ��ϼ���.
*/		
		
		
		Hospital hospital = new Hospital();
		hospital.color = "�Ͼ��";
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
			Scanner scanner = new Scanner(System.in);
			patients[i].setName(scanner.nextLine());
		}
		
		// �ǻ� 3���� ȯ�� 7������ �����Ѵ�.

		/* �̷��� �ϸ� �ȵȴ�.
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

		// ��ȣ�� 6�� - ���� �ٸ� �������� �ѹ��� �����δ�.
		
		for(int i=0; i<6; i++){
			if(i%4==0){
				nurses[i].move("����");
			} else if(i%4==1){
				nurses[i].move("����");
			} else if(i%4==2){
				nurses[i].move("����");
			} else {
				nurses[i].move("����");
			}
		}
	}
}