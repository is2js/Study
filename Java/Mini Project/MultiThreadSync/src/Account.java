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
