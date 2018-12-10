package czz.testSWT;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import czz.swt.ResDefine;
import czz.swt.ResBag;

class RegBagTest {
	
	ResDefine[] resList = {new ResDefine(1,"黄金","克"), new ResDefine(2,"人民币","元"), new ResDefine(3,"负重","千克"), new ResDefine(4,"水","瓶"), new ResDefine(5,"食物","份")};
	
	@Test
	void testAdd() {
		ResBag resBag = new ResBag();
		assertTrue(resBag.addRes(resList[0], 2));
		assertTrue(resBag.addRes(resList[1], 3));
		assertTrue(resBag.addRes(resList[2], 4));
		assertFalse(resBag.addRes(resList[0], 0));
		assertFalse(resBag.addRes(resList[3], -1));
		assertEquals(3, resBag.getResCount(2));
		assertEquals(0, resBag.getResCount(4));
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
		assertEquals(2, resBag.getResCount(1));
		assertEquals(1, resBag.getResCount(2));
		assertEquals(0, resBag.getResCount(3));
		assertFalse(resBag.reduceRes(1, 4));
		assertEquals(2, resBag.getResCount(1));
	}
	
	/*
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
	*/
	
	@Test
	void testClone() {
		ResBag resBag = new ResBag();
		resBag.addRes(resList[0], 2);
		resBag.addRes(resList[1], 3);
		resBag.addRes(resList[2], 4);
		ResBag newBag = new ResBag(resBag);			//克隆背包中装载新的值，新的内容不会影响旧的内容
		assertEquals(2, newBag.getResCount(1));
		assertEquals(3, newBag.getResCount(2));
		assertEquals(4, newBag.getResCount(3));
		assertTrue(resBag.addRes(resList[2], 4));
		assertEquals(8, resBag.getResCount(3));
		assertEquals(4, newBag.getResCount(3));
	}
	
}
