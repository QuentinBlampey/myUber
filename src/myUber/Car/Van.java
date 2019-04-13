package myUber.Car;

import java.util.List;

import myUber.GPS;
import myUber.ID.Id;
import myUber.ID.IdGenerator;

public class Van implements Car {
	
	protected static int seats = 6;
	protected Id id;
	protected List<Id> drivers;
	protected GPS gps;
	
	public int getSeats() {
		return seats;
	}
	
	public Id getId() {
		return id;
	}
	public void setId(Id id) {
		this.id = id;
	}
	public List<Id> getDrivers() {
		return drivers;
	}
	public void setDrivers(List<Id> drivers) {
		this.drivers = drivers;
	}
	public GPS getGps() {
		return gps;
	}
	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public Van(List<Id> drivers, GPS gps) {
		super();
		IdGenerator ig = IdGenerator.getInstance();
		this.id = ig.getId("Van");
		this.drivers = drivers;
		this.gps = gps;
	}

	@Override
	public String toString() {
		return "Van " + id.getNum() + " " + gps.toString();
	}
	
	
}