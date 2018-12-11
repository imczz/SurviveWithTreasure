package czz.swt;

/**
 * 资源组，记录完全相同的资源的数量
 * @author CZZ
 * */
public class ResPackage {

	/**
	 * 相同的资源
	 * */
	protected Res res;
	
	/**
	 * 资源的数量（value>0）
	 * */
	private int count;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public ResPackage(ResDefine resDefine, int value) {
		this.res = new Res(resDefine);
		this.count = value;
	}
	
	/**
	 * 构造方法2
	 * */
	public ResPackage(Res res, int value) {
		this.res = new Res(res);
		this.count = value;
	}
	
	/**
	 * 复制（拷贝）构造方法
	 * */
	public ResPackage(ResPackage resPackage) {
		this.res = new Res(resPackage.res);
		this.count = resPackage.count;
	}

	/**
	 * 获取资源定义
	 * @return 资源定义
	 * */
	public ResDefine getResDefine() {
		return this.res.getResDefine();
	}
	
	/**
	 * 获取资源的数量
	 * @return 资源的数量
	 * */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * 增加资源的数量
	 * @param count 装入的数量
	 * @return true装入成功；false装入失败（count<0等）
	 * */
	public boolean pushRes(int count) {
		boolean ret = false;
		if (count > 0) {
			ret = true;
			count+=count;
		}
		return ret;
	}
	
	/**
	 * 资源的数量增加1
	 * @return true装入成功；false装入失败（count<0等）
	 * */
	public boolean pushRes() {
		return pushRes(1);
	}
	
	/**
	 * 拿出资源
	 * @param count 要拿出的资源的数量
	 * @return 拿出的资源
	 * */
	public Res popRes(int count) {
		Res ret = null;
		if (count > 0 && this.count >= count) {
			count-=count;			//减少数量
			ret = new Res(this.res);
		}
		return ret;
	}
	
	/**
	 * 拿出一个资源
	 * @return 拿出的资源
	 * */
	public Res popRes() {
		return popRes(1);
	}
	
	/**
	 * 将资源相同的资源组加入本组
	 * @param resPackage 另一个资源组
	 * @return true相加成功;false失败(定义不同等)
	 * */
	public boolean addPackage(ResPackage resPackage) {
		boolean ret = false;
		if (this.isSameRes(resPackage)) ret = this.pushRes(resPackage.getCount());
		return ret;
	}
	
	/**
	 * 判断两个资源组是否相同
	 * @param resPackage 另一个资源组
	 * @return true相同;false不同
	 * */
	public boolean equals(ResPackage resPackage) {
		boolean ret = false;
		if (this.res.equals(resPackage.res) && this.count == resPackage.count) ret = true;
		return ret;
	}
	
	/**
	 * 判断两个资源组的资源是否相同
	 * @param resPackage 另一个资源组
	 * @return true相同;false不同
	 * */
	public boolean isSameRes(ResPackage resPackage) {
		boolean ret = false;
		if (this.res.equals(resPackage.res)) ret = true;
		return ret;
	}
	
	/**
	 * 判断某资源是否与资源组的资源相同
	 * @param res 某个资源
	 * @return true相同;false不同
	 * */
	public boolean isSameRes(Res res) {
		boolean ret = false;
		if (this.res.equals(res)) ret = true;
		return ret;
	}
	
	/**
	 * 判断两个资源的资源定义是否相同
	 * */
	public boolean isSameDefine(ResPackage resPackage) {
		boolean ret = false;
		if (this.res.resDefine.equals(resPackage.res.resDefine)) ret = true;
		return ret;
	}
	
}
