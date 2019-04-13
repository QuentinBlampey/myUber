package myUber.Factory;

import java.util.List;

import myUber.GPS;
import myUber.Car.Car;
import myUber.ID.Id;
import myUber.Ride.Ride;
import myUber.Ride.UberBlack;
import myUber.Ride.UberPool;
import myUber.Ride.UberVan;
import myUber.Ride.UberX;

public class RideFactory extends AbstractFactory {
	
	@Override
	public Car getCar(String carName, GPS gps, List<Id> drivers) {
		return null;
	}
	
	@Override
	public Ride getRide(String rideName, GPS gps1, GPS gps2 ) {
		if (rideName.equalsIgnoreCase("UberX")) {
			return new UberX(gps1, gps2);
		}
		else if (rideName.equalsIgnoreCase("UberBlack")) {
			return new UberBlack(gps1, gps2);
		}
		else if (rideName.equalsIgnoreCase("UberVan")) {
			return new UberVan(gps1, gps2);
		}
		else if (rideName.equalsIgnoreCase("UberPool")) {
			return new UberPool(gps1, gps2);
		}
		else {
			throw new RuntimeException("String in argument is not an accepted String value");
		}
	}

}
