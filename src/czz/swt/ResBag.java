package czz.swt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
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
	public ResBag() {
		bag = new ConcurrentHashMap<Integer, Integer>();
	}
	
	/**
	 * 复制（拷贝）构造方法
	 * */
	public ResBag(ResBag resBag) {
		bag = new ConcurrentHashMap<Integer, Integer>(resBag.bag);
	}
	
	/**
	 * 得到背包中资源的数量
	 * @return 背包中资源的数量
	 * */
	public int size() {
		return bag.size();
	}
	
	/**
	 * 返回内部的背包
	 * @return 返回用于内部存储的背包
	 * */
	public ConcurrentHashMap<Integer, Integer> getBag() {
		return this.bag;
	}
	
	/**
	 * 清空这个背包
	 * */
	public void clear() {
		this.bag.clear();
	}
	
	/**
	 * 批量修改背包内容，不是覆盖，而是合并，要求合并之后数量value>=0
	 * @param oneBag <id, number>构成的序列，number可以为负，但是合并之后非负
	 * @return true合并成功;false合并失败，无更改
	 * */
	public boolean batch(HashMap<Integer, Integer> oneBag) {
		boolean ret = true;
		if (oneBag != null && oneBag.size() != 0) {
			ConcurrentHashMap<Integer, Integer> testBag = new ConcurrentHashMap<Integer, Integer>(this.bag);
			Iterator<Entry<Integer, Integer>> iter = oneBag.entrySet().iterator();
			Entry<Integer, Integer> entry = null;
			Integer value = 0;
			while (iter.hasNext()) {
				entry = iter.next();
				if (entry.getValue() == 0) continue;
				value = testBag.get(entry.getKey());	//原背包中含有entry.getKey()
				if (value != null && value != 0) {			//需要合并
					value += entry.getValue();				//计算合并后的值
				} else {
					value = entry.getValue();
				}
				if (value < 0) {					//合并后的值小于0
					ret = false;
					break;
				} else if (value == 0) {
					testBag.remove(entry.getKey());
				} else {
					testBag.put(entry.getKey(), value);
				}
			}
			if (ret) {
				this.bag.clear();
				this.bag = testBag;				//用新的背包替换旧的背包
			}
		}
		return ret;
	}
	
	/**
	 * oneBag不为空且oneBag的value全为正值，则背包替换为oneBag，
	 * @param oneBag <id, number>构成的序列，number>0，为空则无操作
	 * @return true成功;false失败（比如oneBag的value含有负值）
	 * */
	public boolean reload(HashMap<Integer, Integer> oneBag) {
		boolean ret = true;
		if (oneBag != null && oneBag.size() > 0) {
			ConcurrentHashMap<Integer, Integer> testBag = new ConcurrentHashMap<Integer, Integer>();
			Iterator<Entry<Integer, Integer>> iter = oneBag.entrySet().iterator();
			Entry<Integer, Integer> entry = null;
			Integer value = 0;
			while (iter.hasNext()) {
				entry = iter.next();
				value = entry.getValue();
				if (value > 0) {
					testBag.put(entry.getKey(), value);
				} else {
					ret = false;
					break;
				}
			}
			if (ret) {
				this.bag.clear();
				this.bag = testBag;
			}
		}
		return ret;
	}
	
	/**
	 * 添加资源
	 * @param id 资源的id
	 * @param value 资源增加的数量(value > 0)
	 * @return 数量是否发生变化。true添加成功;false添加失败
	 * */
	public boolean addRes(int id, int value) {
		boolean ret = false;
		if (value > 0) {				//增加的值>0
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
	 * @return 数量是否发生变化。true减少成功;false减少失败
	 * */
	public boolean reduceRes(int id, int value) {
		boolean ret = false;
		if (value > 0) {				//减少的值>0
			Integer previous = bag.get(id);
			if (previous != null && previous >= value) {
				int newValue = previous - value;			//新的数量
				if (newValue == 0) {
					bag.remove(id);					//删除数量为0的资源
					ret = true;
				} else if (newValue > 0){			
					bag.put(id, newValue);			//旧的数量应该大于减去的数量，否则失败
					ret = true;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 增加或者减少资源
	 * @param id 资源的id
	 * @param value 资源增减的数量（value<>0）
	 * @return 数量是否发生变化。true增减成功;false增减失败
	 * */
	public boolean addReduceRes(int id, int value) {
		boolean ret = false;
		if (value > 0) {
			ret = this.addRes(id, value);
		} else if (value < 0) {
			ret = this.reduceRes(id, -value);
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
	 * @return 资源的数量，没有则返回0
	 * */
	public Integer getResNumber(int id) {
		Integer ret = bag.get(id);
		if (ret == null) ret = 0; 
		return ret;
	}
}
