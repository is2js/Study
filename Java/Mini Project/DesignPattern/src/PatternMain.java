import factorymethod.FactoryMethod;
import factorymethod.Product;
import factorymethod.TVFactory;
import mail.IProxy;
import mail.Mail;
import mail.Proxy;
import observer.Publisher;
import observer.Subscriber;

public class PatternMain {

	public static void main(String[] args) {
		
		
		/* Proxy 예제 */
		// 메일 대행자 생성
		IProxy mailProxy = new Proxy();
		
		// 편지글 작성
		Mail mail = new Mail();
		
		// 대행자를 통한 발송
		mailProxy.sender(mail);
		
		
		/* Factory 예제 */
		FactoryMethod factory = new TVFactory();
		Product product = factory.make();
		
		
		
		/* Observer 예제 */
		Publisher publisher = new Publisher();
		
		publisher.addObserver(new Subscriber("홍길동"));
//		publisher.addObserver(new Subscriber("이순신"));
		new Subscriber("이순신", publisher);	// 오버로딩을 활용한 생성자 선언
		publisher.addObserver(new Subscriber("장보고"));
		
		publisher.process();
		
		
	}

}
