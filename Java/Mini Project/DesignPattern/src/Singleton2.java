
public class Singleton2 {

	// 3. �ڱ� �ڽ��� ��Ƶδ� ��������� �����
	private static Singleton2 instance = new Singleton2();
	
	// 1. �����ڸ� private ���� ����� �ܺο��� ������ �� ������ �����Ѵ�.
	private Singleton2(){
		
	}
	
	
	// 2. �ܺο��� ������ �� �����Ƿ�
	// ���ο��� ������ �� ����� �� �ֵ��� �������̽��� �����ؾ� �Ѵ�.
	// �ܺο��� new�� �ȵǹǷ� �Լ��� static���� �����ؾ� �Ѵ�.
	public static Singleton2 getInstance(){
		// 4. ��������� instance �� ������ instance�� return �Ѵ�.
		return instance;
	}
}