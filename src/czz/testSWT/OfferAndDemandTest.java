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
	
	@Test
	void testBatch() {
		HashMap<Integer, Integer> values = new HashMap<Integer, Integer>();
		values.put(1, 2);
		values.put(2, 3);
		values.put(3, 4);
		Demand demand = new Demand(0, values);
		values.put(1, 0);
		values.put(2, 1);
		values.put(3, -1);
		assertTrue(demand.batch(values));
		assertEquals(2, demand.getValueById(1));
		assertEquals(4, demand.getValueById(2));
		assertEquals(3, demand.getValueById(3));
		values.put(1, 0);
		values.put(2, -4);
		values.put(3, 2);
		assertTrue(demand.batch(values));
		assertEquals(2, demand.getValueById(1));
		assertEquals(0, demand.getValueById(2));
		assertEquals(5, demand.getValueById(3));
		values.put(1, -4);
		values.put(2, 0);
		values.put(3, 0);
		assertFalse(demand.batch(values));
		assertEquals(2, demand.getValueById(1));
		assertEquals(0, demand.getValueById(2));
		assertEquals(5, demand.getValueById(3));
	}
	
	@Test
	void testContains() {
		HashMap<Integer, Integer> values1 = new HashMap<Integer, Integer>();
		values1.put(1, 2);
		values1.put(2, 3);
		values1.put(3, 4);
		Demand demand1 = new Demand(0, values1);
		HashMap<Integer, Integer> values2 = new HashMap<Integer, Integer>();
		values2.put(1, 2);
		values2.put(2, 3);
		values2.put(3, 4);
		Demand demand2 = new Demand(1, values2);
		HashMap<Integer, Integer> values3 = new HashMap<Integer, Integer>();
		values3.put(1, 2);
		values3.put(2, 3);
		Demand demand3 = new Demand(0, values3);
		HashMap<Integer, Integer> values4 = new HashMap<Integer, Integer>();
		values4.put(1, 2);
		values4.put(2, 3);
		values4.put(3, 3);
		Demand demand4 = new Demand(0, values4);
		HashMap<Integer, Integer> values5 = new HashMap<Integer, Integer>();
		values5.put(1, 2);
		values5.put(2, 3);
		values5.put(4, 3);
		Demand demand5 = new Demand(0, values5);
		HashMap<Integer, Integer> values6 = new HashMap<Integer, Integer>();
		values6.put(1, 2);
		values6.put(5, 3);
		Demand demand6 = new Demand(0, values6);
		HashMap<Integer, Integer> values7 = new HashMap<Integer, Integer>();
		values7.put(1, 2);
		values7.put(2, 4);
		Demand demand7 = new Demand(0, values7);
		assertTrue(demand1.contains(demand2));
		assertTrue(demand2.contains(demand1));
		assertTrue(demand1.contains(demand3));
		assertTrue(demand1.contains(demand4));
		assertFalse(demand1.contains(demand5));
		assertFalse(demand1.contains(demand6));
		assertFalse(demand1.contains(demand7));
	}
	
}
