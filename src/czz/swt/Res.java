package czz.swt;

/**
 * 物品、资源
 * @author CZZ
 * */
public class Res extends Entity{

	/**
	 * 计量资源的单位
	 * */
	private String unit;
	
	/**
	 * 是否为可以使用的物品
	 * */
	private boolean isConsumable;
	
	/**
	 * 最大使用次数
	 * */
	private int useTime;

	/**
	 * 资源提供的资源（不允许某资源占用自己，待占用计算时不算自己的Offer）
	 * */
	private Offer offer;
	
	/**
	 * 资源占用的资源（不允许某资源占用自己，待占用计算时不算自己的Offer）
	 * */
	private Demand demand;
	
	//====================methods====================

	public int getUseTime() {
		return useTime;
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
	public Res(int id, String name, String unit) {
		super(id, name);
		this.unit = unit;
		this.isConsumable =false;
		this.useTime = -1;				//负数代表无限次使用
		this.offer = null;
		this.demand = null;
	}
	
	/**
	 * 构造方法2
	 * @param id 资源id
	 * @param name 资源的名称
	 * @param unit 计量资源的单位
	 * @param isConsumable 是否为消耗品
	 * @param useTime 最大使用次数
	 * */
	public Res(int id, String name, String unit, boolean isConsumable, int useTime, Offer offer, Demand demand) {
		super(id, name);
		this.unit = unit;
		this.isConsumable = isConsumable;
		this.useTime = useTime;
		this.offer = offer;
		this.demand = demand;
	}
	
	/**
	 * 构造方法2
	 * @param res 另一个资源
	 * */
	public Res(Res res) {
		super(res.id, res.name);
		this.unit = res.unit;
		this.isConsumable = res.isConsumable;
		this.useTime = res.useTime;
		this.offer = new Offer(res.offer);
		this.demand = new Demand(res.demand);
	}
	
	/**
	 * 判断资源是否需要被删除
	 * @return 消耗品使用次数是否耗尽
	 * */
	public boolean needDelete() {
		boolean ret = false;
		if (this.isConsumable && this.useTime == 0) ret = true;
		return ret;
	}
	
	/**
	 * 判断消耗品资源的使用次数，非消耗品无法使用
	 * @return 消耗品是否可以被使用
	 * */
	public boolean canUse() {
		boolean ret = false;
		if (this.isConsumable && this.useTime != 0) ret = true;
		return ret;
	}
	
	/**
	 * 使用此资源1次
	 * @return 是否使用成功并扣除使用次数
	 * */
	public boolean useRes() {
		boolean ret = false;
		if (this.canUse()) {
			if (this.useTime > 0) this.useTime--;			//消耗品
			ret = true;
		}
		return ret;
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
	 * 判断两个相同的资源是否完全相同
	 * @param res 另一个资源
	 * */
	public boolean equals(Res res) {
		return (this.id == res.id && this.useTime == res.useTime);
	}
	
	/**
	 * 转换为文字
	 * */
	@Override
	public String toString() {
		return name;
	}
}
