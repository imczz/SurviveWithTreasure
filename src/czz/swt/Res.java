package czz.swt;

/**
 * 物品、资源
 * @author CZZ
 * */
public class Res {

	/**
	 * 资源名称
	 * */
	private int id;
	
	/**
	 * 资源名称
	 * */
	private String name;
	
	/**
	 * 计量资源的单位
	 * */
	private String unit;
	
	/**
	 * 是否为消耗品
	 * */
	private boolean isConsumable;
	
	/**
	 * 最大使用次数
	 * */
	private int useTime;

	/**
	 * 资源提供的资源
	 * */
	private Offer offer;
	
	/**
	 * 资源占用的资源
	 * */
	private Demand demand;
	
	//====================methods====================
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
		this.id = id;
		this.name = name;
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
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.isConsumable = isConsumable;
		this.useTime = useTime;
		this.offer = offer;
		this.demand = demand;
	}
	
	/**
	 * 判断两个相同的资源是否完全相同
	 * */
	public boolean equals(Res res) {
		return (this.id == res.id);
	}
	
	/**
	 * 转换为文字
	 * */
	@Override
	public String toString() {
		return name;
	}
}
