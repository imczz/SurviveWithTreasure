package czz.testSWT;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import czz.swt.Demand;
import czz.swt.Offer;
import czz.swt.ResDefine;

class OfferAndDemandTest {
	
	ResDefine[] resList = {new ResDefine(0,"空间","立方米"), new ResDefine(1,"黄金","克"), new ResDefine(2,"人民币","元"), new ResDefine(3,"负重","千克"), new ResDefine(4,"水","瓶"), new ResDefine(5,"食物","份")};
	
	@Test
	void testAddValue() {
		Demand demand = new Demand(resList[0]);
		assertFalse(demand.addValue(resList[0], 1));
		assertFalse(demand.addValue(resList[1], 0));
		assertTrue(demand.addValue(resList[2], 2));
		assertEquals(2, demand.getValue(2));
	}

	@Test
	void testReduceValue() {
		HashMap<ResDefine, Integer> values = new HashMap<ResDefine, Integer>();
		values.put(resList[1], 2);
		values.put(resList[2], 3);
		values.put(resList[3], 4);
		Demand demand = new Demand(resList[0], values);
		assertFalse(demand.reduceValue(0, 1));
		assertFalse(demand.reduceValue(1, 0));
		assertTrue(demand.reduceValue(1, 1));
		assertEquals(1, demand.getValue(1));
		assertFalse(demand.reduceValue(1, 2));
		assertEquals(1, demand.getValue(1));
		assertTrue(demand.reduceValue(2, 3));
		assertEquals(0, demand.getValue(2));
		assertEquals(4, demand.getValue(3));
	}
	
	@Test
	void testBatch() {
		HashMap<ResDefine, Integer> values = new HashMap<ResDefine, Integer>();
		values.put(resList[1], 2);
		values.put(resList[2], 3);
		values.put(resList[3], 4);
		Offer offer = new Offer(null, values);
		values.put(resList[1], 0);
		values.put(resList[2], 1);
		values.put(resList[3], -1);
		assertTrue(offer.batch(values));
		assertEquals(2, offer.getValue(1));
		assertEquals(4, offer.getValue(2));
		assertEquals(3, offer.getValue(3));
		values.put(resList[1], 0);
		values.put(resList[2], -4);
		values.put(resList[3], 2);
		assertTrue(offer.batch(values));
		assertEquals(2, offer.getValue(1));
		assertEquals(0, offer.getValue(2));
		assertEquals(5, offer.getValue(3));
		values.put(resList[1], -4);
		values.put(resList[2], 0);
		values.put(resList[3], 0);
		assertFalse(offer.batch(values));
		assertEquals(2, offer.getValue(1));
		assertEquals(0, offer.getValue(2));
		assertEquals(5, offer.getValue(3));
	}
	
	@Test
	void testBatch2() {
		HashMap<ResDefine, Integer> values = new HashMap<ResDefine, Integer>();
		values.put(resList[1], 2);
		values.put(resList[2], 3);
		values.put(resList[3], 4);
		Demand demand = new Demand(null, values);
		assertTrue(demand.batch(values, 2));
		assertEquals(6, demand.getValue(1));
		assertEquals(9, demand.getValue(2));
		assertEquals(12, demand.getValue(3));
	}
	
	@Test
	void testContains() {
		HashMap<ResDefine, Integer> values1 = new HashMap<ResDefine, Integer>();
		values1.put(resList[0], 2);
		values1.put(resList[1], 3);
		values1.put(resList[2], 4);
		Demand demand1 = new Demand(null, values1);
		HashMap<ResDefine, Integer> values2 = new HashMap<ResDefine, Integer>();
		values2.put(resList[0], 2);
		values2.put(resList[1], 3);
		values2.put(resList[2], 4);
		Demand demand2 = new Demand(null, values2);
		HashMap<ResDefine, Integer> values3 = new HashMap<ResDefine, Integer>();
		values3.put(resList[0], 2);
		values3.put(resList[1], 3);
		Demand demand3 = new Demand(null, values3);
		HashMap<ResDefine, Integer> values4 = new HashMap<ResDefine, Integer>();
		values4.put(resList[0], 2);
		values4.put(resList[1], 3);
		values4.put(resList[2], 3);
		Demand demand4 = new Demand(null, values4);
		HashMap<ResDefine, Integer> values5 = new HashMap<ResDefine, Integer>();
		values5.put(resList[0], 2);
		values5.put(resList[1], 3);
		values5.put(resList[3], 3);
		Demand demand5 = new Demand(null, values5);
		HashMap<ResDefine, Integer> values6 = new HashMap<ResDefine, Integer>();
		values6.put(resList[0], 2);
		values6.put(resList[4], 3);
		Demand demand6 = new Demand(null, values6);
		HashMap<ResDefine, Integer> values7 = new HashMap<ResDefine, Integer>();
		values7.put(resList[0], 2);
		values7.put(resList[1], 4);
		Demand demand7 = new Demand(null, values7);
		assertTrue(demand1.contains(demand2));
		assertTrue(demand2.contains(demand1));
		assertTrue(demand1.contains(demand3));
		assertTrue(demand1.contains(demand4));
		assertFalse(demand1.contains(demand5));
		assertFalse(demand1.contains(demand6));
		assertFalse(demand1.contains(demand7));
	}
	
}
