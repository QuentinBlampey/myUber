package myUber.Ride;

import myUber.Customer;
import myUber.GPS;

public class UberPool implements Ride {

	protected String status;
	protected double price;
	protected GPS gps1;
	protected GPS gps2;
	protected Customer customer;
	protected int mark;
	
	public int getMark() {
		return mark;
	}
	
	public void setMark(int mark) {
		this.mark = mark;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public UberPool(GPS gps1, GPS gps2) {
		super();
		this.status = "attente";
		this.gps1 = gps1;
		this.gps2 = gps2;
		this.mark = -1;
	}
	
	
	
	public UberPool(GPS gps1, GPS gps2, Customer customer) {
		super();
		this.gps1 = gps1;
		this.gps2 = gps2;
		this.customer = customer;
		this.mark = -1;
	}
	@Override
	public String toString() {
		return "UberPool [" + price + " €, " + gps1.toString() + ", to " + gps2.toString() + "]";
	}
	
	

}
