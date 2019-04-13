package myUber.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import myUber.ID.Id;
import myUber.ID.IdGenerator;

class IdGeneratorTest {

	@Test
	void test() {
		IdGenerator idGenerator = IdGenerator.getInstance();
		assertEquals(idGenerator.getId("driver").getNum(),0);
		IdGenerator idGenerator2 = IdGenerator.getInstance();
		assertEquals(idGenerator2.getId("driver").getNum(),1);
		assertEquals(idGenerator.getId("driver").getNum(),2);
	}
	
	@Test
	void test2() {
		IdGenerator ig = IdGenerator.getInstance();
		Id i1 = ig.getId("Berline");
		Id i2 = ig.getId("Berline");
		assertEquals(i1.getNum(),0);
		assertEquals(i2.getNum(),1);
		IdGenerator ig2 = IdGenerator.getInstance();
		assertEquals(ig2.getId("Berline").getNum(),2);
	}
	
}
