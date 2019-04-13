package myUber.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import myUber.Uber;

class TimeTest {

	@Test
	void test() {
		Uber uber = Uber.getInstance();
		assertEquals(uber.getTime().getTime(),0);
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(uber.getTime().getTime(),1);
	}
	
}
