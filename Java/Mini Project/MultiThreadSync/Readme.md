# MultiThread 동기화
#### MultiThread 동기화에 대해 실습한다.
- ##### (1) 10개의 쓰레드를 생성한다.
- ##### (2) 공유되는 계좌 잔액 10,000원을 생성한다.
- ##### (3) 각각의 쓰레드는 깨어난 후 1,000원 이내의 랜덤한 금액을 입금 혹은 출금한다.
- ##### (4) 이 때, 계좌의 잔액은 마이너스가 되면 절대 안된다.
- ##### (5) 각각의 쓰레드는 작업이 끝난 후 10초 이내의 랜덤함 시간을 슬립 후 재 작업한다.
- ##### (6) 입/출금 작업 및 전/후의 잔액, 쓰레드가 슬립 및 깨어날 때 로그를 출력한다.

<br>


## 결과 화면
![MultiThreadSync](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/MultiThreadSync/graphics/result.PNG)

<br>


## Source Code
#### [1] Main.class
```java
public class Main {

	public static void main(String[] args) {
		CustomThread customThread = new CustomThread();
		Thread th01 = new Thread(customThread);
		Thread th02 = new Thread(customThread);
		Thread th03 = new Thread(customThread);
		Thread th04 = new Thread(customThread);
		Thread th05 = new Thread(customThread);
		Thread th06 = new Thread(customThread);
		Thread th07 = new Thread(customThread);
		Thread th08 = new Thread(customThread);
		Thread th09 = new Thread(customThread);
		Thread th10 = new Thread(customThread);

		th01.setName("thread01");
		th02.setName("thread02");
		th03.setName("thread03");
		th04.setName("thread04");
		th05.setName("thread05");
		th06.setName("thread06");
		th07.setName("thread07");
		th08.setName("thread08");
		th09.setName("thread09");
		th10.setName("thread10");

		th01.start();
		th02.start();
		th03.start();
		th04.start();
		th05.start();
		th06.start();
		th07.start();
		th08.start();
		th09.start();
		th10.start();
	}
}

```

<br>

#### [2] CustomThread.class
```java
public class CustomThread implements Runnable{

	Account account = new Account();

	@Override
	public void run() {
		while(account.balance > 0) {
			account.calculate();
		}
	}
}
```

<br>

#### [3] Account.class
```java
public class Account {
	int balance = 10000;	// 계좌잔액
	int flag = 0;	// 입금(0), 출금(1) flag
	int money = 0;	// 입금 및 출금하는 금액
	int temp = 0;	// 출금시, 계좌잔액이 마이너스가 되지 않기 위해 사용하는 변수
	int randomSleepTime = 0;	// Thread가 1~10초 사이의 랜덤값으로 Sleep하기 위한 변수

	public synchronized void calculate() {

		flag = (int) (Math.random()*2);	// 0 또는 1 랜덤값 생성 (0:입금 , 1: 출금);
		money = (int) (Math.random()*1000) + 1; // 1~1000 사이의 랜덤 금액
		temp = balance - money;
		Thread thread = Thread.currentThread();	// 현재 쓰레드

		// Before 계좌잔액 출력
		System.out.println("\nBefore 계좌잔액 : " + balance);

		// 입금(flag:0)
		if(flag == 0)
		{
			System.out.println(thread.getName() + " 의 [입금금액] : " + money);
			balance = balance + money;
		}

		// 출금(flag:1)
		else if(flag == 1 && temp >= 0)	// 출금시, 계좌잔액이 마이너스가 되지 않기 위한 조건
		{
			System.out.println(thread.getName() + " 의 [출금금액] : " + money);
			balance = balance - money;
		}

		// After 계좌잔액 출력
		System.out.println("After 계좌잔액 : " + balance);

		// 1~10 초 이내의 랜덤한 시간을 Sleep
		randomSleepTime = (int) (Math.random()*9000 + 1000);// 1~10초 사이의 랜덤 정수값 생성
		System.out.println("\n***** Sleep 시간 : " + randomSleepTime/1000 + "초  *****");

		try {
			Thread.sleep(randomSleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
```
