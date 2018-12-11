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
	 * @param res 资源的定义
	 * */
	public Cost(ResDefine res) {
		super(res);
	}
	
	/**
	 * 构造方法2
	 * @param res 资源的定义
	 * @param resValues 消耗的资源与对应的数量
	 * */
	public Cost(ResDefine res, HashMap<ResDefine, Integer> resValues) {
		super(res, resValues);
	}
	
	@Override
	protected String functionalDescription() {
		return "花费";
	}

}
