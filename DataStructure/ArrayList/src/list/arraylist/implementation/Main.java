package list.arraylist.implementation;

public class Main {

	public static void main(String[] args) {

		ArrayList numbers = new ArrayList();
		
		// addLast 구현
		numbers.addLast(10);
		numbers.addLast(20);
		numbers.addLast(30);
		numbers.addLast(40);
		
		// add 구현
		numbers.add(1, 15);
		
		// addFirst 구현 ( 첫번째 위치에 추가 )
		numbers.addFirst(5);
		
		
		// remove 구현
//		numbers.remove(4);
//		System.out.println(numbers.remove(1));
		
		// removeFirst 구현
//		numbers.removeFirtst();
		
		
		System.out.println(numbers);
		
		
		// iterator 전에, for문을 이용해보기
		/*for(int i=0 ; i<numbers.size() ; i++){
			System.out.println(numbers.get(i));
		}*/
		
		
		ArrayList.ListIterator li = numbers.listIterator();
		
		// iterator 패턴 - 위에 있는 for문과 비교해보기
		while(li.hasNext()){
			System.out.println(li.next());
		}
		
		
		
		while(li.hasPrevious()){
			System.out.println(li.previous());
		}
		
		
		while(li.hasNext()){
			int number = (int) li.next();
			if(number == 30){
				li.add(35);
			}
			System.out.println(number);
		}
		System.out.println(numbers);
		
		while(li.hasNext()){
			int number = (int) li.next();
			if(number == 20){
				li.remove();
			}
			System.out.println(number);
		}
		System.out.println(numbers);
		
	}
}