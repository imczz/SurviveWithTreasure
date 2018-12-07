package czz.swt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 资源存储空间，内含三个背包:资源背包resBag，资源提供资源背包resOffer，资源占用资源背包resDemand
 * */
public class Storage {

	/**
	 * 资源背包，里面包含所有的已有的资源，记录每种资源的数量
	 * */
	protected ResBag resBag;
	
	/**
	 * 背包中的资源提供的资源，比如“有了骆驼就有了负重”
	 * */
	protected ResBag resOffer;
	
	/**
	 * 背包中想要存放资源，需要占用的资源，比如“食物占用负重”
	 * */
	protected ResBag resDemand;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public Storage() {
		this.resBag = new ResBag();
		this.resOffer = new ResBag();
		this.resDemand = new ResBag();
	}
	
	/**
	 * 复制（拷贝）构造方法
	 * @param 待复制的存储空间
	 * */
	public Storage(Storage storage) {
		this.resBag = new ResBag(storage.getResBag());
		this.resOffer = new ResBag(storage.getResOffer());
		this.resDemand = new ResBag(storage.getResDemand());
	}
	
	/**
	 * 返回内部的资源背包（最好克隆后再修改）
	 * @return 资源背包
	 * */
	public ResBag getResBag() {
		return this.resBag;
	}
	
	/**
	 * 返回内部的提供物背包（最好克隆后再修改）
	 * @return 提供物背包
	 * */
	public ResBag getResOffer() {
		return this.resOffer;
	}
	
	/**
	 * 返回内部的占用背包（最好克隆后再修改）
	 * @return 占用背包
	 * */
	public ResBag getResDemand() {
		return this.resDemand;
	}
	
	/**
	 * 清空存储空间
	 * */
	public void clear() {
		this.resBag.clear();
		this.resOffer.clear();
		this.resDemand.clear();
	}
	
