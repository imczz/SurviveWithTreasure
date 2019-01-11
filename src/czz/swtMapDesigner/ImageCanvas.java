package czz.swtMapDesigner;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JLabel;

import czz.swt.PlaceNode;

/**
 * 绘图画布，在画布上可以描点连线
 * @author CZZ
 * */
public class ImageCanvas extends JLabel{

	/**
	 * 画布工具状态枚举类型
	 * */
	public static enum ToolState {Select, Point, Line};
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -854776518703564180L;

	/**
	 * 节点列表
	 * */
	private HashMap<Integer, NodePoint<PlaceNode>> nodeMap;
	
	/**
	 * 被选中的节点列表
	 * */
	private HashSet<Integer> selectNode;
	
	/**
	 * 编号递增
	 * */
	private int index;
	
	/**
	 * 画布工具状态
	 * */
	private ToolState toolState;
	
	//====================methods====================
	
	public ToolState getToolState() {
		return toolState;
	}

	public void setToolState(ToolState toolState) {
		this.toolState = toolState;
	}

	/**
	 * 构造方法
	 * */
	public ImageCanvas() {
		this.index = 0;
		this.nodeMap = new HashMap<Integer, NodePoint<PlaceNode>>();
		this.selectNode = new HashSet<Integer>();
		this.toolState = ToolState.Select;
		CanvasMouseListener mouseListener = new CanvasMouseListener(this);
		this.addMouseListener(mouseListener);
	}
	
	/**
	 * 绘图函数
	 * */
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g; 
		Iterator<Entry<Integer, NodePoint<PlaceNode>>> iter = nodeMap.entrySet().iterator();
		Entry<Integer, NodePoint<PlaceNode>> entry = null;
		double px, py;
		double r = 4;
		NodePoint<PlaceNode> placeNode = null;
		Color backgroundColor = null;
		Color borderColor = null;
		Color fontColor = null;
		Font font = null;
		while (iter.hasNext()) {
			entry = iter.next();
			placeNode = entry.getValue();
			px = placeNode.getX();
			py = placeNode.getY();
			r = placeNode.getR();
			if (px < 0 || py < 0 || r < 0) continue;
			Ellipse2D ellipse=new Ellipse2D.Double(px - r, py - r, 2 * r, 2 * r);
			font = placeNode.getIndexFont();
			if (this.selectNode.contains(placeNode.getIndex())) {
				backgroundColor = placeNode.getSelectBbackgroundColor();
				borderColor = placeNode.getSelectBorderColor();
				fontColor = placeNode.getSelectFontColor();
			} else {
				backgroundColor = placeNode.getBackgroundColor();
				borderColor = placeNode.getBorderColor();
				fontColor = placeNode.getFontColor();
			}
			if (backgroundColor != null) {				//先画背景
				g2.setColor(backgroundColor);
				g2.fill(ellipse);
			}
			if (font != null && fontColor != null) {	//再画文字
				g2.setFont(font);
				g2.setColor(fontColor);
				FontMetrics fm = this.getFontMetrics(font);
				String indexString = Integer.valueOf(placeNode.getIndex()).toString();
				int fontHeight = (int) (fm.getHeight() * 0.6);
				int fontWidth = fm.stringWidth(indexString);
				g2.drawString(indexString, ((int)px) - (fontWidth / 2), ((int)py) + (fontHeight / 2));
			}
			if (borderColor != null) {					//最后描边
				Stroke s = new BasicStroke(placeNode.getBorderWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
				g2.setStroke(s); 
				g2.setColor(borderColor);
				g2.draw(ellipse);
			}
		}
	}
	
	/**
	 * 鼠标点击事件
	 * */
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Integer nearest = getNearest(x, y);
		if (nearest == null) {		//周围没有点
			if (this.toolState == ToolState.Point) {
				NodePoint<PlaceNode> newNode = new NodePoint<PlaceNode>(null, null, e.getX(), e.getY(), index);
				nodeMap.put(index, newNode);
				this.selectNode.clear();
				this.selectNode.add(index);
				index++;
			} else if (this.toolState == ToolState.Line) {
				
			}
		} else {
			NodePoint<PlaceNode> node = this.nodeMap.get(nearest);
			if (this.toolState == ToolState.Select || this.toolState == ToolState.Point) {
				if (e.isControlDown()) {
					if (this.selectNode.contains(nearest)) {
						this.selectNode.remove(nearest);
					} else {
						this.selectNode.add(nearest);
					}
				} else {
					this.selectNode.clear();
					this.selectNode.add(nearest);
				}
			} else if (this.toolState == ToolState.Line) {
				
			}
		}
		this.repaint();
	}
	
	/**
	 * 查找周围的点
	 * @param x 鼠标x坐标
	 * @param y 鼠标y坐标
	 * @return 可以触发的点与对应的距离
	 * */
	public HashMap<Integer, Double> getSurrounding(int x, int y) {
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
	public Integer getNearest(int x, int y) {
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
