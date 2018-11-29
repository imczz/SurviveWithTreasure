package czz.swt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 资源消耗记录
 * @author CZZ
 * */
public class Cost {

	/**
	 * 资源id
	 * */
	private int id;
	
	/**
	 * 需要消耗的资源列表
	 * */
	List<Integer> costResList;
	
	/**
	 * 需要消耗的资源对应的数量
	 * */
	HashMap<Integer, Integer> costResValue;
	
	//====================methods====================
	
	public int getID() {
		return id;
	}

	/**
	 * 构造方法
	 * @param resID 消耗资源的id
	 * @param costResValue 被消耗的资源与对应的值
	 * */
	public Cost(int resID, HashMap<Integer, Integer> costResValue) {
		this.id = resID;
		this.costResList = new ArrayList<Integer>();
		this.costResValue = new HashMap<Integer, Integer>();
		this.costResList.addAll(costResValue.keySet());
		this.costResValue.putAll(costResValue);
	}
	
	/**
	 * 转换为字符串
	 * @return 描述性字符串
	 * */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("资源");
		buffer.append(this.id);
		if (costResList != null && costResList.size() > 0) {
			buffer.append("需要:[");
			for (int i = 0; i < costResList.size(); i++) {
				
			}
			buffer.append("]");
		} else {
			buffer.append("无消耗");
		}
		return buffer.toString();
	}
}
