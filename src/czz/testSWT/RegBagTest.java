package czz.testSWT;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import czz.swt.Pair;
import czz.swt.Res;
import czz.swt.ResBag;

class RegBagTest {
	
	Res[] resList = {new Res(1,"黄金","克"), new Res(2,"人民币","元"), new Res(3,"负重","千克"), new Res(4,"水","瓶"), new Res(5,"食物","份")};
	
	@Test
	void testAdd() {
		ResBag resBag = new ResBag();
		assertTrue(resBag.addRes(resList[0], 2));
		assertTrue(resBag.addRes(resList[1], 3));
		assertTrue(resBag.addRes(resList[2], 4));
		assertFalse(resBag.addRes(resList[0], 0));
		assertFalse(resBag.addRes(resList[3], -1));
		assertEquals(3, resBag.getResNumber(2));
		assertEquals(0, resBag.getResNumber(4));
	}
	
	@Test
	void testReduce() {
		ResBag resBag = new ResBag();
		assertTrue(resBag.addRes(resList[0], 2));
		assertTrue(resBag.addRes(resList[1], 3));
		assertTrue(resBag.addRes(resList[2], 4));
		assertFalse(resBag.reduceRes(1, 0));
		assertTrue(resBag.reduceRes(2, 2));
		assertTrue(resBag.reduceRes(3, 4));
		assertEquals(2, resBag.getResNumber(1));
		assertEquals(1, resBag.getResNumber(2));
		assertEquals(0, resBag.getResNumber(3));
		assertFalse(resBag.reduceRes(1, 4));
		assertEquals(2, resBag.getResNumber(1));
	}
	
	@Test
	void testAddReduce() {
		ResBag resBag = new ResBag();
		assertTrue(resBag.addReduceRes(resList[0], 2));
		assertTrue(resBag.addReduceRes(resList[1], 3));
		assertTrue(resBag.addReduceRes(resList[2], 4));
		assertTrue(resBag.addReduceRes(resList[2], 3));
		assertFalse(resBag.addReduceRes(resList[0], 0));
		assertTrue(resBag.addReduceRes(resList[1], -2));
		assertEquals(2, resBag.getResNumber(1));
		assertEquals(1, resBag.getResNumber(2));
		assertEquals(7, resBag.getResNumber(3));
		assertTrue(resBag.addReduceRes(resList[1], -1));
		assertEquals(0, resBag.getResNumber(2));
		assertFalse(resBag.addReduceRes(resList[2], -10));
		assertEquals(7, resBag.getResNumber(3));
	}
	
	@Test
	void testClone() {
		ResBag resBag = new ResBag();
		resBag.addRes(resList[0], 2);
		resBag.addRes(resList[1], 3);
		resBag.addRes(resList[2], 4);
		ResBag newBag = new ResBag(resBag);			//克隆背包中装载新的值，新的内容不会影响旧的内容
		assertEquals(2, newBag.getResNumber(1));
		assertEquals(3, newBag.getResNumber(2));
		assertEquals(4, newBag.getResNumber(3));
		assertTrue(resBag.addReduceRes(resList[2], 4));
		assertEquals(8, resBag.getResNumber(3));
		assertEquals(4, newBag.getResNumber(3));
	}

	@Test
	void testBatch() {
		ResBag resBag = new ResBag();
		HashMap<Integer, Pair<Res, Integer>> oneBag = new HashMap<Integer, Pair<Res, Integer>>();
		oneBag.put(resList[0].getId(), new Pair<Res, Integer>(resList[0], 3));
		oneBag.put(resList[1].getId(), new Pair<Res, Integer>(resList[1], 4));
		assertTrue(resBag.batch(oneBag));
		assertEquals(3, resBag.getResNumber(1));
		assertEquals(4, resBag.getResNumber(2));
		oneBag.put(resList[0].getId(), new Pair<Res, Integer>(resList[0], 3));
		oneBag.put(resList[1].getId(), new Pair<Res, Integer>(resList[1], -2));
		assertTrue(resBag.batch(oneBag));
		assertEquals(6, resBag.getResNumber(1));
		assertEquals(2, resBag.getResNumber(2));
		oneBag.put(resList[0].getId(), new Pair<Res, Integer>(resList[0], -6));
		oneBag.put(resList[1].getId(), new Pair<Res, Integer>(resList[1], 0));
		assertTrue(resBag.batch(oneBag));
		assertEquals(0, resBag.getResNumber(1));
		oneBag.put(resList[0].getId(), new Pair<Res, Integer>(resList[0], 0));
		oneBag.put(resList[1].getId(), new Pair<Res, Integer>(resList[1], -6));
		assertFalse(resBag.batch(oneBag));
		assertEquals(2, resBag.getResNumber(2));
		oneBag.put(resList[2].getId(), new Pair<Res, Integer>(resList[2], -6));
		assertFalse(resBag.batch(oneBag));
	}
	
	@Test
	void testReLoad() {
		ResBag resBag = new ResBag();
		HashMap<Integer, Pair<Res, Integer>> oneBag = new HashMap<Integer, Pair<Res, Integer>>();
		oneBag.put(resList[0].getId(), new Pair<Res, Integer>(resList[0], 3));
		oneBag.put(resList[1].getId(), new Pair<Res, Integer>(resList[1], 4));
		assertTrue(resBag.reload(oneBag));
		oneBag.put(resList[0].getId(), new Pair<Res, Integer>(resList[0], 1));
		oneBag.put(resList[1].getId(), new Pair<Res, Integer>(resList[1], 2));
		oneBag.put(resList[2].getId(), new Pair<Res, Integer>(resList[2], 3));
		assertTrue(resBag.reload(oneBag));
		assertEquals(1, resBag.getResNumber(1));
		assertEquals(3, resBag.getResNumber(3));
		oneBag.put(resList[0].getId(), new Pair<Res, Integer>(resList[0], 1));
		oneBag.put(resList[1].getId(), new Pair<Res, Integer>(resList[1], -2));
		oneBag.put(resList[2].getId(), new Pair<Res, Integer>(resList[2], 3));
		assertFalse(resBag.reload(oneBag));
		assertEquals(1, resBag.getResNumber(1));
		assertEquals(2, resBag.getResNumber(2));
		assertEquals(3, resBag.getResNumber(3));
	}
	
}
