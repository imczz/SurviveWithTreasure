package czz.swt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import czz.util.MyCompare;

/**
 * 购买商品消耗资源，某资源带来资源（例如骆驼带来负重），某资源占用资源（食物占用负重）
 * */
public abstract class OfferAndDemand{

	/**
	 * 资源的id
	 * */
	protected ResDefine res;
	
	/**
	 * 相对应的资源id列表
	 * */
	protected Set<ResDefine> resList;
	
	/**
	 * 资源对应的数量
	 * */
	protected HashMap<ResDefine, Integer> resValues;
	
	/**
	 * 通过资源定义的id查找资源定义
	 * */
	protected HashMap<Integer, ResDefine> idDefine;
	
	//====================methods====================
	
	public ResDefine getDefine() {
		return this.res;
	}

	/**
	 * 获取消耗的资源列表
	 * @return 消耗的资源列表
	 * */
	public Set<ResDefine> getResList() {
		return this.resList;
	}
	
	/**
	 * 获取消耗资源与对应数量的对应表
	 * @return 消耗资源与对应数量的对应表
	 * */
	public HashMap<ResDefine, Integer> getResValues() {
		return this.resValues;
	}
	
	/**
	 * 构造方法
	 * */
	public OfferAndDemand() {
		this.res = null;
		this.resList = new HashSet<ResDefine>();
		this.resValues = new HashMap<ResDefine, Integer>();
		this.idDefine = new HashMap<Integer, ResDefine>();
	}
	
	/**
	 * 构造方法2
	 * @param resID 消耗资源的id
	 * */
	public OfferAndDemand(ResDefine res) {
		this.res = res;
		this.resList = new HashSet<ResDefine>();
		this.resValues = new HashMap<ResDefine, Integer>();
		this.idDefine = new HashMap<Integer, ResDefine>();
	}
	
	/**
	 * 构造方法3
	 * @param resID 消耗资源的id
	 * @param costResValue 被消耗的资源与对应的值
	 * */
	public OfferAndDemand(ResDefine res, HashMap<ResDefine, Integer> resValues) {
		this.res = res;
		this.resList = new HashSet<ResDefine>();
		this.resValues = new HashMap<ResDefine, Integer>();
		this.idDefine = new HashMap<Integer, ResDefine>();
		if (resValues != null) {
			this.resList.addAll(resValues.keySet());
			this.resValues.putAll(resValues);
			Iterator<ResDefine> iter = this.resList.iterator();
			ResDefine resDefine = null;
			while (iter.hasNext()) {
				resDefine = iter.next();
				this.idDefine.put(resDefine.id, resDefine);
			}
		}
	}
	
	/**
	 * 复制（拷贝）构造方法
	 * @param 
	 * */
	protected OfferAndDemand(OfferAndDemand offerAndDemand) {
		this.res = offerAndDemand.res;
		this.resList = new HashSet<ResDefine>(offerAndDemand.resList);
		this.resValues = new HashMap<ResDefine, Integer>(offerAndDemand.resValues);
		this.idDefine = new HashMap<Integer, ResDefine>(offerAndDemand.idDefine);
	}
	
	public void clear() {
		this.resValues.clear();
		this.idDefine.clear();
		this.resList.clear();
	}
	
	/**
	 * 功能的描述
	 * */
	protected abstract String functionalDescription();
	
	/**
	 * 查找某个资源是否存在
	 * @param resDefine 资源定义
	 * @return true存在;false不存在
	 * */
	public boolean hasRes(ResDefine resDefine) {
		boolean ret = false;
		if (resDefine != null) {
			ret = resList.contains(resDefine);
		}
		return ret;
	}
	
	/**
	 * 查找某个资源是否存在
	 * @param resID 资源id
	 * @return true存在;false不存在
	 * */
	public boolean hasRes(int resID) {
		boolean ret = false;
		ResDefine resDefine = this.idDefine.get(resID);
		if (resDefine != null) {
			ret = this.hasRes(resDefine);
		}
		return ret;
	}
	
	/**
	 * 获取某个资源对应的值
	 * @param resDefine 资源定义
	 * @return 某个资源对应的值，0为不存在
	 * */
	public int getValue(ResDefine resDefine) {
		Integer ret = resValues.get(resDefine);
		return (ret != null ? ret.intValue() : 0);
	}
	
	/**
	 * 获取某个资源对应的值
	 * @param resID
	 * @return 某个资源对应的值，0为不存在
	 * */
	public int getValue(int resID) {
		int ret = 0;
		ResDefine resDefine = this.idDefine.get(resID);
		if (resDefine != null) {
			ret = this.getValue(resDefine);
		}
		return ret;
	}
	
