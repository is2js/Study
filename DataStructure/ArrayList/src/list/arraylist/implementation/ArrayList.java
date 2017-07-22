package list.arraylist.implementation;

public class ArrayList {
	
	// ArrayList 내부적으로 몇개의 값들이 채워졌는가를 size가 가리킨다.
	private int size = 0;
	
	// 객체 내부적으로 사용할 배열
	private Object[] elementData = new Object[100];
	// Object 데이터 타입을 생성을 해서 elementData라고 하는 비공개 접근제한자를 갖고 있는 인스턴스 변수에 할당한 것이다.
	// 그리고 그 변수에 배열에 수용할 수 있는 값의 숫자는 100개 이다.
	
	// elementData는 고정된 크기(100)를 갖는다.
	
	
	
	// addFirst 구현
	public boolean addFirst(Object element){
		return add(0, element);
	}
		
	
	// addLast 구현
	public boolean addLast(Object element){
		elementData[size] = element;
		size++;
		return true;
	}
	
	
	// add 구현
	public boolean add(int index, Object element){
		for (int i = size-1 ; i >= index ; i--){
			elementData[i+1] = elementData[i];
		}
		elementData[index] = element;
		size++;
		return true;
	}
	
	// toString 메소드 구현
	public String toString(){
		String str = "[";
		for(int i=0; i<size; i++){
			str += elementData[i];
			if(i < size-1){
				str += ", ";
			}
		}
		return str + "]";
	}
	
	// remove 구현
	public Object remove(int index){
		// 삭제할 값을 반환해주기 위해 removed 변수에 저장
		Object removed = elementData[index];
		for (int i = index ; i < size-1  ; i++){
			elementData[i] = elementData[i+1];
		}
		size--;
		
		// 한칸씩 앞으로 이동후, 가장 마지막에 저장되어 있는 데이터를 null로 만들어준다.
		elementData[size] = null;
		
		return removed;	// 삭제했던 값을 알려주기 위해 반환
	}
	
	
	// removeFirst 구현
	public Object removeFirst(){
		return remove(0);
	}
	
	// removeLast 구현
	public Object removeLast(){
		return remove(size-1);
	}
	
	// get 구현 - ArrayList의 가장 큰 장점
	public Object get(int index){
		return elementData[index];
	}
	
	// size 구현
	// 변수로 직접 접근하지 않고, 메소드로 만드는 것은 외부에서 size값을 수정하지 못하게 하기 위해서이다.
	// 그래서 위에 size는 private으로 되어 있다.
	public int size(){
		return size;
	}
	
	
	// size indexOf - 해당 값이 몇번째 인덱스에 있는지 알려주는 메소드
	public int indexOf(Object o){
		for(int i=0 ; i<size ; i++){
			if(o.equals(elementData[i])){
				return i;
			}
		}
		return -1;	// 찾는 값이 없을 경우
	}
	
	
	// ListIterator 구현
	public ListIterator listIterator(){
		return new ListIterator();
	}
	
	public class ListIterator{
		private int nextIndex = 0;
		
		// hasNext()
		public boolean hasNext(){
			return nextIndex < size();
		}
		
		// next()
		public Object next(){
//			Object returnData = elementData[nextIndex];
//			nextIndex++;
//			return returnData;
			
			// 위의 코드를 더 간단하게 작성
			return elementData[nextIndex++];
		}
		
		//hasPrevious()
		public boolean hasPrevious(){
			return nextIndex > 0;
		}
		
		// previous()
		public Object previous(){
			return elementData[--nextIndex];
		}
		
		
		// iterator add
		public void add(Object element){
			ArrayList.this.add(nextIndex++, element);
		}
		
		
		// iterator remove - next()를 한번이라도 호출한 후에 실행해야 오류가 안난다.
		public void remove(){
			ArrayList.this.remove(nextIndex-1);
			nextIndex--;
		}
		
	}
}