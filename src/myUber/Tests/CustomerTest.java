package myUber.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import myUber.Customer;
import myUber.Driver;
import myUber.GPS;
import myUber.Mean;
import myUber.Uber;
import myUber.Car.Car;
import myUber.Factory.AbstractFactory;
import myUber.Factory.FactoryProducer;
import myUber.ID.Id;

class CustomerTest {

	@Test
	void noteDriver() {
		Uber uber = Uber.getInstance();
		AbstractFactory cf = FactoryProducer.getFactory("Car");
		Car c = cf.getCar("Berline", new GPS(0,0), new ArrayList<Id>());
		Driver d = new Driver( "Prenom", Integer.toString(1), c);
		uber.addDriver(d);
		Customer customer = new Customer("a","b",new GPS(0,0));
		customer.noteDriver(d, 3);
		customer.noteDriver(d, 5);
		Customer customer2 = new Customer("a","b",new GPS(0,0));
		customer2.noteDriver(d, 4);
		assertEquals(Mean.mean(uber.getMarks().get(d.getIdD())),4);
	}
	
	@Test
	void bookRide() {
		Customer customer = new Customer("a","b",new GPS(0,0));
		customer.bookRide(new GPS(2,2), 2);
		assertEquals(customer.getMessageBox().size(),4);
	}
	
}
