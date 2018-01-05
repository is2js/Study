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
