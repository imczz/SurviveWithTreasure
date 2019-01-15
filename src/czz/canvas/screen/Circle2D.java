package czz.canvas.screen;

import java.awt.Color;

import czz.canvas.logic.CircleLogic;

/**
 * 平面上的圆
 * @author CZZ
 * */
public class Circle2D {

	/**
	 * 圆心
	 * */
	private Point2D center;
	
	/**
	 * 半径
	 * */
	private int r;
	
	/**
	 * 颜色
	 * */
	private Color color;
	
	/**
	 * 被选中的颜色
	 * */
	private Color selectColor;

	/**
	 * 对应的逻辑坐标系中的圆
	 * */
	private CircleLogic logicCircle;
	
	//====================methods====================
	
	public Point2D getCenter() {
		return center;
	}

	public void setCenter(Point2D center) {
		this.center = center;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
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

	public CircleLogic getLogicCircle() {
		return logicCircle;
	}

	public void setLogicCircle(CircleLogic logicCircle) {
		this.logicCircle = logicCircle;
	}
	
}
