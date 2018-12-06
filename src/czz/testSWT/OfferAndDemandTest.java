package czz.testSWT;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import czz.swt.Demand;

class OfferAndDemandTest {
	
	@Test
	void testAddValue() {
		Demand demand = new Demand(0);
		assertFalse(demand.addValue(0, 1));
		assertFalse(demand.addValue(1, 0));
		assertTrue(demand.addValue(1, 2));
		assertEquals(2, demand.getValueById(1));
	}

	@Test
	void testReduceValue() {
		HashMap<Integer, Integer> values = new HashMap<Integer, Integer>();
		values.put(1, 2);
		values.put(2, 3);
		values.put(3, 4);
		Demand demand = new Demand(0, values);
		assertFalse(demand.reduceValue(0, 1));
		assertFalse(demand.reduceValue(1, 0));
		assertTrue(demand.reduceValue(1, 1));
		assertEquals(1, demand.getValueById(1));
		assertFalse(demand.reduceValue(1, 2));
		assertEquals(1, demand.getValueById(1));
		assertTrue(demand.reduceValue(2, 3));
		assertEquals(0, demand.getValueById(2));
		Integer[] expectedArr = {1, 3};
		Integer[] actualArr = new Integer[2];
		demand.getResList().toArray(actualArr);
		assertArrayEquals(expectedArr, actualArr);
	}
	
}
