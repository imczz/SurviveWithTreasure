package czz.swt;

import java.util.ArrayList;
import java.util.List;

/**
 * 商店（可以用某些东西，购买某些物品）
 * @author CZZ
 * */
public class Store {

	/**
	 * 商店id号
	 * */
	private int id;
	
	/**
	 * 商店名称
	 * */
	private int name;
	
	/**
	 * 商品列表
	 * */
	private List<Res> resList;

	//====================methods====================
	
	public int getId() {
		return id;
	}

	public int getName() {
		return name;
	}

	public List<Res> getResList() {
		return resList;
	}

	public void setResList(List<Res> resList) {
		this.resList.clear();
		this.resList.addAll(resList);
	}
	
	/**
	 * 构造方法
	 * @param id 商店id
	 * */
	public Store(int id) {
		this.id = id;
		this.resList = new ArrayList<Res>();
	}
	
	/**
	 * 构造方法2
	 * @param id 商店id
	 * @param name 商店名称
	 * @param resList 出售商品列表
	 * */
	public Store(int id, int name, List<Res> resList) {
		this.id = id;
		this.name = name;
		this.resList = new ArrayList<Res>();
		this.resList.addAll(resList);
	}
}
