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
	 * 一个资源背包，key为资源id，value为<资源，数量>对
	 * */
	protected ConcurrentHashMap<Integer, Pair<ResDefine, Integer>> bag;
	
	/**
	 * TODO:资源增减记录
	 * */
	//protected Logger log;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public ResBag() {
		this.bag = new ConcurrentHashMap<Integer, Pair<ResDefine, Integer>>();
	}
	
	/**
	 * 复制（拷贝）构造方法
	 * */
	public ResBag(ResBag resBag) {
		this.bag = new ConcurrentHashMap<Integer, Pair<ResDefine, Integer>>();
		Iterator<Entry<Integer, Pair<ResDefine, Integer>>> iter = resBag.bag.entrySet().iterator();		//待并入数据迭代器
		Entry<Integer, Pair<ResDefine, Integer>> entry = null;
		while (iter.hasNext()) {
			entry = iter.next();
			this.bag.put(entry.getKey(), new Pair<ResDefine, Integer>(entry.getValue()));
		}
		
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
	public ConcurrentHashMap<Integer, Pair<ResDefine, Integer>> getBag() {
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
	public boolean batch(HashMap<Integer, Pair<ResDefine, Integer>> oneBag) {
		boolean ret = true;
		if (oneBag != null && oneBag.size() != 0) {
			ConcurrentHashMap<Integer, Pair<ResDefine, Integer>> testBag = 
					new ConcurrentHashMap<Integer, Pair<ResDefine, Integer>>(this.bag);	//复制一份旧背包，得到一份待处理背包
			Iterator<Entry<Integer, Pair<ResDefine, Integer>>> iter = oneBag.entrySet().iterator();		//待并入数据迭代器
			Entry<Integer, Pair<ResDefine, Integer>> entry = null;
			Pair<ResDefine, Integer> newPair = null;
			Pair<ResDefine, Integer> oldResPair = null;
			int id = 0;
			while (iter.hasNext()) {
				entry = iter.next();
				newPair = entry.getValue();
				id = entry.getKey();
				if (newPair.second == 0) continue;
				oldResPair = testBag.get(id);		//原背包中含有entry.getKey()
				if (oldResPair != null) {			//需要合并
					if (oldResPair.second + newPair.second < 0) {					//合并后的值小于0
						ret = false;									//合并失败
						break;
					} else if (oldResPair.second == 0) {			//合并之后的值等于0
						testBag.remove(entry.getKey());					//移除资源
					}
					oldResPair.second += newPair.second;			//计算合并后的值
				} else {							//需要新增
					testBag.put(id, new Pair<ResDefine, Integer>(newPair));	//将新值装入
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
	 * @param oneBag <id, number>构成的序列，number>0，为0则不插入
	 * @return true成功;false失败（比如oneBag的value含有负值）
	 * */
	public boolean reload(HashMap<Integer, Pair<ResDefine, Integer>> oneBag) {
		boolean ret = true;
		if (oneBag != null && oneBag.size() > 0) {
			ConcurrentHashMap<Integer, Pair<ResDefine, Integer>> testBag = new ConcurrentHashMap<Integer, Pair<ResDefine, Integer>>();
			Iterator<Entry<Integer, Pair<ResDefine, Integer>>> iter = oneBag.entrySet().iterator();
			Entry<Integer, Pair<ResDefine, Integer>> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				Pair<ResDefine, Integer> resPair = entry.getValue();
				if (resPair.second > 0) {
					testBag.put(entry.getKey(), resPair);
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
	 * @param ResDefine 资源
	 * @param value 资源增加的数量(value > 0)
	 * @return 数量是否发生变化。true添加成功;false添加失败
	 * */
	public boolean addRes(ResDefine res, int value) {
		boolean ret = false;
		if (res != null && value > 0) {				//增加的值>0
			int id = res.getId();
			Pair<ResDefine, Integer> resPair = bag.get(id);
			if (resPair != null) {				//存在某个资源（查询到资源数量）
				resPair.second += value;			//更新资源数量
			} else {							//新增资源
				bag.put(id, new Pair<ResDefine, Integer>(res, value));
			}
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 减少资源
	 * @param resId 资源的id
	 * @param value 资源增加的数量(value > 0)
	 * @return 数量是否发生变化。true减少成功;false减少失败
	 * */
	public boolean reduceRes(int resId, int value) {
		boolean ret = false;
		if (value > 0) {				//减少的值>0
			Pair<ResDefine, Integer> resPair = bag.get(resId);
			if (resPair != null && resPair.second >= value) {
				resPair.second -= value;			//更新资源数量
				if (resPair.second == 0) {
					bag.remove(resId);					//删除数量为0的资源
				}
				ret = true;
			}
		}
		return ret;
	}
	
	/**
	 * 增加或者减少资源
	 * @param ResDefine 资源
	 * @param value 资源增减的数量（value<>0）
	 * @return 数量是否发生变化。true增减成功;false增减失败
	 * */
	public boolean addReduceRes(ResDefine res, int value) {
		boolean ret = false;
		if (value > 0) {
			ret = this.addRes(res, value);
		} else if (value < 0) {
			ret = this.reduceRes(res.getId(), -value);
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
	public int getResNumber(int resId) {
		int ret = 0;
		Pair<ResDefine, Integer> pair = bag.get(resId);
		if (pair != null) ret = pair.second;
		return ret;
	}
	
	/**
	 * 转换为资源与对应数量的列表（HashMap）
	 * @return 资源与对应数量的HashMap
	 * */
	public HashMap<Integer, Integer> toResValue(){
		HashMap<Integer, Integer> ret = null;
		if (this.bag.size() > 0) {
			ret = new HashMap<Integer, Integer>();;
			Iterator<Entry<Integer, Pair<ResDefine, Integer>>> iter = this.bag.entrySet().iterator();
			Entry<Integer, Pair<ResDefine, Integer>> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				ret.put(entry.getKey(), entry.getValue().second);
			}
		}
		return ret;
	}
	
}
