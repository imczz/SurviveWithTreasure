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
	 * */
	Pair(T first, E second){
		this.first = first;
		this.second = second;
	}
	
}
