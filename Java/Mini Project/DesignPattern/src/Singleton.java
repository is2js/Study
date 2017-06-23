/**
 * �̱��� ������ �˾ƺ���.
 * @author MDY
 *
 */
public class Singleton {
	
	// 3. �ڱ� �ڽ��� ��Ƶδ� ��������� �����
	private static Singleton instance = null;
	
	// 1. �����ڸ� private ���� ����� �ܺο��� ������ �� ������ �����Ѵ�.
	private Singleton(){
		
	}
	
	
	// 2. �ܺο��� ������ �� �����Ƿ�
	// ���ο��� ������ �� ����� �� �ֵ��� �������̽��� �����ؾ� �Ѵ�.
	// �ܺο��� new�� �ȵǹǷ� �Լ��� static���� �����ؾ� �Ѵ�.
	public static Singleton getInstance(){
		// 4. ��������� instance �� ������ instance�� return �Ѵ�.
		if(instance == null){
			instance = new Singleton();
		}
		return instance;
	}
}