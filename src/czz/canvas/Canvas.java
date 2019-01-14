package czz.canvas;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import czz.swt.PlaceNode;
import czz.swtMapDesigner.EdgeLine;
import czz.swtMapDesigner.NodePoint;

/**
 * 画布
 * @author CZZ
 * */
public class Canvas {

	/**
	 * 画布工具状态枚举类型
	 * */
	public static enum ToolState {Select, Point, Line};
	
	/**
	 * 节点列表
	 * */
	protected HashMap<Integer, NodePoint<PlaceNode>> nodeMap;
	
	/**
	 * 被选中的节点列表
	 * */
	protected HashSet<Integer> selectNode;
	
	/**
	 * 边列表
	 * */
	protected HashMap<Integer, ArrayList<EdgeLine<Integer>>> edgeList;
	
	/**
	 * 编号递增
	 * */
	protected int index;
	
	/**
	 * 画布工具状态
	 * */
	protected ToolState toolState;
	
	//====================methods====================
	
	public ToolState getToolState() {
		return this.toolState;
	}

	public void setToolState(ToolState toolState) {
		this.toolState = toolState;
	}
	
	/**
	 * 构造方法
	 * */
	public Canvas() {
		this.index = 0;
		this.nodeMap = new HashMap<Integer, NodePoint<PlaceNode>>();
		this.selectNode = new HashSet<Integer>();
		this.toolState = ToolState.Select;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * 设置index的值
	 * @param index 待设置的index
	 * @return true设置成功;false设置失败(index已被占用)
	 * */
	public boolean setIndex(int index) {
		boolean ret = false;
		if (!this.nodeMap.containsKey(index)) {			//index不能设置为已经存在的点的index
			this.index = index;
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 鼠标点击事件
	 * */
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Integer nearest = getNearestPoint(x, y);
		if (nearest == null) {		//周围没有点
			if (this.toolState == ToolState.Point) {
				//graph.addNode(index, "" + index, index);
				NodePoint<PlaceNode> newNode = new NodePoint<PlaceNode>(null, null, e.getX(), e.getY(), index);
				nodeMap.put(index, newNode);
				this.selectNode.clear();
				this.selectNode.add(index);
				index++;
				while (this.nodeMap.containsKey(index)) {			//此index已经被占用
					index++;										//尝试下一个
				}
			} else if (this.toolState == ToolState.Line) {
				
			}
		} else {					//鼠标点击了一个点
			//NodePoint<PlaceNode> nodePoint = this.nodeMap.get(nearest);
			if (this.toolState == ToolState.Select || this.toolState == ToolState.Point) {
				if (e.isControlDown()) {
					if (this.selectNode.contains(nearest)) {		//此点被选择
						this.selectNode.remove(nearest);
					} else {
						this.selectNode.add(nearest);
					}
				} else {
					if (this.selectNode.contains(nearest)) {
						if (this.selectNode.size() == 1) {	//点击已经被选择且仅被选择的点
							this.selectNode.remove(nearest);		//取消选择
						} else {							//多选状态
							this.selectNode.clear();
							this.selectNode.add(nearest);	//仅选定这个点而取消其他点的选择
						}
					} else {
						this.selectNode.clear();
						this.selectNode.add(nearest);
					}
					
				}
			} else if (this.toolState == ToolState.Line) {
				if (this.selectNode.size() == 0) {				//当前没有点被选择
					this.selectNode.add(nearest);					//选择被点击的点
				} else if (this.selectNode.size() == 1) {			//当前有一个点被选择
					if (this.selectNode.contains(nearest)) {			//点击被选中的点
						this.selectNode.remove(nearest);					//取消选择
					} else {											//点击另外的点
						
					}
				} else if (this.selectNode.size() == 2) {
					if (this.selectNode.contains(nearest)) {	//点击被选中的两个节点中的一个
						
					}
				}
			}
		}
		//this.repaint();
	}
	
	/**
	 * 查找周围的点
	 * @param x 鼠标x坐标
	 * @param y 鼠标y坐标
	 * @return 可以触发的点与对应的距离
	 * */
	public HashMap<Integer, Double> getSurroundingPoint(int x, int y) {
		HashMap<Integer, Double> ret = null;
		Iterator<Entry<Integer, NodePoint<PlaceNode>>> iter = nodeMap.entrySet().iterator();
		Entry<Integer, NodePoint<PlaceNode>> entry = null;
		if (iter.hasNext()) {
			double distance = 0;
			ret = new HashMap<Integer, Double>();
			while(iter.hasNext()) {
				entry = iter.next();
				distance = entry.getValue().distanceTo(x, y);
				if (distance < entry.getValue().getFocusRadius()) ret.put(entry.getKey(), distance);
			}
		}
		return ret;
	}
	
	/**
	 * 获取一个最近的点
	 * @param x 鼠标x坐标
	 * @param y 鼠标y坐标
	 * @return 鼠标点击触发的最近的点的编号
	 * */
	public Integer getNearestPoint(int x, int y) {
		Integer ret = null;
		Iterator<Entry<Integer, NodePoint<PlaceNode>>> iter = nodeMap.entrySet().iterator();
		Entry<Integer, NodePoint<PlaceNode>> entry = null;
		NodePoint<PlaceNode> node = null;
		double minDistance = -1;			//最短距离
		double distance = 0;
		while(iter.hasNext()) {
			entry = iter.next();
			node = entry.getValue();
			distance = node.distanceTo(x, y);
			if (distance < node.getFocusRadius()) {
				if (minDistance == -1) {
					minDistance = distance;
					ret = entry.getKey();
				} else if (distance < minDistance){
					minDistance = distance;
					ret = entry.getKey();
				}
			}
		}
		return ret;
	}
	
}
