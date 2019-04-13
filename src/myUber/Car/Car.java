package myUber.Car;

import java.util.List;

import myUber.GPS;
import myUber.ID.Id;

/**
 * Interface voiture.    
 */

public interface Car {
	
	public int getSeats();
	public Id getId();
	public void setId(Id id);
	public List<Id> getDrivers();
	public void setDrivers(List<Id> drivers);
	public GPS getGps();
	public void setGps(GPS gps);
	
}
