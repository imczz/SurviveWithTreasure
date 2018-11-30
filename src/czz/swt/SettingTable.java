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
	private List<Res> resList;
	
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
		labels = new HashMap<String, List<Label> >();
		states = new HashMap<String, List<State> >();
		labels.put("global", new ArrayList<Label>());
		labels.put("node", new ArrayList<Label>());
		labels.put("group", new ArrayList<Label>());
		states.put("global", new ArrayList<State>());
		states.put("node", new ArrayList<State>());
		states.put("group", new ArrayList<State>());
	}
	
	public List<Res> getResList() {
		return resList;
	}

	public void setResList(List<Res> resList) {
		this.resList = resList;
	}

	
}
