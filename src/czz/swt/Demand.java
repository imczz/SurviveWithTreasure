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
	 * */
	public Demand(int resID) {
		super(resID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 构造方法2
	 * @param id 资源的id
	 * @param resValues 占用的资源与对应的数量
	 * */
	public Demand(int resID, HashMap<Integer, Integer> resValues) {
		super(resID, resValues);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 复制（拷贝）构造方法
	 * @param demand 另一个资源占用
	 * */
	public Demand(Demand demand) {
		super(demand);
	}
	
	@Override
	protected String functionalDescription() {
		return "占用";
	}
}
