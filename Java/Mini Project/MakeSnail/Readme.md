# 달팽이 배열 만들기
---
### <결과 화면>
![결과](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/MakeSnail/MakeSnail_result_01.PNG)
![결과](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/MakeSnail/MakeSnail_result_02.PNG)
---
### <소스 코드>
```Java
package com.mdy.makesnail;

public class MakeSnail {

	public static void main(String args[]) {
		printSnail(makeSnail(5));
		printSnail(drawSnail(6));
		printSnail(makeSnail(7));
		printSnail(drawSnail(8));
		printSnail(makeSnail(9));
		printSnail(drawSnail(10));
	}

	final static int UP = 0;
	final static int RIGHT = 1;
	final static int DOWN = 2;
	final static int LEFT = 3;

	// 1. 4개의 방향을 사용하는 달팽이 알고리즘
	public static int[][] drawSnail(int count) {
		int[][] result = new int[count][count];
		int x = -1;   // 배열의 가로축 : -1 부터 시작해야 아래 로직에서 조건문을 하나 제외할 수 있다
		int y = 0;    // 배열의 세로축
		int direction = UP; // 0 오른쪽, 1아래, 2왼쪽, 3위
		int number = 1; // 출력되는 숫자값

		int size = count; // 매번 반복되어야하는 가로 세로 크기

		// 숫자가 가로*세로 보다 작거나 같을때까지 반복
		while (number <= count*count) {
			for (int i = 0; i < size; i++) {
				// 해당 방향만 index 값이 변한다
				switch(direction){
					case UP: x++; break;
					case RIGHT: y++; break;
					case DOWN:  x--; break;
					case LEFT: y--; break;
				}
				// 배열에 값을 넣는다
				result[y][x] = number;
				number++;
			}
			// 방향전환
			direction++;
			// 방향이 위에서 오른쪽으로 , 아래에서 왼쪽으로 변할때 size는 1씩 감소한다.
			if (direction == RIGHT || direction == LEFT) size--;
			// 방향이 한바퀴 돌았으면 다시 UP
			if (direction > 3) direction = UP;
		}
		return result;
	}

	// 2. +, - 증감을 사용하는 알고리즘
	public static int[][] makeSnail(int count){
		int result[][] = new int[count][count];

		int increase = 1; // 증감값
		int x=0; // 좌표
		int y=0;

		int number = 0;

		int snail = count*2-1; // 반복하는 크기 = 가로 + 세로 -1

		for(int i= snail ; i>0 ; i=i-2){
			for(int j=0 ; j<i ; j++){
				if(j <= i/2){
					if(i != snail || j != 0)
						x += increase;
				}else{
					y += increase;
				}
				result[y][x] = ++number;
			}
			increase *= -1;
		}
		return result;
	}

	public static void printSnail(int result[][]){
		System.out.println("--------- count "+result.length+" -----------");
		for(int i=0; i<result.length ; i++){
			for(int j=0 ; j<result[0].length ; j++){
				String temp = result[i][j] < 10 ? "0"+result[i][j]: ""+result[i][j] ;
				System.out.printf("[%s]",temp);
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
```
---
