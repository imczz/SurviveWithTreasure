package czz.canvas.screen;

import java.awt.Color;

/**
 * 平面上的直线
 * @author CZZ
 * */
public class Line2D {

	/**
	 * 节点1
	 * */
	private Point2D p1;
	
	/**
	 * 节点2
	 * */
	private Point2D p2;
	
	/**
	 * 线条宽度
	 * */
	private int lineWidth;
	
	/**
	 * 线条颜色
	 * */
	private Color color;
	
	/**
	 * 被选中时的线条颜色
	 * */
	private Color selectColor;

	//====================methods====================
	
	public Point2D getP1() {
		return this.p1;
	}

	public void setP1(Point2D p1) {
		this.p1 = p1;
	}

	public Point2D getP2() {
		return this.p2;
	}

	public void setP2(Point2D p2) {
		this.p2 = p2;
	}

	public int getLineWidth() {
		return this.lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getSelectColor() {
		return selectColor;
	}

	public void setSelectColor(Color selectColor) {
		this.selectColor = selectColor;
	}
	
	/**
	 * 构造方法
	 * */
	public Line2D(Point2D p1, Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.lineWidth = 4;
		this.color = Color.BLACK;
		this.selectColor = Color.GRAY;
	}
	
	/**
	 * 构造方法2
	 * */
	public Line2D(Point2D p1, Point2D p2, int lineWidth, Color color, Color selectColor) {
		this.p1 = p1;
		this.p2 = p2;
		this.lineWidth = lineWidth;
		this.color = color;
		this.selectColor = selectColor;
	}
}
