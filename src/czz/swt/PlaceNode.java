package czz.swt;

/**
 * 地图上的节点
 * @author CZZ
 * */
public class PlaceNode extends Entity{

	
	//====================methods====================
	
	/**
	 * 构造方法
	 * @param id 节点的id
	 * */
	public PlaceNode(int id) {
		super(id);
	}
	
	/**
	 * 构造方法2
	 * @param id 节点的id
	 * @param name 节点的名称
	 * */
	public PlaceNode(int id, String name) {
		super(id, name);
	}
	
}
