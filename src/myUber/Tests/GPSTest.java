package myUber.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import myUber.GPS;

class GPSTest {

	@Test
	void test() {
		GPS gps = new GPS(1,2);
		GPS gps1 = new GPS(2,3);
		assertEquals(gps.distanceTo(gps1), Math.sqrt(2));
	}

}
