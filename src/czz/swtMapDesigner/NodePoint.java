package czz.swtMapDesigner;

import java.awt.Color;
import java.awt.Font;

import czz.graph.Node;

/**
 * 图上的节点所在，显示在图中是一个圆
 * @author CZZ
 * */
public class NodePoint<T> extends Point2D{

	/**
	 * 图的节点
	 * */
	private Node<T> node;
	
	/**
	 * 节点模板
	 * */
	private NodeTemplate template;
	
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

	/**
	 * 构造方法
	 * @param node 图中的点所对应的节点
	 * @param template 生成节点的模板
	 * */
	public NodePoint(Node<T> node, NodeTemplate template) {
		super();
		this.node = node;
		this.template = template;
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
		super(x, y, index);
		this.node = node;
		this.template = template;
	}

	/**
	 * 构造方法3
	 * @param node 图中的点所对应的节点
	 * @param template 生成节点的模板
	 * @param x 圆心横坐标
	 * @param y 圆心纵坐标
	 * @param index 显示编号
	 * @param indexFont 显示的编号的字体
	 * @param focusRadius 相应单击的区域的半径
	 * @param r 背景半径
	 * @param borderWidth 描边线宽度
	 * @param borderColor 描边颜色
	 * */
	public NodePoint(Node<T> node, NodeTemplate template, int x, int y, int index, Font indexFont, int focusRadius, int r, 
			int borderWidth, Color borderColor, Color backgroundColor, Color fontColor, Color selectBorderColor, 
			Color selectBbackgroundColor, Color selectFontColor) {
		super(x, y, index, indexFont, focusRadius, r, borderWidth, borderColor, backgroundColor, fontColor,
				selectBorderColor, selectBbackgroundColor, selectFontColor);
		this.node = node;
		this.template = template;
	}
	
}
