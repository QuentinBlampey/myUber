package myUber;

import myUber.ID.Id;

/**
 * Sauvegarde d'un trajet fini.
 */

public class RideRegistration {
	
	protected Id driverId;
	protected Id customerId;
	protected Id carId;
	protected GPS gps1;
	protected GPS gps2;
	protected double length;
	protected double duration;
	protected double price;
	
	
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Id getDriverId() {
		return driverId;
	}
	public void setDriverId(Id driverId) {
		this.driverId = driverId;
	}
	public Id getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Id customerId) {
		this.customerId = customerId;
	}
	public Id getCarId() {
		return carId;
	}
	public void setCarId(Id carId) {
		this.carId = carId;
	}
	public GPS getGps1() {
		return gps1;
	}
	public void setGps1(GPS gps1) {
		this.gps1 = gps1;
	}
	public GPS getGps2() {
		return gps2;
	}
	public void setGps2(GPS gps2) {
		this.gps2 = gps2;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}

	public RideRegistration(Id driverId, Id customerId, Id carId, GPS gps1, GPS gps2, double duration, double price) {
		super();
		this.driverId = driverId;
		this.customerId = customerId;
		this.carId = carId;
		this.gps1 = gps1;
		this.gps2 = gps2;
		this.length = gps1.distanceTo(gps2);
		this.duration = duration;
		this.price = price;
	}
	@Override
	public String toString() {
		return "RideRegistration [" + gps1.toString() + " to " + gps2.toString() + ", " + length + " km, " + price + " €, " + duration
				+ " h]";
	}
	
	
}
