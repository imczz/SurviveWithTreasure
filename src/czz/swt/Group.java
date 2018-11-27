package czz.swt;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 队伍是参加这个游戏的基本单位
 * @author CZZ
 * */
public class Group {
	
	/**
	 * id号
	 * */
	private int id;
	
	/**
	 * 队伍名
	 * */
	private String name;

	
	/**
	 * 标签列表
	 * */
	private ConcurrentHashMap<Integer, Label> labels;
	
	/**
	 * 队伍状态列表
	 * */
	private ConcurrentHashMap<Integer, State> states;

	//====================methods====================
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConcurrentHashMap<Integer, Label> getLabels() {
		return labels;
	}

	public ConcurrentHashMap<Integer, State> getStates() {
		return states;
	}
	
	/**
	 * 构造方法
	 * @param id 节点的id
	 * */
	public Group(int id) {
		this.id = id;
		labels = new ConcurrentHashMap<Integer, Label>();
		states = new ConcurrentHashMap<Integer, State>();
	}
	
	/**
	 * 构造方法2
	 * @param id 节点的id
	 * @param name 节点的名称
	 * */
	public Group(int id, String name) {
		this.id = id;
		this.name = name;
		labels = new ConcurrentHashMap<Integer, Label>();
		states = new ConcurrentHashMap<Integer, State>();
	}
	
	/**
	 * 给节点增加一个标签
	 * @param label 待添加的标签
	 * @return true 添加成功;false 添加失败或者标签已存在
	 * */
	public boolean addLabel(Label label) {
		boolean ret = false;
		if (labels.get(label.getId()) == null) {
			labels.put(label.getId(), label);
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 移除指定的标签
	 * @param labelId 待移除的标签id
	 * @return true 移除成功;false 移除失败或者标签不存在
	 * */
	public boolean removeLabel(int labelId) {
		boolean ret = false;
		if (labels.get(labelId) != null) {
			labels.remove(labelId);
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 移除指定的标签
	 * @param label 待移除的标签
	 * @return true 移除成功;false 移除失败或者标签不存在
	 * */
	public boolean removeLabel(Label label) {
		return removeLabel(label.getId());
	}
	
	/**
	 * 查找标签
	 * @param labelId 查找的的标签的id
	 * @return 非空：查找到结果;null:没有结果
	 * */
	public Label getLabel(int labelId) {
		return labels.get(labelId);
	}
	
	/**
	 * 给节点增加一个状态
	 * @param state 待添加的状态
	 * @return true 添加成功;false 添加失败或者状态已存在
	 * */
	public boolean addState(State state) {
		boolean ret = false;
		if (states.get(state.getId()) == null) {
			states.put(state.getId(), state);
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 移除指定的状态
	 * @param stateId 待移除的状态的id
	 * @return true 移除成功;false 移除失败或者状态不存在
	 * */
	public boolean removeState(int stateId) {
		boolean ret = false;
		if (states.get(stateId) != null) {
			states.remove(stateId);
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 移除指定的状态
	 * @param state 待移除的状态
	 * @return true 移除成功;false 移除失败或者状态不存在
	 * */
	public boolean removeState(Label state) {
		return removeState(state.getId());
	}
	
	/**
	 * 查找状态
	 * @param stateId 查找的的状态的id
	 * @return 非空：查找到结果;null:没有结果
	 * */
	public State getState(int stateId) {
		return states.get(stateId);
	}
	
}
