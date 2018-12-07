package czz.swt;

/**
 * 物品、资源
 * @author CZZ
 * */
public class Res{

	/**
	 * 资源定义
	 * */
	protected ResDefine resDefine;
	
	/**
	 * 剩余使用次数
	 * */
	private int useTime;
	
	//====================methods====================

	/**
	 * 构造方法（资源需要使用资源定义来构造）
	 * */
	public Res(ResDefine resDefine) {
		this.resDefine = resDefine;					//记录资源定义
		this.useTime = resDefine.getMaxUseTime();
	}
	
	/**
	 * 获取此资源的定义
	 * @return 资源定义
	 * */
	public ResDefine getResDefine() {
		return this.resDefine;
	}
	
	/**
	 * 获取资源定义的id
	 * @return 资源的id
	 * */
	public int getID() {
		return this.getResDefine().getId();
	}
	
	/**
	 * 判断资源是否用尽，且需要被删除
	 * @return 消耗品使用次数是否耗尽
	 * */
	public boolean outOfUse() {
		boolean ret = false;
		if (resDefine.isConsumable() && this.useTime == 0) ret = true;
		return ret;
	}
	
	/**
	 * 判断消耗品资源的使用次数，非消耗品无法使用
	 * @return 消耗品是否可以被使用
	 * */
	public boolean canUse() {
		boolean ret = false;
		if (resDefine.isConsumable() && this.useTime != 0) ret = true;
		return ret;
	}
	
	/**
	 * 使用此资源1次
	 * @return 是否使用成功并扣除使用次数
	 * */
	public boolean useRes() {
		boolean ret = false;
		if (this.canUse()) {
			if (this.useTime > 0) this.useTime--;			//消耗品
			ret = true;
		}
		return ret;
	}
}
