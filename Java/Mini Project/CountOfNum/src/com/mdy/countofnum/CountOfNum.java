package com.mdy.countofnum;

public class CountOfNum {

	public static void main(String[] args) {
		
		System.out.println("1부터 10000까지 8이라는 숫자가 총 몇번 나오는가?");
		System.out.println( count(10000,8) + "번");
	}
	
	public static int count(int limit, int target) {
		int result = 0; 
//		String unit = target + ""; // 정수를 문자열로 형변환 - 방법(1)
//		String unit = (String) target; // 이렇게 하면 안됨.
		String unit = Integer.toString(target);  // 정수를 문자열로 형변환 - 방법(2)
		
		for(int i=0 ; i<=limit ; i++)
		{
			String temp = Integer.toString(i);
			String array[] = temp.split("");
			
			for(String item : array)
			{
				if(item.equals(unit))
				{
					result = result + 1;
				}
			}
		}
		return result;
	}
}
