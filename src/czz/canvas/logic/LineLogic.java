package czz.canvas.logic;

/**
 * 逻辑平面上的直线
 * @author CZZ
 * */
public class LineLogic {

	/**
	 * 线的类型枚举，直线Straight，射线Ray，线段Segment
	 * @category STRAIGHT 直线
	 * @category RAY 射线
	 * @category SEGMENT 线段
	 * */
	public static enum LineType {
		STRAIGHT("直线"), RAY("射线"), SEGMENT("线段");
		
		private String name;   
	     
	    private LineType(String name) {  
	        this.name = name;    
	    }  
  
	    @Override  
	    public String toString() {  
	        return this.name;  
	    } 	
	};
	
	/**
	 *  线的类型，直线，射线，线段
	 * */
	protected LineType lineType;

	/**
	 * 线的生成方式枚举，两点式Point，垂线Perpendicular，角平分线AngleBisector，垂直平分线VerticalBisector, 圆的切线Tangent
	 * @category POINT 两点式
	 * @category PERPENDICULAR 垂线
	 * @category ANGLE_BISECTOR 角平分线（从一个角的顶点引出一条射线，把这个角分成两个完全相同的角，这条射线叫做这个角的角平分线。）
	 * @category VERTICAL_BISECTOR 垂直平分线
	 * @category TANGENT 切线
	 * */
	public static enum Creation {
		POINT("两点式"), PERPENDICULAR("垂线"), ANGLE_BISECTOR("角平分线"), VERTICAL_BISECTOR("垂直平分线"), TANGENT("切线");
		
		private String name;   
	     
	    private Creation(String name) {  
	        this.name = name;    
	    }  
  
	    @Override  
	    public String toString() {  
	        return this.name;  
	    } 	
	};
	
	/**
	 * 线的生成方式
	 * */
	protected Creation creation;
	
	/**
	 * 第一个点，比如射线的起点
	 * */
	protected PointLogic p1;
	
	/**
	 * 第二个点
	 * */
	protected PointLogic p2;
	
	/**
	 * 垂线构造方法，角平分线构造方法1，垂直平分线构造方法
	 * */
	protected LineLogic l1;
	
	/**
	 * 角平分线构造方法2
	 * */
	protected LineLogic l2;
	
	/**
	 * 切线构造方法
	 * */
	protected CircleLogic c;
	
	//====================methods====================
	
	public LineType getLineType() {
		return this.lineType;
	}

	public Creation getCreation() {
		return this.creation;
	}

	public PointLogic getP1() {
		return this.p1;
	}

	public void setP1(PointLogic p1) {
		this.p1 = p1;
	}

	public PointLogic getP2() {
		return this.p2;
	}

	public void setP2(PointLogic p2) {
		this.p2 = p2;
	}

	public LineLogic getL1() {
		return this.l1;
	}

	public void setL1(LineLogic l1) {
		this.l1 = l1;
	}

	public LineLogic getL2() {
		return this.l2;
	}

	public void setL2(LineLogic l2) {
		this.l2 = l2;
	}

	public CircleLogic getC() {
		return this.c;
	}

	public void setC(CircleLogic c) {
		this.c = c;
	}
	
	/**
	 * 构造方法（修改线的类型）
	 * @param 线型
	 * */
	private LineLogic(LineType lineType) {
		this.lineType = lineType;
	}
	
	/**
	 * 构造方法（使用两个点构造）
	 * */
	public LineLogic(LineType lineType, PointLogic p1, PointLogic p2) {
		this.creation = Creation.POINT;
		this.lineType = lineType;
		this.p1 = p1;
		this.p2 = p2;
	}
	
	
	/**
	 * 垂线构造方式（静态内部类）
	 * */
	public static class Perpendicular extends LineLogic{
		
		/**
		 * 构造方法
		 * @param l1 某一条线（直线，射线，线段）
		 * @param p1 线上线外的某一点
		 * */
		Perpendicular(LineLogic l1, PointLogic p1){
			super(LineType.STRAIGHT);						//垂线是直线
			this.creation = Creation.PERPENDICULAR;			//垂线构造方式
			this.l1 = l1;
			this.p1 = p1;
		}
	}
	
	/**
	 * 角平分线构造方式（静态内部类）
	 * */
	public static class AngleBisector extends LineLogic{
		
		/**
		 * 构造方法
		 * @param l1 角的一条边
		 * @param l2 角的另一条边
		 * */
		AngleBisector(LineLogic l1, LineLogic l2){
			super(LineType.RAY);							//角平分线是射线
			this.creation = Creation.ANGLE_BISECTOR;			//角平分线构造方式
			this.l1 = l1;
			this.l2 = l2;
		}
	}
	
	/**
	 * 垂直平分线构造方式（静态内部类）
	 * */
	public static class VerticalBisector extends LineLogic{
		
		/**
		 * 构造方法
		 * @param l1 一个线段
		 * */
		VerticalBisector(LineLogic l1){
			super(LineType.RAY);							//垂直平分线是直线
			this.creation = Creation.VERTICAL_BISECTOR;		//垂直平分线构造方式
			this.l1 = l1;
		}
	}
	
	/**
	 * 圆的切线构造方式（静态内部类）
	 * */
	public static class Tangent extends LineLogic{
		
		/**
		 * 构造方法
		 * @param c 切线所切的圆
		 * @param p1 圆上或者圆外一点p
		 * */
		Tangent(CircleLogic c, PointLogic p1){
			super(LineType.STRAIGHT);					//切线是直线
			this.creation = Creation.TANGENT;			//切线构造方式
			this.c = c;
			this.p1 = p1;
		}
	}
	
	public boolean isLegal() {
		boolean ret = false;
		if (this.creation == Creation.POINT) {					//两点式
			if (this.p1 != null && this.p2 != null) {				//点不为空
				if (this.p1.getX() != this.p2.getX() || this.p1.getY() != this.p2.getY()) {//两点确定一条直线
					ret = true;
				}
			}
		} else if (this.creation == Creation.PERPENDICULAR) {	//垂线
			if (this.l1 != null && this.p1 != null) {				//存在线和一个点
				if (l1.isLegal()) {										//线合法
					ret = true;	
				}
			}
		} else if (this.creation == Creation.ANGLE_BISECTOR) {	//角平分线
			if (this.l1 != null && this.l2 != null) {				//角的两边不为空
				if (l1.lineType == LineType.RAY && l1.lineType == l2.lineType && l1.p1 == l2.p1) {	//角的两边共用同一个端点
					//TODO 选择正确的角平分线构造方式
					//ret = true;
				}
			}
		} else if (this.creation == Creation.VERTICAL_BISECTOR) {	//垂直平分线
			if (this.l1 != null) {
				if (this.l1.isLegal() && this.l1.lineType == LineType.SEGMENT) {				//合理线段
					ret = true;
				}
			}
		} else if (this.creation == Creation.TANGENT) {				//圆的切线
			if (this.c != null && this.p1 != null) {					//存在
				
			}
		}
		return ret;
	}
	
}
