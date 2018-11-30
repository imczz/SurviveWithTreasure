package czz.swt;

import java.util.HashMap;

/**
 * 资源提供的资源
 * */
public class Offer extends OfferAndDemand{
	
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * @param id 资源的id
	 * @param resValues 提供的资源与对应的数量
	 * */
	public Offer(int resID, HashMap<Integer, Integer> resValues) {
		super(resID, resValues);
	}

	@Override
	protected String functionalDescription() {
		return "提供";
	}
}
