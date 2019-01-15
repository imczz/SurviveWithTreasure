package czz.canvas.logic;

import czz.canvas.screen.Point2D;

/**
 * 逻辑平面上的点
 * @author CZZ
 * */
public class PointLogic {

	/**
	 * 横坐标
	 * */
	private double x;
	
	/**
	 * 纵坐标
	 * */
	private double y;
	
	/**
	 * 对应的屏幕上的点
	 * */
	private Point2D p2d;

	//====================methods====================
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Point2D getP2d() {
		return p2d;
	}

	public void setP2d(Point2D p2d) {
		this.p2d = p2d;
	}
	
	/**
	 * 构造方法
	 * */
	public PointLogic() {
		this.x = 0;
		this.y = 0;
		this.p2d = null;
	}
	
	/**
	 * 构造方法2
	 * */
	public PointLogic(double x, double y) {
		this.x = x;
		this.y = y;
		this.p2d = null;
	}
	
	/**
	 * 两个点是否相同
	 * @param p 另一个点
	 * @return true相同;false不同
	 * */
	public boolean equals(PointLogic p) {
		boolean ret = false;
		if (this.x == p.x && this.y == p.y) ret = true;				//两点重合
		return ret;
	}
	
}
