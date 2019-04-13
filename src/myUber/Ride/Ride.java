package myUber.Ride;

import myUber.Customer;
import myUber.GPS;

/**
 * Interface trajet. 
 */

public interface Ride {
	
	public String getStatus();
	public void setStatus(String status);
	public double getPrice();
	public void setPrice(double price);
	public GPS getGps1();
	public void setGps1(GPS gps);
	public GPS getGps2();
	public void setGps2(GPS gps);
	public void setCustomer(Customer customer);
	public Customer getCustomer();
	public int getMark();
	public void setMark(int mark);
	
}
