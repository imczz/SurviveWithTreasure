package czz.swt;

import java.util.HashMap;

/**
 * 资源消耗记录
 * @author CZZ
 * */
public class Cost extends OfferAndDemand{
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * @param id 资源的id
	 * @param resValues 消耗的资源与对应的数量
	 * */
	public Cost(int id, HashMap<Integer, Integer> resValues) {
		super(id, resValues);
	}
	
	@Override
	protected String functionalDescription() {
		return "需求";
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
			buffer.append("需要:[");
			for (int i = 0; i < resList.size(); i++) {
				
			}
			buffer.append("]");
		} else {
			buffer.append("无消耗");
		}
		return buffer.toString();
	}

}
