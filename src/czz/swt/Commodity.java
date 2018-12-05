package czz.swt;

import java.util.HashMap;

/**
 * 商店里售卖的商品
 * @author CZZ
 * */
public class Commodity extends Entity{

	private HashMap<Integer, Integer> resCountList;
	
	//====================methods====================

	/**
	 * 获取商品列表
	 * @return 获取商品列表，列表是一个哈希表，由<id, number>构成
	 * */
	public HashMap<Integer, Integer> getResCountList() {
		return resCountList;
	}

	/**
	 * 构造方法
	 * */
	public Commodity(int id, String name, HashMap<Integer, Integer> resCountList) {
		super(id, name);
		this.resCountList = new HashMap<Integer, Integer>();
		this.resCountList.putAll(resCountList);
	}
	
}
