package czz.swt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

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
	protected ResBag resOffered;
	
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
	 * 购买商品
	 * @param commodity 商品
	 * @param cost 花费
	 * @param value 数量
	 * @return 1购买成功;返回值<0购买失败
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
						nowResOffer = this.resOffered.getResNumber(totalResDemandId);
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
						if (!this.resOffered.hasRes(totalResDemandId) || totalResDemandValue > nowResOffer) {
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
						this.resOffered.addRes(entry.getKey(), entry.getValue());			//增加提供物（骆驼提供负重等）
					}
					totalOfferList.clear();
					iter = totalDemandList.entrySet().iterator();		//商品占用资源集合迭代器
					while (iter.hasNext()) {
						this.resDemand.reduceRes(entry.getKey(), entry.getValue());			//减少占用资源（食物占用负重）
					}
					totalDemandList.clear();
					ret = 1;					//购买过程成功结束
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
	 * */
	public int checkBags() {
		int ret = 1;
		if (this.resBag.size() == 0) {			//资源背包大小为空
			//TODO
		} else {								//资源背包大小不为空
			//TODO
		}
		
		return ret;
	}
}
