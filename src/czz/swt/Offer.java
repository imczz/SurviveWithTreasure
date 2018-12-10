package czz.swt;

import java.util.HashMap;

/**
 * 资源提供的资源
 * @author CZZ
 * */
public class Offer extends OfferAndDemand{
	
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * @param id 资源的id
	 * */
	public Offer(int resID) {
		super(resID);
	}
	
	/**
	 * 构造方法2
	 * @param id 资源的id
	 * @param resValues 提供的资源与对应的数量
	 * */
	public Offer(int resID, HashMap<Integer, Integer> resValues) {
		super(resID, resValues);
	}

	/**
	 * 复制（拷贝）构造方法
	 * @param offer 另一个资源提供
	 * */
	public Offer(Offer offer) {
		super(offer);
	}
	
	@Override
	protected String functionalDescription() {
		return "提供";
	}
}
