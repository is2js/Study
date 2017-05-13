package com.mdy.rockscissorpaper;

import java.util.Scanner;

public class User extends Player {
	
	public Pick showPick(){
		Scanner scanner = new Scanner(System.in);
		String strPick = scanner.nextLine();
		
		pick = new Pick(strPick);
		
		return pick;
	}

}