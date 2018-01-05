public class CustomThread implements Runnable{
	
	Account account = new Account();
	
	@Override
	public void run() {
		while(account.balance > 0) {
			account.calculate();
		}
	}
}
