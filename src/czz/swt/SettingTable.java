package czz.swt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * SWT系统设置表
 * @author CZZ
 * */
public class SettingTable {

	/**
	 * 资源定义表
	 * */
	private HashMap<Integer, ResDefine> resSet;
	
	/**
	 * 标签定义表
	 * */
	private HashMap<String, List<Label> > labels;
	
	/**
	 * 状态定义表
	 * */
	private HashMap<String, List<State> > states;
	
	//====================methods====================
	
	public SettingTable() {
		resSet = new HashMap<Integer, ResDefine>();
		labels = new HashMap<String, List<Label> >();
		states = new HashMap<String, List<State> >();
		labels.put("global", new ArrayList<Label>());
		labels.put("node", new ArrayList<Label>());
		labels.put("group", new ArrayList<Label>());
		states.put("global", new ArrayList<State>());
		states.put("node", new ArrayList<State>());
		states.put("group", new ArrayList<State>());
	}
	
	/**
	 * 资源是否存在
	 * @param id 带检验存在资源的id
	 * @return true存在;false不存在
	 * */
	public boolean hasRes(int id) {
		return this.resSet.containsKey(id);
	}
	
	/**
	 * 查找资源
	 * @param id 带查找资源的id
	 * @return 查询结果，为空则不存在
	 * */
	public ResDefine getRes(int id) {
		return this.resSet.get(id);
	}
	
}