	/**
	 * 尝试增加资源，检验资源占用，不真正增加资源
	 * @param res 资源
	 * @param value 资源增加值(value >= 0),value=0返回false
	 * @return true可以增加;false不可以增加（value<0或者占用值不足等）
	 * */
	public boolean testAddRes(ResDefine res, int value) {
		boolean ret = false;
		if (value > 0) {
			ret = true;
			int resId = res.getId();
			Iterator<Entry<Integer, Integer>> iter = res.getDemand().getResValues().entrySet().iterator();
			Entry<Integer, Integer> entry = null;
			Integer leftOffer = 0;
			while (iter.hasNext()) {
				entry = iter.next();
				leftOffer = this.resOffer.getResNumber(resId) + this.resBag.getResNumber(resId) - this.resDemand.getResNumber(resId);	//剩余可占用
				if (entry.getValue() * value > leftOffer) {			//待占用>已占用(不允许某资源占用自己，所以不计算res.getOffer())
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 增加资源，不校验占用，只是单纯的增加
	 * @param res 资源
	 * @param value 资源增加值(value >= 0)value=0返回false
	 * @return true资源增加;false无法增加（value<0）
	 * */
	public boolean addRes(ResDefine res, int value) {
		boolean ret = false;
		if (value > 0) {				//可以增加资源	
			this.resBag.addRes(res, value);
			Iterator<Entry<Integer, Integer>> iter = res.getDemand().getResValues().entrySet().iterator();
			Entry<Integer, Integer> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				this.resDemand.addRes(res, entry.getValue() * value);	//增加资源占用
			}
			iter = res.getOffer().getResValues().entrySet().iterator();
			while (iter.hasNext()) {
				entry = iter.next();
				this.resOffer.addRes(res, entry.getValue() * value);	//增加资源提供
			}
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 带校验的增加资源，用于队伍、仓库等，用于保证资源大于占用
	 * @param res 资源
	 * @param value 资源增加值(value >= 0)value=0返回false
	 * @return true可以增加;false不可以增加（value<0或者占用值不足等）
	 * */
	public boolean addResWithValidate(ResDefine res, int value) {
		boolean ret = false;
		if (testAddRes(res, value)) {	//经过检验
			addRes(res, value);				//增加资源
		}
		return ret;
	}
	
	/**
	 * 尝试减少资源，检验资源数量及提供物，不真正减少资源
	 * @param res 资源
	 * @param value 资源减少值(value >= 0),value=0返回false
	 * @return true可以增加;false不可以增加（value<0或者占用值不足等）
	 * */
	public boolean testReduceRes(ResDefine res, int value) {
		boolean ret = false;
		if (value > 0 && this.resBag.getResNumber(res.getId()) > value) {
			Offer testOffer = new Offer(0, this.getResOffer().toResValue());			//总提供
			Demand testDemand = new Demand(0, this.getResDemand().toResValue());		//总占用
			testOffer.batch(this.resBag.toResValue());							//背包中的资源也作为提供
			testOffer.batch(res.getOffer().getResValues(), -value);					//去掉当前影响（提供）
			testDemand.batch(res.getDemand().getResValues(), -value);				//去掉当前影响（占用）
			if (testOffer.contains(testDemand)) ret = true;					//提供的资源仍然大于占用
		}
		return ret;
	}
	
	/**
	 * 减少资源，需要验证资源的数量，不验证资源占用
	 * @param res 资源
	 * @param value 资源增加值(value >= 0)value=0返回false
	 * @return true资源减少;false资源未减少
	 * */
	public boolean ReduceRes(ResDefine res, int value) {
		boolean ret = false;
		if (value > 0 && this.resBag.getResNumber(res.getId()) > value) {				//可以减少资源
			this.resBag.reduceRes(res.getId(), value);
			Iterator<Entry<Integer, Integer>> iter = res.getDemand().getResValues().entrySet().iterator();
			Entry<Integer, Integer> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				this.resDemand.reduceRes(entry.getKey(), entry.getValue() * value);	//增加资源占用
			}
			iter = res.getOffer().getResValues().entrySet().iterator();
			while (iter.hasNext()) {
				entry = iter.next();
				this.resOffer.reduceRes(entry.getKey(), entry.getValue() * value);	//增加资源提供
			}
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 尝试同时增加一组资源
	 * @param resMap 资源与资源对应的数量表
	 * @return true可以增加;false提供资源不足以占用
	 * */
	public boolean testChangeResList(HashMap<Integer, Pair<ResDefine, Integer>> resMap) {
		boolean ret = false;
		if (resMap == null || resMap.size() == 0) {
			ret = true;
		} else {		//resMap不为空
			Offer testOffer = new Offer(0, this.resOffer.toResValue());			//装入自身的提供
			Demand testDemand = new Demand(0, this.resDemand.toResValue());		//装入自身的占用
			testOffer.batch(this.resBag.toResValue());
			Iterator<Entry<Integer, Pair<ResDefine, Integer>>> iter = resMap.entrySet().iterator();
			Entry<Integer, Pair<ResDefine, Integer>> entry = null;
			Pair<ResDefine, Integer> pair = null;
			ResDefine thisRes = null;
			int resValue = 0;
			Demand resDemand = null;
			Offer resOffer = null;
			while (iter.hasNext()) {
				entry = iter.next();
				pair = entry.getValue();
				thisRes = pair.first;					//资源
				resValue = pair.second;					//资源的数量
				testOffer.addValue(thisRes.getId(), resValue);				//新加的资源也可以被占用
				resDemand = thisRes.getDemand();			//此资源的资源占用
				resOffer = thisRes.getOffer();				//此资源的资源提供
				testDemand.batch(resDemand.resValues, resValue);	//累加资源占用
				testOffer.batch(resOffer.resValues, resValue);		//累加资源提供
			}
			if (testOffer.contains(testDemand)) ret = true;			//提供物大于待占用
		}
		return ret;
	}
	
	/**
	 * 同时增加一组资源
	 * @param resMap 资源与资源对应的数量表
	 * @return true：资源增加;false：resMap为空
	 * */
	public boolean addResList(HashMap<Integer, Pair<ResDefine, Integer>> resMap) {
		boolean ret = false;
		if (resMap != null && resMap.size() > 0) {
			ResBag resBag = new ResBag();
			Iterator<Entry<Integer, Pair<ResDefine, Integer>>> iter = resMap.entrySet().iterator();
			Entry<Integer, Pair<ResDefine, Integer>> entry = null;
			Pair<ResDefine, Integer> pair = null;
			ResDefine thisRes = null;
			int resValue = 0;
			Demand resDemand = null;
			Offer resOffer = null;
			while (iter.hasNext()) {
				entry = iter.next();
				pair = entry.getValue();
				thisRes = pair.first;					//资源
				resValue = pair.second;					//资源的数量
				this.resBag.addRes(thisRes, resValue);
			}
			ret = true;
		}
		return ret;
	}
	
	
}
