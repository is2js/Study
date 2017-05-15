# 가위 바위 보 를 하는 프로그램입니다.
---
 - 상속 / Override / static 변수 개념을 사용하였습니다.
 - 클래스를 세분화 하였습니다.
---
![계산](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/RockScissorPaper/rockscissorpaper_calculate.PNG)
```Java
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
```

```Java
package com.mdy.rockscissorpaper;

import java.util.Scanner;

public class Pick {

	String label;
	int value;

	public static final int DRAW = 0;
	public static final int USER_WIN = 1;
	public static final int COMP_WIN = 2;

	public static final String ROCK = "바위";
	public static final String SCISSOR = "가위";
	public static final String PAPER = "보";


	public Pick(String label){
		this.label = label;
		calValue(label);
	}

	private void calValue(String label) {
		switch(label){
			case "가위":
				value = 0;
				break;
			case "바위":
				value = 1;
				break;
			case "보":
				value = 2;
				break;
			default:
				System.out.println("잘못된 선택입니다.");
				Scanner scanner = new Scanner(System.in);
				calValue(scanner.nextLine());
		}
	}

	public static int battle(Pick userPick, Pick compPick){
		int temp = userPick.value - compPick.value;
		if(temp == -1 || temp == 2 ){
			return COMP_WIN;
		}else if(temp == 1 || temp == -2){
			return USER_WIN;
		}else{
			return DRAW;
		}
	}
}
```

```Java
package com.mdy.rockscissorpaper;

public class Player {
	protected Pick pick;

	public Pick showPick(){
		return pick;
	}
}
```

```Java
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
```

```java
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
```

![결과](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/RockScissorPaper/rockscissorpaper_result.PNG)
