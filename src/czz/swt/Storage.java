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
	public boolean testAddRes(Res res, int value) {
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
	 * @return true可以增加;false不可以增加（value<0或者占用值不足等）
	 * */
	public boolean addRes(Res res, int value) {
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
	public boolean addResWithValidate(Res res, int value) {
		boolean ret = false;
		if (testAddRes(res, value)) {	//经过检验
			addRes(res, value);				//增加资源
		}
		return ret;
	}
	
	/**
	 * 尝试同时增加一组资源
	 * */
	public boolean testAddResList(HashMap<Integer, Pair<Res, Integer>> resMap) {
		boolean ret = false;
		HashMap<Integer, Integer> testOffer = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> testDemand = new HashMap<Integer, Integer>();
		Iterator<Entry<Integer, Pair<Res, Integer>>> iter = resMap.entrySet().iterator();
		Entry<Integer, Pair<Res, Integer>> entry = null;
		Pair<Res, Integer> pair = null;
		Res thisRes = null;
		Demand resDemand = null;
		Offer resOffer = null;
		while (iter.hasNext()) {
			entry = iter.next();
			pair = entry.getValue();
			thisRes = pair.first;
			resDemand = thisRes.getDemand();
			resOffer = thisRes.getOffer();
			//resDemand.
		}
		return ret;
	}
	
	/**
	 * 同时增加一组资源
	 * */
	public boolean addResList() {
		boolean ret = false;
		//TODO
		return ret;
	}
	
	
}
