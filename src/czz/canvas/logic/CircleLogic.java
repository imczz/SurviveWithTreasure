package czz.canvas.logic;

import czz.canvas.screen.Circle2D;

/**
 * 逻辑平面上的圆
 * @author CZZ
 * */
public class CircleLogic {

	/**
	 * 圆心
	 * */
	private PointLogic center;
	
	/**
	 * 半径
	 * */
	private double r;
	
	/**
	 * 对应的屏幕上的圆
	 * */
	private Circle2D c2d;

	//====================methods====================
	
	public PointLogic getCenter() {
		return center;
	}

	public void setCenter(PointLogic center) {
		this.center = center;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public Circle2D getC2d() {
		return c2d;
	}

	public void setC2d(Circle2D c2d) {
		this.c2d = c2d;
	}
	
	/**
	 * 构造方法
	 * */
	CircleLogic(PointLogic center, double r) {
		this.center = center;
		this.r = r;
		this.c2d = null;
	}
	
}
