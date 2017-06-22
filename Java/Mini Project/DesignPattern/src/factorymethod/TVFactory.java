package factorymethod;

public class TVFactory extends FactoryMethod {

	@Override
	public Product make() {
		return new TV();
	}
}
