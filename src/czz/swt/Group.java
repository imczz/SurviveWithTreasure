package czz.swt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import czz.util.MyCompare;

/**
 * 队伍是参加这个游戏的基本单位
 * @author CZZ
 * */
public class Group extends EntityWithLabelAndState{
	
	/**
	 * 游戏引擎的引用
	 * */
	private Engine engine;
	
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
	 * @param id 节点的id
	 * */
	public Group(Engine engine, int id) {
		super(id);
	}
	
	/**
	 * 构造方法2
	 * @param id 节点的id
	 * @param name 节点的名称
	 * */
	public Group(Engine engine, int id, String name) {
		super(id, name);
	}
	
	/**
	 * 尝试增加资源，不真正增加资源
	 * @param resId 资源id
	 * @param value 资源增加值(value >= 0),value=0返回false
	 * @return true可以增加;false不可以增加（value<0或者占用值不足等）
	 * */
	public boolean tryAddRes(int resId, int value) {
		boolean ret = false;
		if (value > 0) {
			ret = true;
			Iterator<Entry<Integer, Integer>> iter = this.engine.getSettingTable().getRes(resId)
					.getDemand().getResValues().entrySet().iterator();
			Entry<Integer, Integer> entry = null;
			Integer leftOffer = 0;
			while (iter.hasNext()) {
				entry = iter.next();
				leftOffer = this.resOffer.getResNumber(resId) + this.resBag.getResNumber(resId) - this.resDemand.getResNumber(resId);
				if (entry.getValue() > leftOffer) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 增加资源
	 * @param resId 资源id
	 * @param value 资源增加值(value >= 0)value=0返回false
	 * @return true可以增加;false不可以增加（value<0或者占用值不足等）
	 * */
	public boolean addRes(int resId, int value) {
		boolean ret = false;
		if (tryAddRes(resId, value)) {				//可以增加资源	
			this.resBag.addRes(resId, value);
			Iterator<Entry<Integer, Integer>> iter = this.engine.getSettingTable().getRes(resId)
					.getDemand().getResValues().entrySet().iterator();
			Entry<Integer, Integer> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				this.resDemand.addRes(entry.getKey(), entry.getValue());//增加资源占用
			}
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 尝试同时增加一组资源
	 * */
	public boolean tryAddResList() {
		boolean ret = false;
		//TODO
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
	
	/**
	 * 购买商品
	 * @param commodity 商品
	 * @param cost 花费
	 * @param value 数量
	 * @return 1购买成功;返回值<0购买失败；-1:资金不足；-2待占用资源不足；
	 * */
	public int buy(Commodity commodity, Cost cost, int value) {
		int ret = 0;
		if (commodity != null && cost != null && value > 0) {
			HashMap<Integer, Integer> paid = new HashMap<Integer, Integer>();
			//检查资金是否充足
			Iterator<Entry<Integer, Integer>> iter = null;					//Entry迭代器
			Entry<Integer, Integer> entry = null;						//HashMap中的enrty
			iter = cost.getResValues().entrySet().iterator();
			boolean isSufficientFunds = true;				//资金充足
			while (iter.hasNext()) {
				entry = iter.next();
				if (resBag.getResNumber(entry.getKey()) < entry.getValue()) {
					isSufficientFunds = false;				//资金不足
					ret = -1;	//资金不足
					break;
				} else {
					paid.put(entry.getKey(), entry.getValue());			//预付款
					this.resBag.reduceRes(entry.getKey(), entry.getValue());		//预扣款
				}
			}
			if (isSufficientFunds) {				//在资金充足的情况下（ret = 0）
				//检查资源占用
				List<Pair<Integer, Integer>> resList = commodity.getResCountList();			//商品列表(资源id, 资源数量)
				Pair<Integer, Integer> pair = null;				//(资源id, 资源数量)
				Res resTemp = null;
				Demand resTempDemand = null;
				Offer resTempOffer = null;
				HashMap<Integer, Integer> increaseResList = new HashMap<Integer, Integer>();	//待收货的资源
				HashMap<Integer, Integer> totalDemandList = new HashMap<Integer, Integer>();	//商品总占用
				HashMap<Integer, Integer> totalOfferList = new HashMap<Integer, Integer>();		//商品总提供物
				for (int i = 0, length = resList.size(); i < length; i++) {
					pair = resList.get(i);				//拆开商品的包装，获取第一个资源，资源id为pair.first，数量为pair.second
					increaseResList.put(pair.first, pair.second);
					resTemp = this.engine.getSettingTable().getRes(pair.first);
					resTempDemand = resTemp.getDemand();							//资源占用
					iter = resTempDemand.getResValues().entrySet().iterator();		//资源占用集合迭代器
					int demandId = -1;
					Integer demandValue = -1;
					int newDemandValue = -1;
					while (iter.hasNext()) {
						entry = iter.next();			//每个id为pair.first的资源，占用资源entry.getKey()数量为entry.getValue()
						demandId = entry.getKey();
						demandValue = totalDemandList.get(demandId);
						newDemandValue = entry.getValue() * pair.second * value;		//每个商品含有pair.second个资源，共有value个商品
						if (demandValue != null) newDemandValue += demandValue;			//累加资源占用
						totalDemandList.put(entry.getKey(), newDemandValue);				//更新资源占用值
					}
					resTempOffer = resTemp.getOffer();								//资源提供物
					iter = resTempOffer.getResValues().entrySet().iterator();		//资源提供物集合迭代器
					int offerId = -1;
					Integer offerValue = -1;
					int newOfferValue = -1;
					while (iter.hasNext()) {
						entry = iter.next();			//每个id为pair.first的资源，占用资源entry.getKey()数量为entry.getValue()
						offerId = entry.getKey();
						offerValue = totalOfferList.get(offerId);
						newOfferValue = entry.getValue() * pair.second * value;			//每个商品含有pair.second个资源，共有value个商品
						if (offerValue != null) newOfferValue += offerValue;			//累加资源提供物
						totalOfferList.put(entry.getKey(), newOfferValue);					//更新资源提供物数量
					}
				}//for (int i = 0, length = resList.size(); i < length; i++)
				if (totalDemandList.size() > 0) {
					//依次检查占用情况
					iter = totalDemandList.entrySet().iterator();				//统计的所有需求总计的迭代器
					int totalResDemandId = -1;						//需要占用的资源id
					int totalResDemandValue = 0;					//需要占用的资源对应的数量
					Integer nowResOffer = 0;							//现在已经具有的资源
					while (iter.hasNext()) {
						entry = iter.next();							//想存放某资源，需要占用的资源
						totalResDemandId = entry.getKey();				//资源的id
						totalResDemandValue = entry.getValue();			//占用的
						nowResOffer = this.resOffer.getResNumber(totalResDemandId);
						if (nowResOffer == null) nowResOffer = 0;				//没有可用的资源
						if (this.resBag.hasRes(totalResDemandId)) {		//资源提供的资源通常是抽象的，但是也有特例，比如天生的负重
							nowResOffer += this.resBag.getResNumber(totalResDemandId);	//背包中的资源
						}
						if (increaseResList.containsKey(totalResDemandId)) {
							nowResOffer += increaseResList.get(totalResDemandId);		//待收货的资源
						}
						if (totalOfferList.containsKey(totalResDemandId)) {
							nowResOffer += totalOfferList.get(totalResDemandId);		//待收货的资源提供的资源
						}
						if (!this.resOffer.hasRes(totalResDemandId) || totalResDemandValue > nowResOffer) {
							ret = -2;							//待占用资源不足
							break;
						}
					}
				}//if (totalDemandList.size() > 0)
				if (ret == 0) {				//资金充足，且资源占用检验通过
					iter = increaseResList.entrySet().iterator();		//商品内含资源集合迭代器
					while (iter.hasNext()) {
						this.resBag.addRes(entry.getKey(), entry.getValue());			//收货
					}
					increaseResList.clear();
					iter = totalOfferList.entrySet().iterator();		//商品提供资源集合迭代器
					while (iter.hasNext()) {
						this.resOffer.addRes(entry.getKey(), entry.getValue());			//增加提供物（骆驼提供负重等）
					}
					totalOfferList.clear();
					iter = totalDemandList.entrySet().iterator();		//商品占用资源集合迭代器
					while (iter.hasNext()) {
						this.resDemand.reduceRes(entry.getKey(), entry.getValue());			//减少占用资源（食物占用负重）
					}
					totalDemandList.clear();
					ret = 1;					//购买过程成功结束
					try {
						if (checkBags() <= 0) throw new Exception("error");
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}//if (isSufficientFunds)
			if (ret < 0) {						//资金不充足，或者资源占用检验未通过，此时需要退款
				iter = paid.entrySet().iterator();		//预收款集合迭代器
				while (iter.hasNext()) {
					entry = iter.next();
					this.resBag.addRes(entry.getKey(), entry.getValue());			//退预收款
				}
				paid.clear();
			}
		}
		return ret;
	}
	
	/**
	 * 检查背包错误
	 * @return 返回值>0正常，<=0则有错误
	 * */
	public int checkBags() {
		int ret = 1;			//正常
		if (this.resBag.size() == 0) {			//资源背包大小为空
			if (this.resDemand.size() == 0 && this.resOffer.size() == 0) ret = 2;		//全为空
			else ret = -1;															//空穴来风
		} else {								//资源背包大小不为空
			Iterator<Entry<Integer, Integer>> resBagIter = this.resBag.bag.entrySet().iterator();	//Entry迭代器
			Entry<Integer, Integer> resEntry = null;						//HashMap中的enrty
			Res resTemp = null;
			Demand resTempDemand = null;
			Offer resTempOffer = null;
			HashMap<Integer, Integer> totalDemand = new HashMap<Integer, Integer>();					//统计的总占用
			HashMap<Integer, Integer> totalOffer = new HashMap<Integer, Integer>();						//统计总提供物
			Iterator<Entry<Integer, Integer>> iter = null;				//Entry迭代器
			Entry<Integer, Integer> entry = null;						//HashMap中的enrty
			int resNumber = 0;
			int previousValue = 0;
			while (resBagIter.hasNext()) {
				resEntry = resBagIter.next();
				resTemp = this.engine.getSettingTable().getRes(resEntry.getKey());
				resNumber = resEntry.getValue();
				resTempDemand = resTemp.getDemand();					//统计此资源占用
				iter = resTempDemand.getResValues().entrySet().iterator();
				while (iter.hasNext()) {
					entry = iter.next();
					previousValue = totalDemand.getOrDefault(entry.getKey(), 0);
					totalDemand.put(entry.getKey(), previousValue + (entry.getValue() * resNumber));
				}
				resTempOffer = resTemp.getOffer();						//统计此资源提供
				iter = resTempOffer.getResValues().entrySet().iterator();
				while (iter.hasNext()) {
					entry = iter.next();
					previousValue = totalOffer.getOrDefault(entry.getKey(), 0);
					totalOffer.put(entry.getKey(), previousValue + (entry.getValue() * resNumber));
				}
			}
			if (totalDemand.size() != this.resDemand.size()) ret--;
			if (totalOffer.size() != this.resOffer.size()) ret--;
			if (!MyCompare.compairHashMap(totalDemand, this.resDemand.bag)) ret--;
			if (!MyCompare.compairHashMap(totalOffer, this.resOffer.bag)) ret--;
		}
		return ret;
	}
	
	/**
	 * 出售商品
	 * @param commodity 商品
	 * @param cost 花费
	 * @param value 数量
	 * @return 1购买成功;返回值<0购买失败；
	 * */
	public int sale(Commodity commodity, Cost cost, int value) {
		int ret = 0;
		if (commodity != null && cost != null && value > 0) {
			HashMap<Integer, Integer> paid = new HashMap<Integer, Integer>();
			//检查资金是否充足
			Iterator<Entry<Integer, Integer>> iter = null;					//Entry迭代器
			Entry<Integer, Integer> entry = null;						//HashMap中的enrty
			iter = cost.getResValues().entrySet().iterator();
			boolean isSufficientFunds = true;				//资金充足
			while (iter.hasNext()) {
				entry = iter.next();
				if (resBag.getResNumber(entry.getKey()) < entry.getValue()) {
					isSufficientFunds = false;				//资金不足
					ret = -1;//资金不足
					break;
				} else {
					paid.put(entry.getKey(), entry.getValue());			//预付款
					this.resBag.reduceRes(entry.getKey(), entry.getValue());		//预扣款
				}
			}
			if (isSufficientFunds) {				//在资金充足的情况下（ret = 0）
				//检查资源占用
				List<Pair<Integer, Integer>> resList = commodity.getResCountList();			//商品列表(资源id, 资源数量)
				Pair<Integer, Integer> pair = null;				//(资源id, 资源数量)
				Res resTemp = null;
				Demand resTempDemand = null;
				Offer resTempOffer = null;
				HashMap<Integer, Integer> increaseResList = new HashMap<Integer, Integer>();	//待收货的资源
				HashMap<Integer, Integer> totalDemandList = new HashMap<Integer, Integer>();	//商品总占用
				HashMap<Integer, Integer> totalOfferList = new HashMap<Integer, Integer>();		//商品总提供物
				for (int i = 0, length = resList.size(); i < length; i++) {
					pair = resList.get(i);				//拆开商品的包装，获取第一个资源，资源id为pair.first，数量为pair.second
					increaseResList.put(pair.first, pair.second);
					resTemp = this.engine.getSettingTable().getRes(pair.first);
					resTempDemand = resTemp.getDemand();							//资源占用
					iter = resTempDemand.getResValues().entrySet().iterator();		//资源占用集合迭代器
					int demandId = -1;
					Integer demandValue = -1;
					int newDemandValue = -1;
					while (iter.hasNext()) {
						entry = iter.next();			//每个id为pair.first的资源，占用资源entry.getKey()数量为entry.getValue()
						demandId = entry.getKey();
						demandValue = totalDemandList.get(demandId);
						newDemandValue = entry.getValue() * pair.second * value;		//每个商品含有pair.second个资源，共有value个商品
						if (demandValue != null) newDemandValue += demandValue;			//累加资源占用
						totalDemandList.put(entry.getKey(), newDemandValue);				//更新资源占用值
					}
					resTempOffer = resTemp.getOffer();								//资源提供物
					iter = resTempOffer.getResValues().entrySet().iterator();		//资源提供物集合迭代器
					int offerId = -1;
					Integer offerValue = -1;
					int newOfferValue = -1;
					while (iter.hasNext()) {
						entry = iter.next();			//每个id为pair.first的资源，占用资源entry.getKey()数量为entry.getValue()
						offerId = entry.getKey();
						offerValue = totalOfferList.get(offerId);
						newOfferValue = entry.getValue() * pair.second * value;			//每个商品含有pair.second个资源，共有value个商品
						if (offerValue != null) newOfferValue += offerValue;			//累加资源提供物
						totalOfferList.put(entry.getKey(), newOfferValue);					//更新资源提供物数量
					}
				}//for (int i = 0, length = resList.size(); i < length; i++)
				if (totalDemandList.size() > 0) {
					//依次检查占用情况
					iter = totalDemandList.entrySet().iterator();				//统计的所有需求总计的迭代器
					int totalResDemandId = -1;						//需要占用的资源id
					int totalResDemandValue = 0;					//需要占用的资源对应的数量
					Integer nowResOffer = 0;							//现在已经具有的资源
					while (iter.hasNext()) {
						entry = iter.next();							//想存放某资源，需要占用的资源
						totalResDemandId = entry.getKey();				//资源的id
						totalResDemandValue = entry.getValue();			//占用的
						nowResOffer = this.resOffer.getResNumber(totalResDemandId);
						if (nowResOffer == null) nowResOffer = 0;				//没有可用的资源
						if (this.resBag.hasRes(totalResDemandId)) {		//资源提供的资源通常是抽象的，但是也有特例，比如天生的负重
							nowResOffer += this.resBag.getResNumber(totalResDemandId);	//背包中的资源
						}
						if (increaseResList.containsKey(totalResDemandId)) {
							nowResOffer += increaseResList.get(totalResDemandId);		//待收货的资源
						}
						if (totalOfferList.containsKey(totalResDemandId)) {
							nowResOffer += totalOfferList.get(totalResDemandId);		//待收货的资源提供的资源
						}
						if (!this.resOffer.hasRes(totalResDemandId) || totalResDemandValue > nowResOffer) {
							ret = -2;							//待占用资源不足
							break;
						}
					}
				}//if (totalDemandList.size() > 0)
				if (ret == 0) {				//资金充足，且资源占用检验通过
					iter = increaseResList.entrySet().iterator();		//商品内含资源集合迭代器
					while (iter.hasNext()) {
						this.resBag.addRes(entry.getKey(), entry.getValue());			//收货
					}
					increaseResList.clear();
					iter = totalOfferList.entrySet().iterator();		//商品提供资源集合迭代器
					while (iter.hasNext()) {
						this.resOffer.addRes(entry.getKey(), entry.getValue());			//增加提供物（骆驼提供负重等）
					}
					totalOfferList.clear();
					iter = totalDemandList.entrySet().iterator();		//商品占用资源集合迭代器
					while (iter.hasNext()) {
						this.resDemand.reduceRes(entry.getKey(), entry.getValue());			//减少占用资源（食物占用负重）
					}
					totalDemandList.clear();
					ret = 1;					//购买过程成功结束
					try {
						if (checkBags() <= 0) throw new Exception("error");
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}//if (isSufficientFunds)
			if (ret < 0) {						//资金不充足，或者资源占用检验未通过，此时需要退款
				iter = paid.entrySet().iterator();		//预收款集合迭代器
				while (iter.hasNext()) {
					entry = iter.next();
					this.resBag.addRes(entry.getKey(), entry.getValue());			//退预收款
				}
				paid.clear();
			}
		}
		return ret;
	}
	
}
