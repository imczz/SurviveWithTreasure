package czz.swt;

import java.util.ArrayList;
import java.util.List;

/**
 * 商店里售卖的商品
 * @author CZZ
 * */
public class Commodity {

	private int id;
	
	private String name;
	
	private List<Pair<Integer, Integer>> resCountList;
	
	//====================methods====================
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Pair<Integer, Integer>> getResCountList() {
		return resCountList;
	}

	/**
	 * 构造方法
	 * */
	public Commodity(int id, String name, List<Pair<Integer, Integer>> resCountList) {
		this.id = id;
		this.name = name;
		this.resCountList = new ArrayList<Pair<Integer, Integer>>();
		this.resCountList.addAll(resCountList);
	}
	
}
