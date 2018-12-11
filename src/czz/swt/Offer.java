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
	 * */
	public Offer() {
		super();
	}
	
	/**
	 * 构造方法2
	 * @param res 资源的定义
	 * */
	public Offer(ResDefine res) {
		super(res);
	}
	
	/**
	 * 构造方法3
	 * @param res 资源的定义
	 * @param resValues 提供的资源与对应的数量
	 * */
	public Offer(ResDefine res, HashMap<ResDefine, Integer> resValues) {
		super(res, resValues);
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
