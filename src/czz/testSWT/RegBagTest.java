package czz.testSWT;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import czz.swt.ResBag;

class RegBagTest {
	
	@Test
	void testAdd() {
		ResBag resBag = new ResBag();
		assertTrue(resBag.addRes(1, 2));
		assertTrue(resBag.addRes(2, 3));
		assertTrue(resBag.addRes(3, 4));
		assertFalse(resBag.addRes(1, 0));
		assertFalse(resBag.addRes(4, 0));
		assertEquals(Integer.valueOf(3), resBag.getResNumber(2));
		assertEquals(null, resBag.getResNumber(4));
	}
	
	@Test
	void testReduce() {
		ResBag resBag = new ResBag();
		assertTrue(resBag.addRes(1, 2));
		assertTrue(resBag.addRes(2, 3));
		assertTrue(resBag.addRes(3, 4));
		assertFalse(resBag.reduceRes(1, 0));
		assertTrue(resBag.reduceRes(2, 2));
		assertTrue(resBag.reduceRes(3, 4));
		assertEquals(Integer.valueOf(2), resBag.getResNumber(1));
		assertEquals(Integer.valueOf(1), resBag.getResNumber(2));
		assertEquals(null, resBag.getResNumber(3));
		assertFalse(resBag.reduceRes(1, 4));
		assertEquals(Integer.valueOf(2), resBag.getResNumber(1));
	}
	
	@Test
	void testAddReduce() {
		ResBag resBag = new ResBag();
		assertTrue(resBag.addReduceRes(1, 2));
		assertTrue(resBag.addReduceRes(2, 3));
		assertTrue(resBag.addReduceRes(3, 4));
		assertTrue(resBag.addReduceRes(3, 3));
		assertFalse(resBag.addReduceRes(1, 0));
		assertTrue(resBag.addReduceRes(2, -2));
		assertEquals(Integer.valueOf(2), resBag.getResNumber(1));
		assertEquals(Integer.valueOf(1), resBag.getResNumber(2));
		assertEquals(Integer.valueOf(7), resBag.getResNumber(3));
		assertTrue(resBag.addReduceRes(2, -1));
		assertEquals(null, resBag.getResNumber(2));
		assertFalse(resBag.addReduceRes(3, -10));
		assertEquals(Integer.valueOf(7), resBag.getResNumber(3));
	}
	
	@Test
	void testClone() {
		ResBag resBag = new ResBag();
		resBag.addRes(1, 2);
		resBag.addRes(2, 3);
		resBag.addRes(3, 4);
		ResBag newBag = new ResBag(resBag);
		assertEquals(Integer.valueOf(2), newBag.getResNumber(1));
		assertEquals(Integer.valueOf(3), newBag.getResNumber(2));
		assertEquals(Integer.valueOf(4), newBag.getResNumber(3));
		assertTrue(resBag.addReduceRes(3, 4));
		assertEquals(Integer.valueOf(8), resBag.getResNumber(3));
		assertEquals(Integer.valueOf(4), newBag.getResNumber(3));
	}

	@Test
	void testBatch() {
		ResBag resBag = new ResBag();
		HashMap<Integer, Integer> oneBag = new HashMap<Integer, Integer>();
		oneBag.put(1, 3);
		oneBag.put(2, 4);
		assertTrue(resBag.batch(oneBag));
		assertEquals(Integer.valueOf(3), resBag.getResNumber(1));
		assertEquals(Integer.valueOf(4), resBag.getResNumber(2));
		oneBag.put(1, 3);
		oneBag.put(2, -2);
		assertTrue(resBag.batch(oneBag));
		assertEquals(Integer.valueOf(6), resBag.getResNumber(1));
		assertEquals(Integer.valueOf(2), resBag.getResNumber(2));
		oneBag.put(1, -6);
		oneBag.put(2, 0);
		assertTrue(resBag.batch(oneBag));
		assertEquals(null, resBag.getResNumber(1));
		oneBag.put(1, 0);
		oneBag.put(2, -6);
		assertFalse(resBag.batch(oneBag));
		assertEquals(Integer.valueOf(2), resBag.getResNumber(2));
		oneBag.put(3, -6);
		assertFalse(resBag.batch(oneBag));
	}
	
	@Test
	void testReLoad() {
		ResBag resBag = new ResBag();
		HashMap<Integer, Integer> oneBag = new HashMap<Integer, Integer>();
		oneBag.put(1, 3);
		oneBag.put(2, 4);
		assertTrue(resBag.reload(oneBag));
		oneBag.put(1, 1);
		oneBag.put(2, 2);
		oneBag.put(3, 3);
		assertTrue(resBag.reload(oneBag));
		assertEquals(Integer.valueOf(1), resBag.getResNumber(1));
		assertEquals(Integer.valueOf(3), resBag.getResNumber(3));
		oneBag.put(1, 1);
		oneBag.put(2, -2);
		oneBag.put(3, 3);
		assertFalse(resBag.reload(oneBag));
		assertEquals(Integer.valueOf(1), resBag.getResNumber(1));
		assertEquals(Integer.valueOf(2), resBag.getResNumber(2));
		assertEquals(Integer.valueOf(3), resBag.getResNumber(3));
	}
	
}
