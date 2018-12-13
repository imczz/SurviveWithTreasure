package czz.swt;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * 资源存储空间，内含三个背包:资源背包resBag，资源提供资源背包resOffer，资源占用资源背包resDemand
 * @author CZZ
 * */
public class Storage {

	/**
	 * 资源背包，里面包含所有的已有的资源，记录每种资源的数量
	 * */
	protected ResBag resBag;
	
	/**
	 * 背包中的资源提供的资源总数，比如“有了骆驼就有了负重”
	 * */
	protected Offer resOffer;
	
	/**
	 * 背包中想要存放资源，需要占用的资源总数，比如“食物占用负重”
	 * */
	protected Demand resDemand;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * @param id 存储空间id
	 * @param name 存储空间名
	 * */
	public Storage() {
		this.resBag = new ResBag();
		this.resOffer = new Offer();
		this.resDemand = new Demand();
	}
	
	/**
	 * 构造方法2
	 * @param resBag 资源背包
	 * */
	public Storage(ResBag resBag) {
		this.resBag = new ResBag();
		this.resOffer = new Offer();
		this.resDemand = new Demand();
		this.addBagWithValidate(resBag);
	}
	
	/**
	 * 构造方法3
	 * @param resBag 资源背包
	 * @param offer 资源提供
	 * @param demand 资源占用
	 * */
	public Storage(ResBag resBag, Offer offer, Demand demand) {
		this.resBag = new ResBag(resBag);
		this.resOffer = new Offer(offer);
		this.resDemand = new Demand(demand);
	}
	
	/**
	 * 复制（拷贝）构造方法
	 * @param 待复制的存储空间
	 * */
	public Storage(Storage storage) {
		this.resBag = new ResBag(storage.getResBag());
		this.resOffer = new Offer(storage.getResOffer());
		this.resDemand = new Demand(storage.getResDemand());
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
	public Offer getResOffer() {
		return this.resOffer;
	}
	
	/**
	 * 返回内部的占用背包（最好克隆后再修改）
	 * @return 占用背包
	 * */
	public Demand getResDemand() {
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
		if (res != null && value > 0) {
			//ResBag testBag = new ResBag(this.resBag);
			Offer testOffer = new Offer(this.resOffer);
			Demand testDemand = new Demand(this.resDemand);
			Iterator<Entry<ResDefine, Integer>> iter = res.resDefine.demand.getResValues().entrySet().iterator();
			Entry<ResDefine, Integer> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				testDemand.addValue(res.resDefine, entry.getValue() * value);	//增加资源占用
			}
			iter = res.resDefine.offer.getResValues().entrySet().iterator();
			while (iter.hasNext()) {
				entry = iter.next();
				testOffer.addValue(res.resDefine, entry.getValue() * value);	//增加资源提供
			}
			if (testOffer.contains(testDemand)) ret = true;					//提供物的可以覆盖待占用
		}
		return ret;
	}
	
	/**
	 * 增加资源，不校验占用，只是单纯的增加
	 * @param res 资源
	 * @param value 资源增加值(value >= 0)value=0返回false
	 * @return true资源增加;false无法增加（value<0）
	 * */
	public boolean addRes(Res res, int value) {
		boolean ret = false;
		if (res != null && value > 0) {				//可以增加资源	
			if (this.resBag.addRes(res, value)) {		//增加资源成功
				Iterator<Entry<ResDefine, Integer>> iter = res.resDefine.demand.getResValues().entrySet().iterator();
				Entry<ResDefine, Integer> entry = null;
				while (iter.hasNext()) {
					entry = iter.next();
					this.resDemand.addValue(res.resDefine, entry.getValue() * value);	//增加资源占用
				}
				iter = res.resDefine.offer.getResValues().entrySet().iterator();
				while (iter.hasNext()) {
					entry = iter.next();
					this.resOffer.addValue(res.resDefine, entry.getValue() * value);	//增加资源提供
				}
				ret = true;
			}
		}
		return ret;
	}
	
	/**
	 * 增加资源组，不校验占用，只是单纯的增加
	 * @param resPackage 资源组
	 * @return true资源增加;false无法增加（value<0）
	 * */
	public boolean addRes(ResPackage resPackage) {
		boolean ret = false;
		if (resPackage != null && resPackage.getCount() > 0) {				//可以增加资源
			ret = this.addRes(resPackage.res, resPackage.getCount());
		}
		return ret;
	}
	
