package czz.testSWT;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import czz.swt.ResDefine;
import czz.swt.ResPackage;
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
	
	@Test
	void testReload() {
		ResBag resBag = new ResBag();
		List<ResPackage> oneBag = new ArrayList<ResPackage>();
		oneBag.add(new ResPackage(resList[0], 1));
		oneBag.add(new ResPackage(resList[0], 2));
		oneBag.add(new ResPackage(resList[0], 3));
		oneBag.add(new ResPackage(resList[1], 1));
		oneBag.add(new ResPackage(resList[1], 3));
		assertTrue(resBag.reload(oneBag));
		assertEquals(6, resBag.getResCount(resList[0]));
		assertEquals(4, resBag.getResCount(resList[1]));
		assertEquals(0, resBag.getResCount(resList[2]));
		oneBag.clear();
		oneBag.add(new ResPackage(resList[1], 1));
		oneBag.add(new ResPackage(resList[2], 1));
		oneBag.add(new ResPackage(resList[2], 2));
		oneBag.add(new ResPackage(resList[2], 4));
		assertTrue(resBag.reload(oneBag));
		assertEquals(0, resBag.getResCount(resList[0]));
		assertEquals(1, resBag.getResCount(resList[1]));
		assertEquals(7, resBag.getResCount(resList[2]));
	}
	
}
