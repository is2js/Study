package com.mdy.mybbs.view;

import com.mdy.mybbs.model.Bbs;

public class BbsDetail {
	
	public void showDetail(Bbs bbs){
		System.out.println("�۹�ȣ:"+bbs.getId());
		System.out.println("����:"+bbs.getTitle());
		System.out.println("�ۼ���:"+bbs.getAuthor());
		System.out.println("�ۼ�����:"+bbs.getDate());
		System.out.println("��ȸ��:"+bbs.getView());
		System.out.println(bbs.getContent());
		
	}
}
