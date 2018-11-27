package czz.swt;

import java.util.Hashtable;

/**
 * 记录业务逻辑的引擎
 * @author CZZ
 * */
public class Engine {

	/**
	 * 游戏地图
	 * */
	private GameMap gameMap;
	
	/**
	 * 队伍列表
	 * */
	private Hashtable<Integer, Group> groups;
	
	/**
	 * 游戏设定表
	 * */
	private SettingTable settingTable;
	
	/**
	 * 游戏规则
	 * */
	private Rule rule;
}
