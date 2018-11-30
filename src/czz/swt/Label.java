package czz.swt;

/**
 * 标签，标签是某种范围的事物具有的标志
 * @author CZZ
 * */
public class Label {

	/**
	 * 标签的id
	 * */
	private int id;
	
	/**
	 * 标签的名称
	 * */
	private String name;
	
	/**
	 * 标签的数值
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
	 * @param id 标签的id号
	 * */
	public Label(int id) {
		this.id = id;
		this.value = 0f;
	}
	
	/**
	 * 构造方法2
	 * @param id 标签的id号
	 * @param name 标签的名称
	 * */
	public Label(int id, String name, Float value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	/**
	 * 获取状态的（提供给用户看的）名字
	 * */
	public String toString() {
		String ret = name;
		if (ret != null && !ret.isEmpty()) ret = "标签" + this.id;
		return ret;
	}
}
