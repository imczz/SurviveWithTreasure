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
	 * 资源消耗
	 * */
	private Cost cost;

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

	public Cost getCost() {
		return cost;
	}
	
	/**
	 * 构造方法
	 * @param id 资源id
	 * @param cost 资源消耗值
	 * */
	public Res(int id, Cost cost) {
		this.id = id;
		this.cost = cost;
	}
	
	/**
	 * 构造方法2
	 * @param id 资源id
	 * @param name 资源名称
	 * @param cost 资源消耗值
	 * */
	public Res(int id, String name, Cost cost) {
		this.id = id;
		this.name = name;
		this.cost = cost;
	}
	
	public boolean equals(Res res) {
		return (this.id == res.id && this.cost.equals(res.cost));
	}
}
