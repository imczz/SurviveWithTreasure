package czz.swt;

import java.util.ArrayList;
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
			Integer count = 0;					//旧的总数
			count = this.resCount.get(id);				//旧的总数
			if (count == null) count = 0;
			if (resList != null) {
				boolean hasSameRes = false;
				for (int i = 0, length = resList.size(); i <length; i++) {
					if (resList.get(i).isSameRes(resPackage)) {				//资源相同
						resList.get(i).addPackage(resPackage);		//合并资源组
						this.resCount.put(id, count + resPackage.getCount());		//新的总数
						hasSameRes = true;
						break;
					}
				}
				if (!hasSameRes) {
					resList.add(new ResPackage(resPackage));
					this.resCount.put(id, count + resPackage.getCount());		//新的总数
				}
			} else {
				resList = new ArrayList<ResPackage>();
				resList.add(new ResPackage(resPackage));
				this.bag.put(resDefine, resList);						//新的类型
				this.idDefine.put(resDefine.id, resDefine);				//id与定义对应
				this.resCount.put(id, resPackage.getCount());			//新的总数
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
	 * 批量添加
	 * @param resList 资源组列表
	 * @return true成功;false失败(resList为空等)
	 * */
	public boolean batchAdd(List<ResPackage> resList) {
		boolean ret = false;
		if (resList != null && resList.size() > 0) {
			for (int i = 0, length = resList.size(); i < length; i++) {
				this.addRes(resList.get(i));
			}
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 减少资源
	 * @param res 资源
	 * @param count 资源减少的数量(count > 0)
	 * @return 数量是否发生变化。true减少成功;false减少失败
	 * */
	public boolean reduceRes(Res res, int count) {
		boolean ret = false;
		if (res != null && res.getResDefine() != null && count > 0) {
			int id = res.getResDefine().id;
			Integer totalNumber = this.resCount.get(id);			//总数需要足够减
			List<ResPackage> resList = this.bag.get(res.resDefine);
			if (totalNumber != null && totalNumber >= count && resList != null) {
				ResPackage resPackage = null;
				int i, length;
				for (i = 0, length = resList.size(); i < length; i++) {
					resPackage = resList.get(i);
					if (resPackage.isSameRes(res)) {
						if (resPackage.popRes(count) != null) {				//尝试删除count个资源
							//删除成功
							ret = true;
						}
						break;
					}
				}
				if (ret) {	//删除成功，检查列表
					if (resList.get(i).getCount() == 0) resList.remove(i);			//资源组为空
					if (resList.size() == 0) {						//列表为空，资源删除
						this.bag.remove(res.resDefine);
						this.idDefine.remove(id);
						this.resCount.remove(id);
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * 减少资源
	 * @param resPackage 资源组
	 * @return 数量是否发生变化。true减少成功;false减少失败
	 * */
	public boolean reduceRes(ResPackage resPackage) {
		boolean ret = false;
		if (resPackage != null) {
			ret = reduceRes(resPackage.res, resPackage.getCount());
		}
		return ret;
	}
	
	/**
	 * 减少资源，忽略相同定义的资源的区别，数量足够就会从后至前依次删除（为了省下删除后列表移动时间）
	 * @param resDefine 资源定义
	 * @param count 资源减少的数量(count > 0)
	 * @return 数量是否发生变化。true减少成功;false减少失败
	 * @see #reduceRes(Res, int)
	 * */
	public boolean reduceRes(ResDefine resDefine, int count) {
		boolean ret = false;
		if (resDefine != null && count > 0) {
			int id = resDefine.id;
			Integer totalNumber = this.resCount.get(id);			//总数需要足够减
			if (totalNumber != 0 && totalNumber >= count) {			//总数需要足够减
				if (totalNumber == count) {			//数量恰好
					//直接清空列表
					this.bag.remove(resDefine);
					this.idDefine.remove(id);
					this.resCount.remove(id);
					ret = true;
				} else {							//还有剩余
					List<ResPackage> resList = this.bag.get(resDefine);
					if (resList != null) {
						int i, length = resList.size();
						int sum = 0;
						ResPackage resPackage = null;
						for (i = length - 1; i >= 0; i--) {
							resPackage = resList.get(i);
							sum += resPackage.getCount();
							if (sum >= count) {			//数量够减
								break;
							}
							resList.remove(i);			//删除列表
						}
						resPackage = resList.get(i);
						resPackage.popRes(resPackage.getCount() + count - sum);
						if (resPackage.getCount() == 0) resList.remove(i);
						Integer oldCount = this.resCount.get(id);					//旧的总数
						this.resCount.put(id, oldCount - count);
						ret = true;
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * 减少资源，忽略相同定义的资源的区别，数量足够就会从后之前依次删除（为了省下删除后列表移动时间）
	 * @param resId 资源的id
	 * @param count 资源减少的数量(count > 0)
	 * @return 数量是否发生变化。true减少成功;false减少失败
	 * @see #reduceRes(Res, int)
	 * */
	public boolean reduceRes(int resId, int count) {
		boolean ret = false;
		ResDefine resDefine = this.idDefine.get(resId);
		if (resDefine != null && count > 0) {
			ret = reduceRes(resDefine, count);
		}
		return ret;
	}
	
	/**
	 * oneBag不为空且oneBag的value全为正值，则背包替换为oneBag，
	 * @param oneBag <ResDefine, List<ResPackage>>构成的序列
	 * @return true成功;false失败
	 * */
	public boolean reload(List<ResPackage> oneBag) {
		boolean ret = false;
		if (oneBag != null && oneBag.size() > 0) {
			this.bag.clear();
			this.idDefine.clear();
			this.resCount.clear();
			this.batchAdd(oneBag);
			ret = true;
		}
		return ret;
	}
	
}
