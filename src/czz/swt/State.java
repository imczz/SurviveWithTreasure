package czz.swt;

/**
 * 状态
 * @author CZZ
 * */
public class State {

	/**
	 * 状态的id
	 * */
	private int id;
	
	/**
	 * 状态名称
	 * */
	private String name;

	/**
	 * 状态的数值
	 * */
	private Float value;
	
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
	
	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	/**
	 * 构造方法
	 * @param id 状态的id号
	 * */
	public State(int id) {
		this.id = id;
		this.setValue(0f);
	}
	
	/**
	 * 构造方法2
	 * @param id 状态的id号
	 * @param name 状态的名称
	 * */
	public State(int id, String name, Float value) {
		this.id = id;
		this.name = name;
		this.setValue(value);
	}
	
	/**
	 * 获取状态的（提供给用户看的）名字
	 * */
	public String toString() {
		String ret = name;
		if (ret != null && !ret.isEmpty()) ret = "未命名状态" + this.id;
		return ret;
	}
}