	/**
	 * 带校验的增加资源，用于队伍、仓库等，用于保证资源大于占用
	 * @param res 资源
	 * @param value 资源增加值(value >= 0)value=0返回false
	 * @return true可以增加;false不可以增加（value<0或者占用值不足等）
	 * @see #testAddRes(Res, int)
	 * @see #addRes(Res, int)
	 * */
	public boolean addResWithValidate(Res res, int value) {
		boolean ret = false;
		if (testAddRes(res, value)) {	//经过检验
			ret = addRes(res, value);				//增加资源
		}
		return ret;
	}
	
	/**
	 * 尝试减少资源，检验资源数量及提供物，不真正减少资源
	 * @param res 资源
	 * @param value 资源减少值(value >= 0),value=0返回false
	 * @return true可以减少;false不可以减少（value<0或者占用值不足等）
	 * */
	public boolean testReduceRes(Res res, int value) {
		boolean ret = false;
		if (res != null && value > 0) {
			ResBag testBag = new ResBag(this.resBag);
			if (testBag.reduceRes(res, value)) {
				Offer testOffer = new Offer(this.resOffer);
				Demand testDemand = new Demand(this.resDemand);
				Iterator<Entry<ResDefine, Integer>> iter = res.resDefine.demand.getResValues().entrySet().iterator();
				Entry<ResDefine, Integer> entry = null;
				while (iter.hasNext()) {
					entry = iter.next();
					testDemand.reduceValue(res.resDefine, entry.getValue() * value);	//增加资源占用
				}
				iter = res.resDefine.offer.getResValues().entrySet().iterator();
				while (iter.hasNext()) {
					entry = iter.next();
					testOffer.reduceValue(res.resDefine, entry.getValue() * value);	//增加资源提供
				}
				if (testOffer.contains(testDemand)) ret = true;					//提供物的可以覆盖待占用
			}
		}
		return ret;
	}
	
	/**
	 * 减少资源，不校验占用
	 * @param res 资源
	 * @param value 资源减少值(value >= 0)value=0返回false
	 * @return true资源减少;false无法减少（value<0）
	 * */
	private boolean reduceRes(Res res, int value) {
		boolean ret = false;
		if (res != null && value > 0) {				//可以增加资源	
			if (this.resBag.reduceRes(res, value)) {		//减少资源成功
				Iterator<Entry<ResDefine, Integer>> iter = res.resDefine.demand.getResValues().entrySet().iterator();
				Entry<ResDefine, Integer> entry = null;
				while (iter.hasNext()) {
					entry = iter.next();
					this.resDemand.reduceValue(res.resDefine, entry.getValue() * value);	//减少资源占用
				}
				iter = res.resDefine.offer.getResValues().entrySet().iterator();
				while (iter.hasNext()) {
					entry = iter.next();
					this.resOffer.reduceValue(res.resDefine, entry.getValue() * value);		//减少资源提供
				}
				ret = true;
			}
		}
		return ret;
	}
	
	/**
	 * 减少资源，需要验证资源的数量，不验证资源占用
	 * @param res 资源
	 * @param value 资源减少值(value >= 0)value=0返回false
	 * @return true资源减少;false资源未减少
	 * @see #testReduceRes(Res, int)
	 * @see #reduceRes(Res, int)
	 * */
	public boolean ReduceResWithValidate(Res res, int value) {
		boolean ret = false;
		if (testReduceRes(res, value)) {	//经过检验
			ret = reduceRes(res, value);				//增加资源
		}
		return ret;
	}
	
	/**
	 * 尝试装入资源背包
	 * @param resBag 带装入资源背包
	 * @return true可以增加;false提供资源不足以占用
	 * */
	public boolean testAddBag(ResBag resBag) {
		boolean ret = false;
		if (resBag != null) {
			ResBag testBag = new ResBag(this.resBag);
			Offer testOffer = new Offer(this.resOffer);
			Demand testDemand = new Demand(this.resDemand);
			Iterator<Entry<ResDefine, List<ResPackage>>> iter = resBag.getBag().entrySet().iterator();
			Entry<ResDefine, List<ResPackage>> entry = null;
			ResDefine resDefine = null;
			Integer count = 0;
			boolean flag = true;
			Iterator<Entry<ResDefine, Integer>> iterOD = null;
			Entry<ResDefine, Integer> entryOD = null;
			while (iter.hasNext()) {
				entry = iter.next();
				resDefine = entry.getKey();
				if (testBag.batchAdd(entry.getValue())) {
					count = resBag.getResCount(resDefine);
					iterOD = resDefine.offer.getResValues().entrySet().iterator();
					while (iterOD.hasNext()) {
						entryOD = iterOD.next();
						testDemand.reduceValue(resDefine, entryOD.getValue() * count);	//减少资源占用
					}
					iterOD = resDefine.offer.getResValues().entrySet().iterator();
					while (iterOD.hasNext()) {
						entryOD = iterOD.next();
						testOffer.reduceValue(resDefine, entryOD.getValue() * count);		//减少资源提供
					}
				} else {
					flag = false;
					break;
				}
			}
			if (flag) {
				if (testOffer.contains(testDemand)) ret = true;
			}
		}
		return ret;
	}
	
