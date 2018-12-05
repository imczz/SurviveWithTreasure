package czz.swt;

/**
 * 一对
 * @author CZZ
 * */
public class Pair<T, E> {
	
	/**
	 * 第一个字段
	 * */
	public T first;
	
	/**
	 * 第二个字段
	 * */
	public E second;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * @param 第一个字段
	 * @param 第二个字段
	 * */
	public Pair(T first, E second){
		this.first = first;
		this.second = second;
	}
	
	/**
	 * 复制（拷贝）构造方法
	 * @param 另一个对子
	 * */
	public Pair(Pair<T, E> pair) {
		if (pair != null) {				//不为空
			this.first = pair.first;
			this.second = pair.second;
		}
		else {
			this.first = null;
			this.second = null;
		}
	}
	
	/**
	 * 转换为字符串表示
	 * */
	@Override
	public String toString() {
		String first = "null";
		String second = "null";
		if (this.first != null) first = this.first.toString();
		if (this.second != null) second = this.second.toString();
		return "<" + first + ", " + second + ">";
	}
}
