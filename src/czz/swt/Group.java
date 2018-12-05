package czz.swt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import czz.util.MyCompare;

/**
 * 队伍是参加这个游戏的基本单位
 * @author CZZ
 * */
public class Group extends EntityWithLabelAndState{
	
	/**
	 * 游戏引擎的引用
	 * */
	private Engine engine;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * @param id 节点的id
	 * */
	public Group(Engine engine, int id) {
		super(id);
	}
	
	/**
	 * 构造方法2
	 * @param id 节点的id
	 * @param name 节点的名称
	 * */
	public Group(Engine engine, int id, String name) {
		super(id, name);
	}
	
}
