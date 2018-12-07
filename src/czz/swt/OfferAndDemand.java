package czz.swt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import czz.util.MyCompare;

/**
 * 购买商品消耗资源，某资源带来资源（例如骆驼带来负重），某资源占用资源（食物占用负重）
 * */
public abstract class OfferAndDemand{

	/**
	 * 资源的id
	 * */
	protected int id;
	
	/**
	 * 相对应的资源id列表
	 * */
	protected List<Integer> resList;
	
	/**
	 * 资源对应的数量
	 * */
	protected HashMap<Integer, Integer> resValues;
	
	//====================methods====================
	
	public int getID() {
		return id;
	}

	/**
	 * 获取消耗的资源列表
	 * @return 消耗的资源列表
	 * */
	public List<Integer> getResList() {
		return this.resList;
	}
	
	/**
	 * 获取消耗资源与对应数量的对应表
	 * @return 消耗资源与对应数量的对应表
	 * */
	public HashMap<Integer, Integer> getResValues() {
		return this.resValues;
	}
	
	/**
	 * 构造方法
	 * @param resID 消耗资源的id
	 * */
	public OfferAndDemand(int resID) {
		this.id = resID;
		this.resList = new ArrayList<Integer>();
		this.resValues = new HashMap<Integer, Integer>();
	}
	
	/**
	 * 构造方法2
	 * @param resID 消耗资源的id
	 * @param costResValue 被消耗的资源与对应的值
	 * */
	public OfferAndDemand(int resID, HashMap<Integer, Integer> resValues) {
		this.id = resID;
		this.resList = new ArrayList<Integer>();
		this.resValues = new HashMap<Integer, Integer>();
		if (resValues != null) {
			this.resList.addAll(resValues.keySet());
			this.resValues.putAll(resValues);
		}
	}
	
	/**
	 * 复制（拷贝）构造方法
	 * @param 
	 * */
	protected OfferAndDemand(OfferAndDemand offerAndDemand) {
		this.id = offerAndDemand.id;
		this.resList = new ArrayList<Integer>(offerAndDemand.resList);
		this.resValues = new HashMap<Integer, Integer>(offerAndDemand.resValues);
	}
	
	/**
	 * 功能的描述
	 * */
	protected abstract String functionalDescription();
	
	/**
	 * 查找某个资源
	 * @return true存在;false 不存在
	 * */
	public boolean hasRes(int resId) {
		boolean ret = false;
		if (resValues.get(resId) != null) ret = true;
		return ret;
	}
	
	/**
	 * 获取某个资源对应的值
	 * @return 某个资源对应的值，0为不存在
	 * */
	public int getValueById(int resId) {
		Integer ret = resValues.get(resId);
		return (ret != null ? ret.intValue() : 0);
	}
	
	/**
	 * 增加资源对应的数量
	 * @param resId 资源的id(id不为自己的id)
	 * @param value 资源增加的值(value > 0)
	 * @return true数值增加;false数值不变（出错、value<=0等）
	 * */
	public boolean addValue(int resId, int value) {
		boolean ret = false;
		if (resId != this.id && value > 0) {
			Integer previousValue = this.resValues.get(resId);
			if (previousValue != null) {
				previousValue += value;
			} else {
				previousValue = value;
			}
			this.resValues.put(resId, previousValue);
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 减少资源对应的数量
	 * @param resId 资源的id
	 * @param value 资源减少的值(value > 0)
	 * @return true数值减少;false数值不变（资源不足、出错、value<=0等）
	 * */
	public boolean reduceValue(int resId, int value) {
		boolean ret = false;
		if (value > 0) {
			Integer previousValue = this.resValues.get(resId);
			if (previousValue != null && previousValue >= value) {
				previousValue -= value;
				if (previousValue == 0) {
					this.resList.remove(Integer.valueOf(resId));
					this.resValues.remove(resId);
				} else {
					this.resValues.put(resId, previousValue);
				}
				ret = true;
			}
		}
		return ret;
	}
	
	/**
	 * 批量改变数量
	 * @param batchValue 资源名与对应的数量，如果
	 * @return true批量增减正常执行;false出错
	 * */
	public boolean batch(HashMap<Integer, Integer> batchValue) {
		return batch(batchValue, 1);
	}
	
	/**
	 * 批量改变数量
	 * @param batchValue 资源名与对应的数量
	 * @return true批量增减正常执行;false出错
	 * */
	public boolean batch(HashMap<Integer, Integer> batchValue, int times) {
		boolean ret = true;
		if (times != 0) {
			HashMap<Integer, Integer> testValues = new HashMap<Integer, Integer>(this.resValues);
			Iterator<Entry<Integer, Integer>> iter = batchValue.entrySet().iterator();
			Entry<Integer, Integer> entry = null;
			Integer value = null;
			int id;
			Integer previousValue = null;
			while (iter.hasNext()) {
				entry = iter.next();
				id = entry.getKey();
				value = entry.getValue() * times;
				if (value == 0) {	//没有增减直接跳过
					continue;
				} else { 			//有增减
					previousValue = testValues.get(id);
					if (previousValue != null) {
						previousValue += value;
					} else {
						previousValue = value;
					}
					if (previousValue > 0) {
						testValues.put(id, previousValue);
					} else if (previousValue == 0) {
						testValues.remove(id);
						//this.resList.remove(Integer.valueOf(id));
					} else {
						ret = false;
						break;
					}
				}
			}
			if (ret == true) {
				this.resValues.clear();
				this.resValues = testValues;
				this.resList.clear();
				this.resList.addAll(this.resValues.keySet());
			}
		}
		return ret;
	}
	
	/**
	 * 是否是另一个集合的子集
	 * @param offerAndDemand 另一个子集
	 * @return true是;false无法比较或者否
	 * */
	public boolean contains(OfferAndDemand offerAndDemand) {
		boolean ret = false;
		if (MyCompare.compairHashMap(this.resValues, offerAndDemand.resValues) >= 0) ret = true;
		return ret;
	}
	
	/**
	 * 转换为字符串
	 * @return 描述性字符串
	 * */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("资源");
		buffer.append(this.id);
		if (resList != null && resList.size() > 0) {
			String functionalDescription = functionalDescription();
			if (functionalDescription == null || functionalDescription.equals("")) functionalDescription = "需求";
			buffer.append(functionalDescription);
			buffer.append(":[");
			for (int i = 0; i < resList.size(); i++) {
				
			}
			buffer.append("]");
		} else {
			buffer.append("无对应");
		}
		return buffer.toString();
	}
}
