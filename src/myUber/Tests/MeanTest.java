package myUber.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import myUber.Mean;

class MeanTest {

	@Test
	void test() {
		List<Integer> l = new ArrayList<Integer>();
		assertEquals(Mean.mean(l),0);
		l.add(2);
		l.add(3);
		assertEquals(Mean.mean(l),5/2);
	}

}
