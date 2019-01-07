package czz.swt;

/**
 * 状态
 * @author CZZ
 * */
public class State extends Label{

	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public State(int id) {
		super(id);
	}

	/**
	 * 构造方法2
	 * */
	public State(int id, String name, Float value) {
		super(id, name, value);
	}
	
	/**
	 * 获取类型的名字
	 * */
	@Override
	protected String getTypeName() {
		return "状态";
	}
	
}
