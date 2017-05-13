package com.mdy.rockscissorpaper;

public class Computer extends Player {
	
	public Pick showPick() {
		int numbPick = (int)(Math.random()*3);
		
		if(numbPick == 0){
			pick = new Pick(Pick.ROCK);
			System.out.printf("Com: %s %n",Pick.ROCK);
		}else if(numbPick == 1){
			pick = new Pick(Pick.SCISSOR);
			System.out.printf("Com: %s %n",Pick.SCISSOR);
		}else if(numbPick == 2){
			pick = new Pick(Pick.PAPER);
			System.out.printf("Com: %s %n",Pick.PAPER);
		}
		
		if(pick == null){
			System.out.println("pick이 null입니다.");
		}
		
		return pick;
	}

}
