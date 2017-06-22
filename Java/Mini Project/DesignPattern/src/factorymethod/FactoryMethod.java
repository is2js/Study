package factorymethod;

/**
 * 객체를 생성하기 위해 사용되는 추상 클래스
 * 실제 생성은 이 추상클래스를 상속받아서 구현
 * @author MDY
 *
 */

public abstract class FactoryMethod {
	public abstract Product make();
	
}
