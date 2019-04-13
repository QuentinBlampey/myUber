package myUber.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import myUber.Driver;
import myUber.GPS;
import myUber.Car.Car;
import myUber.Factory.AbstractFactory;
import myUber.Factory.FactoryProducer;
import myUber.Ride.Ride;

class AbstractFactoryTest {

	@Test
	void car() {
		AbstractFactory cf = FactoryProducer.getFactory("Car");

		Car c = cf.getCar("Berline", new GPS(0,0), new ArrayList<Driver>());

		Car c1 = cf.getCar("Berline", new GPS(0,0), new ArrayList<Driver>());
		
		assertEquals(c.getGps().getX(),0);
		assertEquals(c.getId().getNum()+1, c1.getId().getNum());
	}
	
	@Test
	void ride() {
		AbstractFactory rf = FactoryProducer.getFactory("Ride");

		Ride r = rf.getRide("Uberblack", 2.0 , new GPS(0,0), new GPS(2,0));
		
		System.out.println(r.getGps1().getY());
	}

}
