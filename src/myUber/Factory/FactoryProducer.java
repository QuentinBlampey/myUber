package myUber.Factory;

public class FactoryProducer {
	public static AbstractFactory getFactory(String choice) {
		if (choice.equalsIgnoreCase("Car")) {
			return new CarFactory();
		}
		else if (choice.equalsIgnoreCase("Ride")) {
			return new RideFactory();
		}
		else {
			throw new RuntimeException("String in argument is not an accepted String value");
		}
	}
}