	/**
	 * 增加资源对应的数量
	 * @param resDefine 资源定义
	 * @param value 资源增加的值(value > 0)
	 * @return true数值增加;false数值不变（出错、value<=0等）
	 * */
	public boolean addValue(ResDefine resDefine, int value) {
		boolean ret = false;
		if (resDefine != null && !resDefine.equals(this.res) && value > 0) {
			Integer previousValue = this.resValues.get(resDefine);
			if (previousValue != null) {
				previousValue += value;
			} else {
				previousValue = value;
				this.idDefine.put(resDefine.id, resDefine);
			}
			this.resValues.put(resDefine, previousValue);
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 增加资源对应的数量
	 * @param resID 已经存在资源定义的id
	 * @param value 资源增加的值(value > 0)
	 * @return true数值增加;false数值不变（出错、value<=0等）
	 * */
	public boolean addValue(int resID, int value) {
		boolean ret = false;
		if (this.res == null || this.res.id != resID) {
			ResDefine resDefine = this.idDefine.get(resID);
			if (resDefine != null) ret = this.addValue(resDefine, value);
		}
		return ret;
	}
	
	/**
	 * 减少资源对应的数量
	 * @param resDefine 资源的定义
	 * @param value 资源减少的值(value > 0)
	 * @return true数值减少;false数值不变（资源不足、出错、value<=0等）
	 * */
	public boolean reduceValue(ResDefine resDefine, int value) {
		boolean ret = false;
		if (resDefine != null && value > 0 && !resDefine.equals(this.res)) {
			Integer previousValue = this.resValues.get(resDefine);
			if (previousValue != null && previousValue >= value) {
				previousValue -= value;
				if (previousValue == 0) {
					this.resList.remove(resDefine);
					this.resValues.remove(resDefine);
					this.idDefine.remove(resDefine.id);
				} else {
					this.resValues.put(resDefine, previousValue);
				}
				ret = true;
			}
		}
		return ret;
	}
	
	/**
	 * 减少资源对应的数量
	 * @param resID 资源的id
	 * @param value 资源减少的值(value > 0)
	 * @return true数值减少;false数值不变（资源不足、出错、value<=0等）
	 * */
	public boolean reduceValue(int resID, int value) {
		boolean ret = false;
		if (this.res == null || this.res.id != resID) {
			ResDefine resDefine = this.idDefine.get(resID);
			if (resDefine != null) ret = this.reduceValue(resDefine, value);
		}
		return ret;
	}
	
	/**
	 * 批量改变数量
	 * @param batchValue 资源名与对应的数量，如果
	 * @return true批量增减正常执行;false出错
	 * */
	public boolean batch(HashMap<ResDefine, Integer> batchValue) {
		return batch(batchValue, 1);
	}
	
	/**
	 * 批量改变数量
	 * @param batchValue 资源名与对应的数量
	 * @return true批量增减正常执行;false出错
	 * */
	public boolean batch(HashMap<ResDefine, Integer> batchValue, int times) {
		boolean ret = true;
		if (times != 0) {
			HashMap<ResDefine, Integer> testValues = new HashMap<ResDefine, Integer>(this.resValues);
			Iterator<Entry<ResDefine, Integer>> iter = batchValue.entrySet().iterator();
			Entry<ResDefine, Integer> entry = null;
			Integer value = null;
			ResDefine resDefine;
			Integer previousValue = null;
			while (iter.hasNext()) {
				entry = iter.next();
				resDefine = entry.getKey();
				value = entry.getValue() * times;
				if (value == 0) {	//没有增减直接跳过
					continue;
				} else { 			//有增减
					previousValue = testValues.get(resDefine);
					if (previousValue != null) {
						previousValue += value;
					} else {
						previousValue = value;
					}
					if (previousValue > 0) {
						testValues.put(resDefine, previousValue);
					} else if (previousValue == 0) {
						testValues.remove(resDefine);
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
		if (this.res != null) buffer.append(this.res);
		if (resList != null && resList.size() > 0) {
			String functionalDescription = functionalDescription();
			if (functionalDescription == null || functionalDescription.equals("")) functionalDescription = "需求";
			buffer.append(functionalDescription);
			buffer.append(":[");
			Iterator<Entry<ResDefine, Integer>> iter = this.resValues.entrySet().iterator();
			Entry<ResDefine, Integer> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				buffer.append(entry.getKey().toString()).append(":").append(entry.getValue().toString()).append(entry.getKey().unit);
				if (iter.hasNext()) buffer.append(", ");
			}
			buffer.append("]");
		} else {
			buffer.append("无对应");
		}
		return buffer.toString();
	}
}
