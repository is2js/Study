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
		
		
		/* Proxy ���� */
		// ���� ������ ����
		IProxy mailProxy = new Proxy();
		
		// ������ �ۼ�
		Mail mail = new Mail();
		
		// �����ڸ� ���� �߼�
		mailProxy.sender(mail);
		
		
		/* Factory ���� */
		FactoryMethod factory = new TVFactory();
		Product product = factory.make();
		
		
		
		/* Observer ���� */
		Publisher publisher = new Publisher();
		
		publisher.addObserver(new Subscriber("ȫ�浿"));
//		publisher.addObserver(new Subscriber("�̼���"));
		new Subscriber("�̼���", publisher);	// �����ε��� Ȱ���� ������ ����
		publisher.addObserver(new Subscriber("�庸��"));
		
		publisher.process();
		
		
	}

}