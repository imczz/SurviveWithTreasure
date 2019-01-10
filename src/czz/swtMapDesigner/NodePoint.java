package czz.swtMapDesigner;

import java.awt.Color;
import java.awt.Font;

import czz.graph.Node;

/**
 * 图上的节点所在，显示在图中是一个圆
 * @author CZZ
 * */
public class NodePoint<T> {

	/**
	 * 图的节点
	 * */
	private Node<T> node;
	
	/**
	 * 节点模板
	 * */
	private NodeTemplate template;
	
	/**
	 * 圆心横坐标，行——
	 * */
	private int x;
	
	/**
	 * 圆心纵坐标，列|
	 * */
	private int y;
	
	/**
	 * 响应点击半径
	 * */
	private int forceRadius;
	
	/**
	 *半径，实际显示时还会加上边沿画刷的一半
	 * */
	private int r;
	
	/**
	 * 边沿画刷宽度
	 * */
	private int borderWidth;
	
	/**
	 * 边沿颜色，空代表没有边沿，但是边沿与背景不能同时为空
	 * */
	private Color borderColor;
	
	/**
	 * 背景色，空代表没有背景，但是背景与边沿不能同时为空
	 * */
	private Color backgroundColor;
	
	/**
	 * 号码，用来显示在中间
	 * */
	private int index;
	
	/**
	 * 号码的字体，空代表不显示
	 * */
	private Font indexFont;
	
	//====================methods====================
	
	public Node<T> getNode() {
		return node;
	}

	public void setNode(Node<T> node) {
		this.node = node;
	}

	public NodeTemplate getTemplate() {
		return template;
	}

	public void setTemplate(NodeTemplate template) {
		this.template = template;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getForceRadius() {
		return forceRadius;
	}

	public void setForceRadius(int forceRadius) {
		this.forceRadius = forceRadius;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Font getIndexFont() {
		return indexFont;
	}

	public void setIndexFont(Font indexFont) {
		this.indexFont = indexFont;
	}

	/**
	 * 构造方法
	 * @param node 图中的点所对应的节点
	 * @param template 生成节点的模板
	 * */
	public NodePoint(Node<T> node, NodeTemplate template) {
		this.node = node;
		this.template = template;
		this.x = 0;
		this.y = 0;
		this.index = 0;
		this.indexFont = null;
		this.forceRadius = 9;
		this.r = 8;
		this.borderWidth = 2;
		this.borderColor = Color.BLACK;
		this.backgroundColor = null;
	}
	
	/**
	 * 构造方法2
	 * @param node 图中的点所对应的节点
	 * @param template 生成节点的模板
	 * @param x 圆心横坐标
	 * @param y 圆心纵坐标
	 * @param index 显示编号
	 * */
	public NodePoint(Node<T> node, NodeTemplate template, int x, int y, int index) {
		this.node = node;
		this.template = template;
		this.x = x;
		this.y = y;
		this.index = index;
		this.indexFont = null;
		this.forceRadius = 9;
		this.r = 8;
		this.borderWidth = 2;
		this.borderColor = Color.BLACK;
		this.backgroundColor = null;
	}

	/**
	 * 构造方法3
	 * @param node 图中的点所对应的节点
	 * @param template 生成节点的模板
	 * @param x 圆心横坐标
	 * @param y 圆心纵坐标
	 * @param index 显示编号
	 * @param indexFont 显示的编号的字体
	 * @param forceRadius 相应单击的区域的半径
	 * @param r 背景半径
	 * @param borderWidth 描边线宽度
	 * @param borderColor 描边颜色
	 * */
	public NodePoint(Node<T> node, NodeTemplate template, int x, int y, int index, Font indexFont, int forceRadius, int r, int borderWidth,
			Color borderColor, Color backgroundColor) {
		this.node = node;
		this.template = template;
		this.x = x;
		this.y = y;
		this.index = index;
		this.indexFont = indexFont;
		this.forceRadius = forceRadius;
		this.r = r;
		this.borderWidth = borderWidth;
		this.borderColor = borderColor;
		this.backgroundColor = backgroundColor;
	}
	
}
