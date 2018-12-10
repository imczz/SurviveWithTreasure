package czz.swt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源背包，记录资源与对应的数量
 * @author CZZ
 * */
public class ResBag {

	/**
	 * 资源的id与资源背包的定义
	 * */
	private ConcurrentHashMap<Integer, ResDefine> idDefine;
	
	/**
	 * 资源对应的数量
	 * */
	private ConcurrentHashMap<Integer, Integer> resCount;
	
	/**
	 * 一个资源背包，key为资源id，value为<资源，数量>对
	 * */
	protected ConcurrentHashMap<ResDefine, List<ResPackage>> bag;
	
	/**
	 * TODO:资源增减记录
	 * */
	//protected Logger log;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public ResBag() {
		this.idDefine = new ConcurrentHashMap<Integer, ResDefine>();
		this.resCount = new ConcurrentHashMap<Integer, Integer>();
		this.bag = new ConcurrentHashMap<ResDefine, List<ResPackage>>();
	}
	
	/**
	 * 复制（拷贝）构造方法
	 * */
	public ResBag(ResBag resBag) {
		this.bag = new ConcurrentHashMap<ResDefine, List<ResPackage>>();
		Iterator<Entry<ResDefine, List<ResPackage>>> iter = resBag.bag.entrySet().iterator();		//待并入数据迭代器
		Entry<ResDefine, List<ResPackage>> entry = null;
		ArrayList<ResPackage> copyResList = null;
		ArrayList<ResPackage> resList = null;
		while (iter.hasNext()) {
			entry = iter.next();
			copyResList = new ArrayList<ResPackage>();
			resList = (ArrayList<ResPackage>) entry.getValue();
			for (int i = 0, length = resList.size(); i < length; i++) {
				copyResList.add(new ResPackage(resList.get(i)));
			}
			this.bag.put(entry.getKey(), copyResList);
		}
		this.idDefine = new ConcurrentHashMap<Integer, ResDefine>(resBag.idDefine);
		this.resCount = new ConcurrentHashMap<Integer, Integer>(resBag.resCount);
	}
	
	/**
	 * 得到背包中资源的种类数
	 * @return 背包中资源的种类数
	 * */
	public int typeCount() {
		return bag.size();
	}
	
	/**
	 * 返回内部的背包
	 * @return 返回用于内部存储的背包
	 * */
	public ConcurrentHashMap<ResDefine, List<ResPackage>> getBag() {
		return this.bag;
	}
	
	/**
	 * 清空这个背包
	 * */
	public void clear() {
		Iterator<Entry<ResDefine, List<ResPackage>>> iter = this.bag.entrySet().iterator();
		Entry<ResDefine, List<ResPackage>> entry = null;
		while(iter.hasNext()) {						//清空每个序列
			entry = iter.next();
			entry.getValue().clear();
		}
		this.bag.clear();
	}
	
	/**
	 * 是否含有某种资源
	 * @param resDefine 资源定义
	 * @return true含有资源;false没有这种资源
	 * */
	public boolean hasRes(ResDefine resDefine) {
		boolean ret = false;
		if (this.bag.containsKey(resDefine)) {
			Integer count = this.resCount.get(resDefine.id);
			if (count != null && count > 0) ret = true;
		}
		return ret;
	}
	
	/**
	 * 是否含有某id的资源
	 * @param resID 资源的id
	 * */
	public boolean hasRes(int resID) {
		boolean ret = false;
		ResDefine resDefine = this.idDefine.get(resID);				//查找资源定义
		if (resDefine != null) ret = hasRes(resDefine);
		return ret;
	}
	
	/**
	 * 获取资源的数量
	 * @param resDefine 资源定义
	 * @return 资源数量
	 * */
	public int getResCount(ResDefine resDefine) {
		int ret = 0;
		if (resDefine != null && hasRes(resDefine)) {
			ret = this.resCount.get(resDefine.id);
		}
		return ret;
	}
	
	/**
	 * 获取某id资源的数量
	 * @param resID 资源id
	 * @return 资源数量
	 * */
	public int getResCount(int resID) {
		int ret = 0;
		if (hasRes(resID)) {
			ret = this.resCount.get(resID);
		}
		return ret;
	}
	
	/**
	 * 添加资源组
	 * @param resPackage 资源组
	 * @return 数量是否发生变化。true添加成功;false添加失败
	 * */
	public boolean addRes(ResPackage resPackage) {
		boolean ret = false;
		if (resPackage != null && resPackage.getCount() > 0) {
			ResDefine resDefine = resPackage.getResDefine();			//资源定义
			int id = resDefine.id;										//资源定义的id
			List<ResPackage> resList = this.bag.get(resDefine);
			if (resList != null) {
				boolean hasSameRes = false;
				Integer count = 0;			//旧的总数
				for (int i = 0, length = resList.size(); i <length; i++) {
					if (resList.get(i).isSameRes(resPackage)) {				//资源相同
						resList.get(i).addPackage(resPackage);		//合并资源组
						count = this.resCount.get(id);				//旧的总数
						this.resCount.put(id, count + resPackage.getCount());		//新的总数
						hasSameRes = true;
						break;
					}
				}
				if (!hasSameRes) {
					resList.add(new ResPackage(resPackage));
					this.resCount.put(id, resPackage.getCount());		//新的总数
				}
			} else {
				resList = new ArrayList<ResPackage>();
				resList.add(new ResPackage(resPackage));
				this.bag.put(resDefine, resList);						//新的类型
				this.idDefine.put(resDefine.id, resDefine);				//id与定义对应
				this.resCount.put(id, resPackage.getCount());		//新的总数
			}
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 根据资源定义添加资源
	 * @param ResDefine 资源定义
	 * @param count 资源增加的数量(count > 0)
	 * @return 数量是否发生变化。true添加成功;false添加失败
	 * */
	public boolean addRes(ResDefine resDefine, int count) {
		boolean ret = false;
		if (resDefine != null && count > 0) {
			ResPackage resPackage = new ResPackage(resDefine, count);
			ret = this.addRes(resPackage);
		}
		return ret;
	}
	
	/**
	 * 添加资源
	 * @param Res 资源
	 * @param count 资源增加的数量(count > 0)
	 * @return 数量是否发生变化。true添加成功;false添加失败
	 * */
	public boolean addRes(Res res, int count) {
		boolean ret = false;
		if (res != null && res.getResDefine() != null && count > 0) {
			ResPackage resPackage = new ResPackage(res, count);
			ret = this.addRes(resPackage);
		}
		return ret;
	}
	
	/**
	 * 减少资源
	 * @param resId 资源的id
	 * @param value 资源增加的数量(value > 0)
	 * @return 数量是否发生变化。true减少成功;false减少失败
	 * */
	public boolean reduceRes(int resId, int value) {
		boolean ret = false;
		//TODO
		return ret;
	}
	
	/**
	 * 批量修改背包内容，不是覆盖，而是合并，要求合并之后数量value>=0
	 * @param oneBag <id, number>构成的序列，number可以为负，但是合并之后非负
	 * @return true合并成功;false合并失败，无更改
	 * */
	public boolean batch(HashMap<ResDefine, List<ResPackage>> oneBag) {
		boolean ret = true;
		//TODO
		return ret;
	}
	
	/**
	 * oneBag不为空且oneBag的value全为正值，则背包替换为oneBag，
	 * @param oneBag <id, number>构成的序列，number>0，为0则不插入
	 * @return true成功;false失败（比如oneBag的value含有负值）
	 * */
	public boolean reload(HashMap<Integer, Pair<ResDefine, Integer>> oneBag) {
		boolean ret = true;
		//TODO
		return ret;
	}
	
}
