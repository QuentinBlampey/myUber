package myUber.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import myUber.Customer;
import myUber.Driver;
import myUber.GPS;
import myUber.Uber;
import myUber.Car.Car;
import myUber.Factory.AbstractFactory;
import myUber.Factory.FactoryProducer;
import myUber.ID.Id;

class DriverTest {

	@Test
	void test() {
		Uber uber = Uber.getInstance();
		AbstractFactory cf = FactoryProducer.getFactory("Car");

		Car c = cf.getCar("Berline", new GPS(0,0), new ArrayList<Id>());
		uber.getCars().add( c );
		uber.addDriver( new Driver( "Prenom", Integer.toString(1), c));
			
		Car c1 = cf.getCar("Standard", new GPS(0,0), new ArrayList<Id>());
		uber.getCars().add( c1 );
		uber.addDriver( new Driver( "Prenom", Integer.toString(2), c1));
		
		Car c2 = cf.getCar("Van", new GPS(0,0), new ArrayList<Id>());
		uber.getCars().add( c2 );
		uber.addDriver( new Driver( "Prenom", Integer.toString(3), c2));
		
		Customer customer = new Customer("Nathan","Moret", new GPS(0,0));
		customer.bookRide(new GPS(3,-6), 1);
		uber.addCustomer(customer);
		
		Customer customer2 = new Customer("Charles","Combelles", new GPS(0,0));
		customer2.bookRide(new GPS(1,15), 1);
		uber.addCustomer(customer2);
		
		Customer customer3 = new Customer("Justine","Taillebois", new GPS(-2,0));
		customer3.bookRide(new GPS(-10,0), 1);
		uber.addCustomer(customer3);
		
		Customer customer4 = new Customer("Natacha","Petit", new GPS(0,2));
		customer4.bookRide(new GPS(-1,0), 1);
		uber.addCustomer(customer4);
		
		Customer customer5 = new Customer("Clément","Stutzmann", new GPS(0,-3));
		customer5.bookRide(new GPS(10,15), 1);
		uber.addCustomer(customer5);
		
		customer.chooseAndPayRide(0);
		customer2.chooseAndPayRide(0);
		customer3.chooseAndPayRide(3);
		customer4.chooseAndPayRide(1);
		customer5.chooseAndPayRide(1);
		
		for (Driver driver : uber.getDrivers()) {
			driver.start();
		}
		
		try {
			Thread.sleep(20000);
			assertEquals(customer.getGps().getX(), 3);
			assertEquals(customer2.getGps().getX(), 1);
			assertEquals(customer3.getGps().getX(), -10);
			assertEquals(customer4.getGps().getX(), -1);
			assertEquals(customer5.getGps().getX(), 0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
