package czz.swt;

import java.util.HashMap;

/**
 * 资源需要占用的资源
 * */
public class Demand extends OfferAndDemand{
	
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public Demand() {
		super();
	}
	
	/**
	 * 构造方法2
	 * @param res 资源的定义
	 * */
	public Demand(ResDefine res) {
		super(res);
	}
	
	/**
	 * 构造方法3
	 * @param res 资源的定义
	 * @param resValues 占用的资源与对应的数量
	 * */
	public Demand(ResDefine res, HashMap<ResDefine, Integer> resValues) {
		super(res, resValues);
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
