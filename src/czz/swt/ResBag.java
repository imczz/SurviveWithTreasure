package czz.swt;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源背包，记录资源与对应的数量
 * */
public class ResBag {

	/**
	 * 一个资源背包，key为资源id，value为资源的数量
	 * */
	protected ConcurrentHashMap<Integer, Integer> bag;
	
	/**
	 * TODO:资源增减记录
	 * */
	//protected Logger log;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	ResBag() {
		bag = new ConcurrentHashMap<Integer, Integer>();
	}
	
	/**
	 * 得到背包中资源的数量
	 * @return 背包中资源的数量
	 * */
	public int size() {
		return bag.size();
	}
	
	/**
	 * 添加资源
	 * @param id 资源的id
	 * @param value 资源增加的数量(value > 0)
	 * @return true添加成功;false添加失败
	 * */
	public boolean addRes(int id, int value) {
		boolean ret = false;
		if (value > 0) {				//增加的值一定是正值
			Integer previous = bag.get(id);
			if (previous != null) {
				value += previous;			//新的数量
			}
			bag.put(id, value);
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 减少资源
	 * @param id 资源的id
	 * @param value 资源增加的数量(value > 0)
	 * @return true减少成功;false减少失败
	 * */
	public boolean reduceRes(int id, int value) {
		boolean ret = false;
		if (value > 0) {				//增加的值一定是正值
			Integer previous = bag.get(id);
			if (previous != null && previous >= value) {
				int newValue = previous - value;			//新的数量
				if (newValue == 0) {
					bag.remove(id);					//删除数量为0的资源
				} else {
					bag.put(id, newValue);
				}
			}
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 增加或者减少资源
	 * @param id 资源的id
	 * @param value 资源增减的数量
	 * @return true增减成功（或者为0）;false增减失败
	 * */
	public boolean addReduceRes(int id, int value) {
		boolean ret = false;
		if (value == 0) ret = true;
		if (value > 0) {
			ret = this.addRes(id, value);
		} else {
			ret = this.reduceRes(id, value);
		}
		return ret;
	}
	
	/**
	 * 是否存在某个资源
	 * @param id 待查询的资源的id
	 * @return true有;false无
	 * */
	public boolean hasRes(int id) {
		return bag.containsKey(id);
	}
	
	/**
	 * 获取背包中资源的数量
	 * @param id 待查询的资源的id
	 * @return 资源的数量，没有则返回空
	 * */
	public Integer getResNumber(int id) {
		return bag.get(id);
	}
}
