package czz.swt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 商店（可以出售商品，或者收购商品）
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
	private String name;
	
	/**
	 * 商品定义列表
	 * */
	private HashMap<Integer, Commodity> commoditys;
	
	/**
	 * 正在出售的商品列表
	 * */
	private List<Integer> saleList;
	
	/**
	 * 正在出售的商品的售价
	 * */
	private HashMap<Integer, Cost> sales;

	/**
	 * 期望收购的商品列表
	 * */
	private List<Integer> acquisitionList;
	
	/**
	 * 期望收购的商品的售价
	 * */
	private HashMap<Integer, Cost> acquisitions;
	
	//====================methods====================
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * 构造方法
	 * @param id 商店id
	 * */
	public Store(int id) {
		this.id = id;
		this.commoditys = new HashMap<Integer, Commodity>();
		this.saleList = new ArrayList<Integer>();
		this.sales = new HashMap<Integer, Cost>();
		this.acquisitionList = new ArrayList<Integer>();
		this.acquisitions = new HashMap<Integer, Cost>();
	}
	
	/**
	 * 构造方法2
	 * @param id 商店id
	 * @param name 商店名称
	 * @param resList 出售商品列表
	 * */
	public Store(int id, String name, HashMap<Integer, Commodity> commoditys, HashMap<Integer, Cost> sales, HashMap<Integer, Cost> acquisitions) {
		this.id = id;
		this.name = name;
		this.id = id;
		this.commoditys = new HashMap<Integer, Commodity>();
		this.saleList = new ArrayList<Integer>();
		this.sales = new HashMap<Integer, Cost>();
		this.acquisitionList = new ArrayList<Integer>();
		this.acquisitions = new HashMap<Integer, Cost>();
		this.commoditys.putAll(commoditys);
		this.sales.putAll(sales);
		this.saleList.addAll(this.sales.keySet());
		this.acquisitions.putAll(acquisitions);
		this.acquisitionList.addAll(this.acquisitions.keySet());
	}
	
	/**
	 * 获取商店默认的名字
	 * @return 名字为空则返回id
	 * */
	public String getDefaultName() {
		String ret = this.name;
		if (ret == null || ret.equals("")) ret = "商店" + this.id;
		return ret;
	}
	
	/**
	 * 获取商店仓库中所有的商品列表和可能出售的商品列表
	 * @return 商品定义列表
	 * */
	public HashMap<Integer, Commodity> getAllCommodits() {
		return this.commoditys;
	}
	
	/**
	 * 获取正在出售的商品的id列表
	 * @return 正在出售的商品列表
	 * */
	public List<Integer> getSaleList() {
		return this.saleList;
	}
	
	/**
	 * 获取正在收购的商品的id列表
	 * @return 
	 * */
	public List<Integer> getAcquisitionList() {
		return this.acquisitionList;
	}
	
	/**
	 * 获取某个商品的售价
	 * @param commodityID 商品id
	 * @return 某个商品的售价，不出售的商品为空
	 * */
	public Cost getCommodityCost(int commodityID) {
		return sales.get(commodityID);
	}
	
	/**
	 * 获取某个商品的回收价格
	 * @param commodityID 商品id
	 * @return 某个商品的回收价格，不回收的商品为空
	 * */
	public Cost getAcquisitionCost(int commodityID) {
		return acquisitions.get(commodityID);
	}
	
	/**
	 * 仅仅添加商品定义，但是不出售或者收购
	 * @param commodity 商品定义
	 * @return true添加成功;false添加失败（重复添加等）
	 * */
	public boolean addCommodity(Commodity commodity) {
		boolean ret = false;
		int id = commodity.getId();
		if (!this.commoditys.containsKey(id)) {
			//当前商品列表不包含此商品，且消耗的源id为此商品的id
			this.commoditys.put(id, commodity);
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 添加出售商品或者收购商品
	 * @param commodity 商品定义
	 * @param sale true出售商品;false为收购商品定义
	 * @param cost 售价或者回收价格
	 * @return true添加成功;false添加失败（重复添加等）
	 * */
	public boolean addCommodity(Commodity commodity, boolean sale, Cost cost) {
		boolean ret = false;
		int id = commodity.getId();
		if (!this.commoditys.containsKey(id) /*&& cost.getID() == id*/) {
			//当前商品列表不包含此商品，且消耗的源id为此商品的id
			this.commoditys.put(id, commodity);
			if (sale) {
				//此商品为用于出售的商品
				this.saleList.add(id);
				this.sales.put(id, cost);
				ret = true;
			} else {
				//此商品为用于收购的展示
				this.acquisitionList.add(id);
				this.acquisitions.put(id, cost);
				ret = true;
			}
			
		}
		return ret;
	}
	
	/**
	 * 删除出售回收商品以及商品定义
	 * @param commodityID
	 * @return true移除成功;false失败（不存在等）
	 * */
	public boolean removeCommodity(int commodityID) {
		boolean ret = false;
		int id = commodityID;
		if (this.commoditys.containsKey(id)) {
			//当前商品列表不包含此商品，且消耗的源id为此商品的id
			this.stopSelling(id, true);			//出售下架
			this.stopSelling(id, false);		//收购下架
			this.commoditys.remove(id);		//移除商品定义
		}
		return ret;
	}
	
	/**
	 * 上架商品，sale为true则出售，反之sale为false则为收购
	 * @param commodityID 商品定义的id
	 * @param sale 出售还是收购
	 * @param cost 售价或者回收价格
	 * @return true上架成功;false失败（重复等）
	 * */
	public boolean onSale(int commodityID, boolean sale, Cost cost) {
		boolean ret = false;
		int id = commodityID;
		if (this.commoditys.containsKey(id) /*&& cost.getID() == id*/) {
			//当前商品列表包含此商品，且消耗的源id为此商品的id
			if (sale) {
				//此商品为用于出售的商品
				if (!this.sales.containsKey(id)) {
					this.saleList.add(id);
					this.sales.put(id, cost);
					ret = true;
				}
			} else {
				//此商品为用于收购的展示
				if (!this.acquisitions.containsKey(id)) {
					this.acquisitionList.add(id);
					this.acquisitions.put(id, cost);
					ret = true;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 下架商品，sale为true则出售下架，反之sale为false则为取消收购
	 * @param commodityID 商品定义的id
	 * @param sale 出售还是收购
	 * @return true下架成功;false失败（商品不存在等）
	 * */
	public boolean stopSelling(int commodityID, boolean sale) {
		boolean ret = false;
		int id = commodityID;
		if (this.commoditys.containsKey(id)) {
			//当前商品列表包含此商品
			if (sale) {
				//此商品为用于出售的商品
				if (this.sales.containsKey(id)) {
					this.saleList.remove(Integer.valueOf(id));
					this.sales.remove(id);
					ret = true;
				}
			} else {
				//此商品为用于收购的展示
				if (this.acquisitions.containsKey(id)) {
					this.acquisitionList.remove(Integer.valueOf(id));
					this.acquisitions.remove(id);
					ret = true;
				}
			}
		}
		return ret;
	}

	
}
