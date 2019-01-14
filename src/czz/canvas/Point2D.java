package czz.canvas;

import java.awt.Color;
import java.awt.Font;

/**
 * 平面上的点
 * @author CZZ
 * */
public class Point2D {

	/**
	 * 圆心横坐标，列|
	 * */
	protected int x;
	
	/**
	 * 圆心纵坐标，行——
	 * */
	protected int y;
	
	/**
	 * 号码，用来显示在中间
	 * */
	protected int index;
	
	/**
	 * 号码的字体，空代表不显示
	 * */
	
	protected Font indexFont;
	/**
	 * 响应点击半径
	 * */
	protected int focusRadius;
	
	/**
	 *半径，实际显示时还会加上边沿画刷的一半
	 * */
	protected int r;
	
	/**
	 * 边沿画刷宽度
	 * */
	protected int borderWidth;
	
	/**
	 * 边沿颜色，空代表没有边沿，但是边沿与背景不能同时为空
	 * */
	protected Color borderColor;
	
	/**
	 * 背景色，空代表没有背景，但是背景与边沿不能同时为空
	 * */
	protected Color backgroundColor;
	
	/**
	 * 文字颜色
	 * */
	private Color fontColor;
	
	/**
	 * 选中状态的边沿颜色，空代表没有边沿，但是边沿与背景不能同时为空
	 * */
	protected Color selectBorderColor;
	
	/**
	 * 选中状态的背景色，空代表没有背景，但是背景与边沿不能同时为空
	 * */
	protected Color selectBbackgroundColor;
	
	/**
	 * 选中状态的文字颜色
	 * */
	private Color selectFontColor;

	//====================methods====================
	
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
	
	public int getFocusRadius() {
		return this.focusRadius;
	}

	public void setFocusRadius(int focusRadius) {
		this.focusRadius = focusRadius;
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

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public Color getSelectBorderColor() {
		return selectBorderColor;
	}

	public void setSelectBorderColor(Color selectBorderColor) {
		this.selectBorderColor = selectBorderColor;
	}

	public Color getSelectBbackgroundColor() {
		return selectBbackgroundColor;
	}

	public void setSelectBbackgroundColor(Color selectBbackgroundColor) {
		this.selectBbackgroundColor = selectBbackgroundColor;
	}

	public Color getSelectFontColor() {
		return selectFontColor;
	}

	public void setSelectFontColor(Color selectFontColor) {
		this.selectFontColor = selectFontColor;
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
	 * */
	public Point2D() {
		this.x = 0;
		this.y = 0;
		this.index = 0;
		this.indexFont = null;
		this.focusRadius = 9;
		this.r = 8;
		this.borderWidth = 2;
		this.borderColor = Color.BLACK;
		this.backgroundColor = null;
		this.fontColor = Color.BLACK;
		this.selectBorderColor = Color.GRAY;
		this.selectBbackgroundColor = null;
		this.selectFontColor = Color.GRAY;
	}
	
	/**
	 * 构造方法2
	 * @param x 圆心横坐标
	 * @param y 圆心纵坐标
	 * @param index 显示编号
	 * @param indexFont 显示的编号的字体
	 * @param focusRadius 相应单击的区域的半径
	 * @param r 背景半径
	 * @param borderWidth 描边线宽度
	 * @param borderColor 描边颜色
	 * @param backgroundColor 背景颜色
	 * @param fontColor 文字颜色
	 * @param selectBorderColor 被选中后的描边颜色
	 * @param selectBackgroundColor 被选中后的背景颜色
	 * @param selectFontColor 被选中后的文字颜色
	 * */
	public Point2D(int x, int y, int index) {
		this.x = x;
		this.y = y;
		this.index = index;
		this.indexFont = new Font("宋体", Font.BOLD, 12);
		this.focusRadius = 9;
		this.r = 8;
		this.borderWidth = 2;
		this.borderColor = Color.BLACK;
		this.backgroundColor = null;
		this.fontColor = Color.BLACK;
		this.selectBorderColor = Color.GRAY;
		this.selectBbackgroundColor = null;
		this.selectFontColor = Color.GRAY;
	}
	
	/**
	 * 构造方法3
	 * @param x 圆心横坐标
	 * @param y 圆心纵坐标
	 * @param index 显示编号
	 * @param indexFont 显示的编号的字体
	 * @param focusRadius 相应单击的区域的半径
	 * @param r 背景半径
	 * @param borderWidth 描边线宽度
	 * @param borderColor 描边颜色
	 * @param backgroundColor 背景颜色
	 * @param fontColor 文字颜色
	 * @param selectBorderColor 被选中后的描边颜色
	 * @param selectBackgroundColor 被选中后的背景颜色
	 * @param selectFontColor 被选中后的文字颜色
	 * */
	public Point2D(int x, int y, int index, Font indexFont, int focusRadius, int r, int borderWidth,
			Color borderColor, Color backgroundColor, Color fontColor, 
			Color selectBorderColor, Color selectBbackgroundColor, Color selectFontColor) {
		this.x = x;
		this.y = y;
		this.index = index;
		this.indexFont = indexFont;
		this.focusRadius = focusRadius;
		this.r = r;
		this.borderWidth = borderWidth;
		this.borderColor = borderColor;
		this.backgroundColor = backgroundColor;
		this.fontColor = fontColor;
		this.selectBorderColor = selectBorderColor;
		this.selectBbackgroundColor = selectBbackgroundColor;
		this.selectFontColor = selectFontColor;
	}
	
	/**
	 * 计算两个点之间的距离
	 * @param x1 第1个点的x
	 * @param y1 第1个点的y
	 * @param x2 第2个点的x
	 * @param y2 第2个点的y
	 * @return 两点距离
	 * */
	public static double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	/**
	 * 与另一个点的距离
	 * @param x 另一个点的x
	 * @param y 另一个点的y
	 * @return 与另一个点的距离
	 * */
	public double distanceTo(int x, int y) {
		return Point2D.distance(this.x, this.y, x, y);
	}
	
	/**
	 * 与另一个点的距离
	 * @param p 另一个点
	 * @return 与另一个点的距离
	 * */
	public double distanceTo(Point2D p) {
		return Point2D.distance(this.x, this.y, p.getX(), p.getY());
	}
	
	/**
	 * 操作点是否可以触发此点
	 * @param x 鼠标点的x
	 * @param y 鼠标点的y
	 * @return true可以触发;false不能触发
	 * */
	public boolean canFocus(int x, int y) {
		boolean ret = false;
		if (this.focusRadius > 0) {
			if (distanceTo(x, y) < this.focusRadius) ret = true;		//与圆心距离小于触发半径
		}
		return ret;
	}
}
