package czz.swt;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 带有标签和状态的实体
 * @author CZZ
 * */
public abstract class EntityWithLabelAndState extends Entity{

	/**
	 * 标签列表
	 * */
	protected ConcurrentHashMap<Integer, Label> labels;
	
	/**
	 * 状态列表
	 * */
	protected ConcurrentHashMap<Integer, State> states;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public EntityWithLabelAndState(int id) {
		super(id);
		labels = new ConcurrentHashMap<Integer, Label>();
		states = new ConcurrentHashMap<Integer, State>();
	}
	
	/**
	 * 构造方法2
	 * */
	public EntityWithLabelAndState(int id, String name) {
		super(id, name);
		labels = new ConcurrentHashMap<Integer, Label>();
		states = new ConcurrentHashMap<Integer, State>();
	}
	
	public ConcurrentHashMap<Integer, Label> getLabels() {
		return labels;
	}

	public ConcurrentHashMap<Integer, State> getStates() {
		return states;
	}
	
	/**
	 * 增加一个标签
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
	 * 移除指定id的标签
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
	 * 增加一个状态
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
	 * 移除指定id的状态
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
