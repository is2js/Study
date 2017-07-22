package list.arraylist.implementation;

public class ArrayList {
	
	// ArrayList ���������� ��� ������ ä�����°��� size�� ����Ų��.
	private int size = 0;
	
	// ��ü ���������� ����� �迭
	private Object[] elementData = new Object[100];
	// Object ������ Ÿ���� ������ �ؼ� elementData��� �ϴ� ����� ���������ڸ� ���� �ִ� �ν��Ͻ� ������ �Ҵ��� ���̴�.
	// �׸��� �� ������ �迭�� ������ �� �ִ� ���� ���ڴ� 100�� �̴�.
	
	// elementData�� ������ ũ��(100)�� ���´�.
	
	
	
	// addFirst ����
	public boolean addFirst(Object element){
		return add(0, element);
	}
		
	
	// addLast ����
	public boolean addLast(Object element){
		elementData[size] = element;
		size++;
		return true;
	}
	
	
	// add ����
	public boolean add(int index, Object element){
		for (int i = size-1 ; i >= index ; i--){
			elementData[i+1] = elementData[i];
		}
		elementData[index] = element;
		size++;
		return true;
	}
}