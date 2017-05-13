public class ExchangeMain {

	public static void main(String[] args) {

		ExchangeMain price = new ExchangeMain();
		price.price(97324);

	}

	public void price(int money) {

		int temp = money;
		int units[] = { 50000, 10000, 5000, 1000, 500, 100, 50, 10, 5, 1 };


		for (int unit : units) {
			temp = money / unit;
			System.out.println(unit + "Àº " + temp + "°³");
			money = money - (temp * unit);
		}

	}
}