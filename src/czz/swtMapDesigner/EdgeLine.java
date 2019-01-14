package czz.swtMapDesigner;

import java.awt.Color;

import czz.canvas.Line2D;
import czz.canvas.Point2D;
import czz.graph.Edge;

/**
 * 图的边表示为线段
 * @author CZZ
 * */
public class EdgeLine<T> extends Line2D{

	/**
	 * 线段对应的边
	 * */
	private Edge<T> edge;

	//====================methods====================
	
	public Edge<T> getEdge() {
		return edge;
	}

	public void setEdge(Edge<T> edge) {
		this.edge = edge;
	}
	
	/**
	 * 构造方法
	 * @param edge 线段对应的边
	 * @param p1 端点1
	 * @param p2 端点2
	 * */
	public EdgeLine(Edge<T> edge, Point2D p1, Point2D p2){
		super(p1, p2);
		this.edge = edge;
	}
	
	/**
	 * 构造方法2
	 * @param edge 线段对应的边
	 * @param p1 端点1
	 * @param p2 端点2
	 * @param lineWidth 线段宽度
	 * @param color 线段颜色
	 * @param selectColor 被选中时的线段颜色
	 * */
	public EdgeLine(Edge<T> edge, Point2D p1, Point2D p2, int lineWidth, Color color, Color selectColor){
		super(p1, p2, lineWidth, color, selectColor);
		this.edge = edge;
	}
	
}
