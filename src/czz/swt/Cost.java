package czz.swt;

/**
 * 资源消耗记录
 * @author CZZ
 * */
public class Cost {

	/**
	 * 资源id
	 * */
	private int resID;
	
	/**
	 * 资源名称
	 * */
	private String resName;
	
	/**
	 * 消耗资源值
	 * */
	private Float value;

	/**
	 * 定义枚举类型，资源消耗方向，Pay:支付、消耗，Gain:获取
	 * */
	public static enum Direction {Pay, Gain}
	
	/**
	 * 资源消耗方向，Pay:支付、消耗，Gain:获取
	 * */
	private Direction direction;
	
	//====================methods====================
	
	public int getResID() {
		return resID;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public Float getValue() {
		return value;
	}
	
	public Direction getDirection() {
		return direction;
	}

	/**
	 * 构造方法
	 * @param resID 消耗资源的id
	 * @param value 消耗资源的数量
	 * @param direction 消耗资源的方向，Pay:支付、消耗，Gain:获取
	 * */
	public Cost(int resID, Float value, Direction direction) {
		this.resID = resID;
		this.value = value;
		this.direction = direction;
	}
	
	/**
	 * 构造方法2
	 * @param resID 消耗资源的id
	 * @param name 消耗资源的名称
	 * @param value 消耗资源的数量
	 * @param direction 消耗资源的方向，Pay:支付、消耗，Gain:获取
	 * */
	public Cost(int resID, String resName, Float value, Direction direction) {
		this.resID = resID;
		this.resName = resName;
		this.value = value;
		this.direction = direction;
	}
	
	/**
	 * 判断两个标价是否相同
	 * @param cost 另一个标价
	 * */
	public boolean equals(Cost cost) {
		return (this.resID == cost.resID && this.value.equals(cost.value) && this.direction == cost.direction);
	}
}
