package myUber.Factory;

import java.util.List;

import myUber.GPS;
import myUber.Car.Berline;
import myUber.Car.Car;
import myUber.Car.Standard;
import myUber.Car.Van;
import myUber.ID.Id;
import myUber.Ride.Ride;

public class CarFactory extends AbstractFactory {
	
	@Override
	public Car getCar(String carName, GPS gps, List<Id> drivers) {
		if (carName.equalsIgnoreCase("Berline")) {
			return new Berline(drivers, gps);
		}
		else if (carName.equalsIgnoreCase("Standard")) {
			return new Standard(drivers, gps);
		}
		else if (carName.equalsIgnoreCase("Van")) {
			return new Van(drivers, gps);
		}
		else {
			throw new RuntimeException("String in argument is not an accepted String value");
		}
	}
	
	@Override
	public Ride getRide(String rideName, double price, GPS gps1, GPS gps2 ) {
		return null;
		
	}
}
