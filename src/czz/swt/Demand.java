package czz.swt;

import java.util.HashMap;

/**
 * 资源需要占用的资源
 * */
public class Demand extends OfferAndDemand{
	
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * @param id 资源的id
	 * @param resValues 占用的资源与对应的数量
	 * */
	public Demand(int resID, HashMap<Integer, Integer> resValues) {
		super(resID, resValues);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String functionalDescription() {
		return "占用";
	}
}
