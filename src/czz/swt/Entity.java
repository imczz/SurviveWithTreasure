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
	
	/**
	 * 实体的注释文本
	 * */
	protected String note;
	
	//====================methods====================
	
	public int getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * 构造方法
	 * */
	public Entity(int id) {
		this.id = id;
		this.name = null;
		this.note = null;
	}
	
	/**
	 * 构造方法2
	 * */
	public Entity(int id, String name, String note) {
		this.id = id;
		this.name = name;
		this.note = note;
	}
	
	/**
	 * 获取类型的名字
	 * */
	protected String getTypeName() {
		return "实体";
	}
	
	/**
	 * 转换为字符串
	 * */
	@Override
	public String toString() {
		String ret = name;
		if (ret == null || ret.equals("")) {
			ret = getTypeName() + id;
		}
		return ret;
	}
}
