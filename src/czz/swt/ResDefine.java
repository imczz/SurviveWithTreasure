package czz.swt;

/**
 * 物品、资源的定义
 * @author CZZ
 * */
public class ResDefine extends Entity{

	/**
	 * 计量资源的单位
	 * */
	protected String unit;
	
	/**
	 * 是否为可以使用的物品
	 * */
	protected boolean isConsumable;
	
	/**
	 * 最大使用次数
	 * */
	protected int maxUseTime;

	/**
	 * 资源提供的资源（不允许某资源占用自己，待占用计算时不算自己的Offer）
	 * */
	protected Offer offer;
	
	/**
	 * 资源占用的资源（不允许某资源占用自己，待占用计算时不算自己的Offer）
	 * */
	protected Demand demand;
	
	/**
	 * 如果此资源是一个容器，那么此资源就会含有一个存储空间
	 * */
	protected Storage storage;
	
	//====================methods====================

	public int getMaxUseTime() {
		return maxUseTime;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean isConsumable() {
		return isConsumable;
	}

	public Demand getDemand() {
		return demand;
	}

	public Offer getOffer() {
		return offer;
	}

	/**
	 * 构造方法
	 * @param id 资源id
	 * @param name 资源的名称
	 * @param unit 计量资源的单位
	 * */
	public ResDefine(int id, String name, String unit) {
		super(id, name);
		this.unit = unit;
		this.isConsumable =false;
		this.maxUseTime = -1;				//负数代表无限次使用
		this.offer = null;
		this.demand = null;
		this.storage = null;
	}
	
	/**
	 * 构造方法2
	 * @param id 资源id
	 * @param name 资源的名称
	 * @param unit 计量资源的单位
	 * @param isConsumable 是否为消耗品
	 * @param maxUseTime 最大使用次数
	 * @param offer 此资源可以提供的资源
	 * @param demand 此资源需要占用的资源
	 * @param storage 此资源包含存储空间
	 * */
	public ResDefine(int id, String name, String unit, boolean isConsumable, int maxUseTime, 
			Offer offer, Demand demand, Storage storage) {
		super(id, name);
		this.unit = unit;
		this.isConsumable = isConsumable;
		this.maxUseTime = maxUseTime;
		this.offer = new Offer(offer);
		this.demand = new Demand(demand);
		this.storage = new Storage(storage);
	}
	
	/**
	 * 检查有无自身依赖或者提供
	 * @return true经过检验没有自身依赖和提供现象;false存在自身依赖或者提供现象
	 * */
	public boolean validate() {
		boolean ret = true;
		if (this.offer.hasRes(this.id) || this.demand.hasRes(this.id)) ret = false;
		return ret;
	}
	
	/**
	 * 增加提供物
	 * @param resId 此资源提供的资源（不为自身id）
	 * @param value 提供的资源的数量
	 * @return true增加成功;false增加失败
	 * */
	protected boolean addOffer(int resId, int value) {
		boolean ret = false;
		if (resId != this.id) {
			ret = this.offer.addValue(resId, value);
		}
		return ret;
	}
	
	/**
	 * 增加占用
	 * @param resId 此资源占用的资源（不为自身id）
	 * @param value 占用的资源的数量
	 * @return true增加成功;false增加失败
	 * */
	protected boolean addDemand(int resId, int value) {
		boolean ret = false;
		if (resId != this.id) {
			ret = this.demand.addValue(resId, value);
		}
		return ret;
	}
	
	/**
	 * 减少提供物
	 * @param resId 此资源提供的资源（不为自身id）
	 * @param value 提供的资源的数量
	 * @return true减少成功;false减少失败
	 * */
	protected boolean reduceOffer(int resId, int value) {
		boolean ret = false;
		if (resId != this.id) {
			ret = this.offer.reduceValue(resId, value);
		}
		return ret;
	}
	
	/**
	 * 减少占用
	 * @param resId 此资源占用的资源（不为自身id）
	 * @param value 占用的资源的数量
	 * @return true减少成功;false减少失败
	 * */
	protected boolean reduceDemand(int resId, int value) {
		boolean ret = false;
		if (resId != this.id) {
			ret = this.demand.reduceValue(resId, value);
		}
		return ret;
	}
	
	/**
	 * 判断两个相同的资源是否完全相同
	 * @param res 另一个资源
	 * */
	public boolean equals(ResDefine res) {
		boolean ret = false;
		if (res != null) {
			ret = (this.id == res.id);
		}
		return ret;
	}
	
	/**
	 * 返回哈希值
	 * */
	@Override
	public int hashCode() {
		return this.id;
	}
	
	/**
	 * 转换为文字
	 * */
	@Override
	public String toString() {
		return name;
	}
}