	/**
	 * 装入资源背包
	 * @param resBag 资源背包
	 * @return true：资源增加;false：resBag为空
	 * */
	public boolean addBag(ResBag resBag) {
		boolean ret = false;
		if (resBag != null) {
			Iterator<Entry<ResDefine, List<ResPackage>>> iter = resBag.getBag().entrySet().iterator();
			Entry<ResDefine, List<ResPackage>> entry = null;
			ResDefine resDefine = null;
			Integer count = 0;
			Iterator<Entry<ResDefine, Integer>> iterOD = null;
			Entry<ResDefine, Integer> entryOD = null;
			while (iter.hasNext()) {
				entry = iter.next();
				resDefine = entry.getKey();
				this.resBag.batchAdd(entry.getValue());
				count = resBag.getResCount(resDefine);
				iterOD = resDefine.offer.getResValues().entrySet().iterator();
				while (iterOD.hasNext()) {
					entryOD = iterOD.next();
					this.resDemand.reduceValue(resDefine, entryOD.getValue() * count);	//减少资源占用
				}
				iterOD = resDefine.offer.getResValues().entrySet().iterator();
				while (iterOD.hasNext()) {
					entryOD = iterOD.next();
					this.resOffer.reduceValue(resDefine, entryOD.getValue() * count);		//减少资源提供
				}
			}
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 带校验的装入资源背包，检查资源提供物是否满足待占用
	 * @param resBag 资源背包
	 * @return true装入成功;false失败(检验未通过等)
	 * @see #testAddBag(ResBag)
	 * @see #addBag(ResBag)
	 * */
	public boolean addBagWithValidate(ResBag resBag) {
		boolean ret = false;
		if (resBag != null) {
			if (this.testAddBag(resBag)) {
				ret = this.addBag(resBag);
			}
		}
		return ret;
	}
	
	/**
	 * 测试装入存储仓库后是否可以提供满足待占用
	 * @param storage 存储仓库
	 * @return true提供满足待占用；false不满足
	 * */
	public boolean testAddStorage(Storage storage) {
		boolean ret = false;
		if (storage != null) {			//存储仓库不为空
			Offer offer = new Offer(this.resOffer);
			Demand demand = new Demand(this.resDemand);
			offer.batch(storage.resOffer.resValues);			//新提供
			demand.batch(storage.resDemand.resValues);			//新占用
			if (offer.contains(demand)) ret = true;			//提供满足占用
		}
		return ret;
	}
	
	/**
	 * 将另一个仓库与此仓库合并
	 * @param storage 存储仓库
	 * @return true合并成功；false失败
	 * */
	public boolean addStorage(Storage storage) {
		boolean ret = false;
		if (storage != null) {			//存储仓库不为空
			Iterator<Entry<ResDefine, List<ResPackage>>> iter = storage.resBag.getBag().entrySet().iterator();
			Entry<ResDefine, List<ResPackage>> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				this.resBag.batchAdd(entry.getValue());
			}
			this.resOffer.batch(storage.resOffer.resValues);
			this.resDemand.batch(storage.resDemand.resValues);
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 待校验的存储仓库合并
	 * @param 待并入的存储仓库
	 * @return true合并成功;false合并失败
	 * @see #testAddStorage(Storage)
	 * @see #addStorage(Storage)
	 * */
	public boolean addStorageWithValidate(Storage storage) {
		boolean ret = false;
		if (storage != null) {			//存储仓库不为空
			if (this.testAddStorage(storage)) {
				ret = this.addStorage(storage);
			}
		}
		return ret;
	}
	
}
