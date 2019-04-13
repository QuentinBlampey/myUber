package myUber.Car;

import java.util.List;

import myUber.GPS;
import myUber.ID.Id;
import myUber.ID.IdGenerator;

public class Standard implements Car {
	
	protected static int seats = 4;
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
	
	public Standard( Id id) {
		super();
		this.id = id;
	}

	public Standard(List<Id> drivers, GPS gps) {
		super();
		IdGenerator ig = IdGenerator.getInstance();
		this.id = ig.getId("Standard");
		this.drivers = drivers;
		this.gps = gps;
	}
	
	public static void main(String[] args) {
		Car c1 = new Standard( IdGenerator.getInstance().getId("Standard"));
		Car c2 = new Standard( IdGenerator.getInstance().getId("Standard") );
		System.out.println(c1.getId().getNum());
		System.out.println(c2.getId().getNum());
	}

	@Override
	public String toString() {
		return "Standard " + id.getNum() + " " + gps.toString();
	}
	
	
	
}