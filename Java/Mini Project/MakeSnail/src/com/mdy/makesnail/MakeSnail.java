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
	
	// 1. 4���� ������ ����ϴ� ������ �˰���
	public static int[][] drawSnail(int count) {
		int[][] result = new int[count][count];
		int x = -1;   // �迭�� ������ : -1 ���� �����ؾ� �Ʒ� �������� ���ǹ��� �ϳ� ������ �� �ִ�
		int y = 0;    // �迭�� ������
		int direction = UP; // 0 ������, 1�Ʒ�, 2����, 3��
		int number = 1; // ��µǴ� ���ڰ�
		
		int size = count; // �Ź� �ݺ��Ǿ���ϴ� ���� ���� ũ��

		// ���ڰ� ����*���� ���� �۰ų� ���������� �ݺ�
		while (number <= count*count) {
			for (int i = 0; i < size; i++) {
				// �ش� ���⸸ index ���� ���Ѵ�
				switch(direction){ 
					case UP: x++; break;
					case RIGHT: y++; break;
					case DOWN:  x--; break;
					case LEFT: y--; break;
				}
				// �迭�� ���� �ִ´�
				result[y][x] = number;
				number++;
			}
			// ������ȯ
			direction++;
			// ������ ������ ���������� , �Ʒ����� �������� ���Ҷ� size�� 1�� �����Ѵ�.
			if (direction == RIGHT || direction == LEFT) size--;
			// ������ �ѹ��� �������� �ٽ� UP
			if (direction > 3) direction = UP;
		}
		return result;
	}
	
	// 2. +, - ������ ����ϴ� �˰���
	public static int[][] makeSnail(int count){
		int result[][] = new int[count][count];

		int increase = 1; // ������
		int x=0; // ��ǥ
		int y=0;
		
		int number = 0;
		
		int snail = count*2-1; // �ݺ��ϴ� ũ�� = ���� + ���� -1
		
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


















