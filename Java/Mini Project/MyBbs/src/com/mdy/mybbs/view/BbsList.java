package com.mdy.mybbs.view;

import java.util.ArrayList;

import com.mdy.mybbs.model.Bbs;

public class BbsList {
	
	// ��Ͽ� ���� �⺻ ����
	
	
	// ����� �Ѹ���
	public void showList(ArrayList<Bbs> datas){
		for( Bbs bbs : datas ){
			System.out.print(""+bbs.getId()+" | ");
			System.out.print(""+bbs.getTitle()+" | ");
			System.out.print(""+bbs.getAuthor()+" | ");
			System.out.print(""+bbs.getDate()+" | ");
			System.out.println(""+bbs.getView()+"");
		}
	}
	
	// ���� �̵�
	public void goInput(){
		
	}
	
	
	// �˻�
	public void search(String word){
		
	}
	
	// ����
	public void delete(long id){
		
	}
	
	
}