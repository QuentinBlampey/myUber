package myUber.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import myUber.Customer;
import myUber.Driver;
import myUber.GPS;
import myUber.Uber;
import myUber.Car.Car;
import myUber.Factory.AbstractFactory;
import myUber.Factory.FactoryProducer;
import myUber.ID.Id;
import myUber.Ride.Ride;
import myUber.Ride.UberX;

class UberTest {

	@Test
	void closest() {
		GPS start = new GPS(0,0);
		List<GPS> destinations = new ArrayList<GPS>();
		destinations.add(new GPS(1,1));
		destinations.add(new GPS(1,2));
		destinations.add(new GPS(0,4));
		destinations.add(new GPS(-1,-2));
		assertEquals(Uber.closest(start, destinations).getX(), 1);
		assertEquals(Uber.closest(start, destinations).getY(), 1);
	}
	
	@Test
	void closestCustomer() {
		GPS start = new GPS(0,0);
		List<Ride> rides = new ArrayList<Ride>();
		rides.add(new UberX( new GPS(1,2), new GPS(2,4), new Customer("a","a", new GPS(1,2))));
		rides.add(new UberX( new GPS(3,4), new GPS(1,4), new Customer("b","b", new GPS(3,4))));
		rides.add(new UberX( new GPS(-1,-1), new GPS(2,-6), new Customer("c","c", new GPS(-1,-1))));
		assertEquals(Uber.closestCustomer(start, rides).getName(), "c");
	}
	
	@Test
	void closestRideDestination() {
		GPS start = new GPS(0,0);
		List<Ride> rides = new ArrayList<Ride>();
		rides.add(new UberX( new GPS(1,2), new GPS(2,4), new Customer("a","a", new GPS(1,2))));
		rides.add(new UberX( new GPS(3,4), new GPS(1,4), new Customer("b","b", new GPS(3,4))));
		rides.add(new UberX( new GPS(-1,-1), new GPS(2,-6), new Customer("c","c", new GPS(-1,-1))));
		assertEquals(Uber.closestRideDestination(start, rides).getCustomer().getName(), "b");
	}
	
	@Test
	void orderCustomerStart() {
		GPS start = new GPS(0,0);
		List<Ride> rides = new ArrayList<Ride>();
		rides.add(new UberX( new GPS(1,2), new GPS(2,4), new Customer("a","a", new GPS(1,2))));
		rides.add(new UberX( new GPS(3,4), new GPS(1,4), new Customer("b","b", new GPS(3,4))));
		rides.add(new UberX( new GPS(-1,-1), new GPS(2,-6), new Customer("c","c", new GPS(-1,-1))));
		assertEquals(Uber.orderCustomerStart(start, rides).get(0).getName(), "c");
		assertEquals(Uber.orderCustomerStart(start, rides).get(1).getName(), "a");
		assertEquals(Uber.orderCustomerStart(start, rides).get(2).getName(), "b");
	}
	
	@Test
	void orderRidesDestination() {
		GPS start = new GPS(0,0);
		List<Ride> rides = new ArrayList<Ride>();
		rides.add(new UberX( new GPS(1,2), new GPS(2,4), new Customer("a","a", new GPS(1,2))));
		rides.add(new UberX( new GPS(3,4), new GPS(1,4), new Customer("b","b", new GPS(3,4))));
		rides.add(new UberX( new GPS(-1,-1), new GPS(2,-6), new Customer("c","c", new GPS(-1,-1))));
		assertEquals(Uber.orderRidesDestination(start, rides).get(0).getCustomer().getName(), "b");
		assertEquals(Uber.orderRidesDestination(start, rides).get(1).getCustomer().getName(), "a");
		assertEquals(Uber.orderRidesDestination(start, rides).get(2).getCustomer().getName(), "c");
	}
	
	@Test
	void orderList() {
		GPS start = new GPS(0,0);
		List<GPS> destinations = new ArrayList<GPS>();
		destinations.add(new GPS(1,3));
		destinations.add(new GPS(2,-1));
		destinations.add(new GPS(0,4));
		destinations.add(new GPS(-1,-2));
		List<GPS> orderList = Uber.orderList(start, destinations);
		assertEquals(orderList.get(0).getX(), 2);
		assertEquals(orderList.get(1).getX(), -1);
		assertEquals(orderList.get(2).getX(), 1);
		assertEquals(orderList.get(3).getX(), 0);
	}
	
	@Test
	void distanceOrder() {
		GPS start = new GPS(0,0);
		List<GPS> destinations = new ArrayList<GPS>();
		destinations.add(new GPS(1,3));
		destinations.add(new GPS(2,-1));
		destinations.add(new GPS(0,4));
		destinations.add(new GPS(-1,-2));
		double distance = Uber.distanceOrder(start, Uber.orderList(start, destinations));
		assertEquals(distance, Math.sqrt(5) + Math.sqrt(10) + Math.sqrt(29) + Math.sqrt(2));
	}
	
	@Test
	void poolDistance() {
		GPS start = new GPS(0,0);
		List<GPS> starts = new ArrayList<GPS>();
		starts.add(new GPS(1,3));
		starts.add(new GPS(2,-1));
		starts.add(new GPS(0,4));
		starts.add(new GPS(-1,-2));
		List<GPS> destinations = new ArrayList<GPS>();
		destinations.add(new GPS(0,3));
		destinations.add(new GPS(0,2));
		destinations.add(new GPS(0,1));
		destinations.add(new GPS(0,0));
		double distance = Uber.poolDistance(start, starts, destinations);
		assertEquals(distance, Math.sqrt(5) + Math.sqrt(10) + Math.sqrt(29) + Math.sqrt(2) + 4);
	}
	
	@Test
	void findCar() {
		Uber uber = Uber.getInstance();
		AbstractFactory cf = FactoryProducer.getFactory("Car");
		Car c = cf.getCar("Berline", new GPS(1,0), new ArrayList<Id>());
		uber.getCars().add( c );
			
		Car c1 = cf.getCar("Standard", new GPS(0,0), new ArrayList<Id>());
		uber.getCars().add( c1 );
		
		Car c3 = cf.getCar("Standard", new GPS(-1,0), new ArrayList<Id>());
		uber.getCars().add( c3 );
		
		Car c2 = cf.getCar("Van", new GPS(1,0), new ArrayList<Id>());
		uber.getCars().add( c2 );
		assertEquals(uber.findCar("standard0").getGps().getX(),0);
		assertEquals(uber.findCar("standard1").getGps().getX(),-1);
	}
}
