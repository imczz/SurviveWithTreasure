package czz.swt;

/**
 * 标签，标签是某种范围的事物具有的标志
 * @author CZZ
 * */
public class Label extends Entity{
	
	/**
	 * 数值
	 * */
	protected Float value;

	//====================methods====================
	
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
		super(id);
		this.value = 0f;
	}
	
	/**
	 * 构造方法2
	 * @param id 标签的id号
	 * @param name 标签的名称
	 * */
	public Label(int id, String name, Float value) {
		super(id, name, null);
		this.value = value;
	}
	
	/**
	 * 获取类型的名字
	 * */
	@Override
	protected String getTypeName() {
		return "标签";
	}
	
}
