package observer;

/**
 * 구독자
 * @author MDY
 *
 */
public class Subscriber implements Observer{
	String name;
	
	public Subscriber(String name){
		this.name = name;
	}
	
	public Subscriber(String name, Publisher publisher){
		this.name = name;
		publisher.addObserver(this);
	}

	@Override
	public void alarm() {
		showEvent();
		
	}
	
	private void showEvent(){
		System.out.println(name + " : 변경사항이 있습니다.");
	}
}
