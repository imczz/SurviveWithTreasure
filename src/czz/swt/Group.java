package czz.swt;

import java.util.ArrayList;
import java.util.List;

/**
 * 队伍是参加这个游戏的基本单位
 * @author CZZ
 * */
public class Group extends EntityWithLabelAndState{
	
	/**
	 * 游戏引擎的引用
	 * */
	private Engine engine;
	
	/**
	 * 存储空间（背包，仓库等）
	 * */
	protected Storage storage;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * @param id 节点的id
	 * */
	public Group(Engine engine, int id) {
		super(id);
		this.storage = new Storage();
	}
	
	/**
	 * 构造方法2
	 * @param id 节点的id
	 * @param name 节点的名称
	 * */
	public Group(Engine engine, int id, String name) {
		super(id, name, null);
		this.storage = new Storage();
	}
	
}
