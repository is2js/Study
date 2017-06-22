package observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 발행자
 * @author MDY
 *
 */
public class Publisher {
	// Observer 저장소
	List<Observer> observers = new CopyOnWriteArrayList<>(); // <- 동기화를 내부적으로 지원하는 List
	
	// Observer 추가하기
	public void addObserver(Observer obs){
		observers.add(obs);
	}
	
	public void removeObserver(Observer obs){
		observers.remove(obs);
	}
	
	// 값을 처리하는 자체 처리함수...
	public void process(){
		// 처리..
		// 이벤트 발생시...
		while(true){
			
			notice();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 변경사항 알리기
	private void notice(){
		for(Observer observer : observers){
			observer.alarm();
		}
	}
}