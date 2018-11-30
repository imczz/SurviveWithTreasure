package czz.swt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 购买商品消耗资源，某资源带来资源（例如骆驼带来负重），某资源占用资源（食物占用负重）
 * */
public abstract class OfferAndDemand {

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
	 * @param costResValue 被消耗的资源与对应的值
	 * */
	public OfferAndDemand(int resID, HashMap<Integer, Integer> resValues) {
		this.id = resID;
		this.resList = new ArrayList<Integer>();
		this.resValues = new HashMap<Integer, Integer>();
		this.resList.addAll(resValues.keySet());
		this.resValues.putAll(resValues);
	}
	
	/**
	 * 功能的描述
	 * */
	protected abstract String functionalDescription();
	
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
