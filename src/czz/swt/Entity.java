package czz.swt;

/**
 * 抽象类实体
 * @author CZZ
 * */
public abstract class Entity{

	/**
	 * id号
	 * */
	protected int id;

	/**
	 * 节点名称
	 * */
	protected String name;
	
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
	
	/**
	 * 构造方法
	 * */
	public Entity(int id) {
		this.id = id;
	}
	
	/**
	 * 构造方法2
	 * */
	public Entity(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * 转换为字符串
	 * */
	@Override
	public String toString() {
		String ret = name;
		if (ret == null || ret.equals("")) {
			ret = "实体" + id;
		}
		return ret;
	}
}
