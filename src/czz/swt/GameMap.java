package czz.swt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import czz.graph.Graph;
import czz.graph.Node;

/**
 * 游戏地图
 * @author CZZ
 * */
public class GameMap {
	
	/**
	 * 游戏地图
	 * */
	private Graph<PlaceNode> gameMapGraph;

	/**
	 * 地图是否经过初始化
	 * */
	private boolean isInitialized;
	
	/**
	 * 起点的id
	 * */
	private Integer startPointId;
	
	/**
	 * 终点的id列表
	 * */
	private HashSet<Integer> endPointIds;
	
	//====================methods====================
	
	public Integer getStartPointId() {
		return startPointId;
	}

	/**
	 * 构造方法
	 * */
	public GameMap() {
		this.gameMapGraph = new Graph<PlaceNode>();
		this.gameMapGraph.toUndirected(false);
		this.gameMapGraph.toUnWeighted();
		isInitialized = false;
		this.endPointIds = new HashSet<Integer>();
		startPointId = null;
	}
	
	/**
	 * 初始化地图，只会成功初始化一次
	 * @param startID 地图起点id
	 * @param endIDs 地图终点id数组，可以与起点相同，可以重复，但是不可以包含不存在的id
	 * @return 初始化结果
	 * */
	public boolean initialize(int startID, int[] endIDs) {
		if (!this.isInitialized) {
			if (this.gameMapGraph.getNode(startID) != null) {
				boolean flag = true;
				for(int i = 0; i < endIDs.length; i++) {
					if (this.gameMapGraph.getNode(endIDs[i]) != null) {
						this.endPointIds.add(endIDs[i]);
					} else {		//数组中包含不存在的id，终止初始化
						flag = false;
						this.endPointIds.clear();
						break;
					}
				}
				if(flag) {
					this.startPointId = startID;
					this.isInitialized = true;
				}
			}
		}
		return this.isInitialized;
	}
	
	/**
	 * 是否经过初始化
	 * */
	public boolean isInitialized() {
		return this.isInitialized;
	}
	
	/**
	 * 地图是否为空
	 * @return true 地图为空;false 地图不为空
	 * */
	public boolean isEmpty() {
		return this.gameMapGraph.isEmpty();
	}
	
	/**
	 * @return 地图上的节点的数量
	 * */
	public int getNodeNumber() {
		return this.gameMapGraph.getNodeNumber();
	}
	
	/**
	 * 添加节点
	 * @param placeNode 地图上的节点
	 * @return true 添加成功;false 节点已存在
	 * */
	public boolean addNode(PlaceNode placeNode) {
		return this.gameMapGraph.addNode(placeNode.getId(), placeNode.getName(), placeNode);
	}
	
	/**
	 * 通过id查找节点
	 * @return 非空:id为id的节点；null:没有查询到结果
	 * */
	public PlaceNode getNode(int id) {
		PlaceNode ret = null;
		Node<PlaceNode> node = this.gameMapGraph.getNode(id);
		if (node != null) ret = node.getElement();
		return ret;
	}
	
	/**
	 * 移除节点
	 * @param id 节点的id号
	 * @return true 删除成功;false 节点并不存在
	 * */
	public boolean removeNode(int id) {
		boolean ret = false;
		if(this.gameMapGraph.removeNode(id) != -1) {		//节点存在
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 添加无向边(id1,id2)或者说是(id2,id1)
	 * @param id1 节点1的id
	 * @param id2 节点2的id
	 * @return true 添加成功;false 端点不存在或者边已经存在
	 * */
	public boolean addEdge(int id1, int id2) {
		return this.gameMapGraph.addEdge(id1, id2, 0);
	}
	
	/**
	 * 删除无向边(id1,id2)或者说是(id2,id1)
	 * @param id1 节点1的id
	 * @param id2 节点2的id
	 * @return true 删除成功;false 端点不存在或者边不存在
	 */
	public boolean removaEdge(int id1, int id2) {
		return this.gameMapGraph.removeEdge(id1, id2);
	}
	
	/**
	 * 获取可以到达的节点的id号
	 * */
	public Integer[] getNextStep(int id) {
		Integer[] ret = null;
		Node<PlaceNode> node = this.gameMapGraph.getNode(id);
		if (node != null) {
			ArrayList<Integer> nodeArray = new ArrayList<Integer>();
			HashMap<Integer, Node<PlaceNode>> nextNodeHashMap = node.getInNodeList();
			Iterator<Integer> iter = nextNodeHashMap.keySet().iterator();
			while (iter.hasNext()) {
				Integer key = iter.next();
				nodeArray.add(key);
			}
			ret = new Integer[nodeArray.size()];
			nodeArray.toArray(ret);
		}
		return ret;
	}
}
