package czz.swt;

import java.util.List;

/**
 * 商店里售卖的商品
 * @author CZZ
 * */
public class Commodity extends Entity{

	private ResBag resBag;
	
	//====================methods====================

	/**
	 * 获取商品列表
	 * @return 获取商品列表，列表是一个哈希表，由<id, number>构成
	 * */
	public ResBag getResBag() {
		return this.resBag;
	}

	/**
	 * 构造方法
	 * @param id 商品id
	 * @param name 商品名称
	 * */
	public Commodity(int id, String name) {
		super(id, name);
		this.resBag = new ResBag();
	}
	
	/**
	 * 构造方法2
	 * @param id 商品id
	 * @param name 商品名称
	 * @param resBag 资源背包
	 * */
	public Commodity(int id, String name, ResBag resBag) {
		super(id, name);
		this.resBag = new ResBag(resBag);
	}
	
	/**
	 * 添加资源序列
	 * @param resList 资源列表
	 * @return true成功;false失败
	 * */
	public boolean addResList(List<ResPackage> resList) {
		boolean ret = false;
		if (resList != null) {
			this.resBag.batchAdd(resList);
		}
		return ret;
	}
	
}
