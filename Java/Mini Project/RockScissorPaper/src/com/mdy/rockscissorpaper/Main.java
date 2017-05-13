package com.mdy.rockscissorpaper;

public class Main {

	static Pick userPick, compPick;
	
	public static void main(String[] args) {
		User user = new User();
		Computer computer = new Computer();
		userPick = user.showPick();
		compPick = computer.showPick();
		int result = Pick.battle(userPick, compPick);
		
		switch(result){
			case Pick.DRAW :
				System.out.print("비김");
				break;
			case Pick.USER_WIN :
				System.out.print("이김");
				break;
			case Pick.COMP_WIN :
				System.out.print("짐");
				break;
				
				
		}
	}

}
