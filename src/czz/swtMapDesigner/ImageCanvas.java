package czz.swtMapDesigner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JLabel;

/**
 * 绘图画布，在画布上可以描点连线
 * @author CZZ
 * */
public class ImageCanvas extends JLabel{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -854776518703564180L;

	/**
	 * 节点列表
	 * */
	private HashMap<Integer, Point> nodeMap;
	
	private int index;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public ImageCanvas() {
		index = 0;
		nodeMap = new HashMap<Integer, Point>();
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
		Graphics2D g2=(Graphics2D) g;
		g2.setColor(Color.GREEN); 
		Iterator<Entry<Integer, Point>> iter = nodeMap.entrySet().iterator();
		Entry<Integer, Point> entry = null;
		double px, py;
		double r = 4;
		while (iter.hasNext()) {
			entry = iter.next();
			px = entry.getValue().getX();
			py = entry.getValue().getY();
			if (px < 0 || py < 0) continue;
			Ellipse2D ellipse=new Ellipse2D.Double(px - r, py - r, 2 * r, 2 * r);  
			g2.draw(ellipse); 
			//g2.fill(ellipse);
		}
	}
	
	/**
	 * 鼠标点击事件
	 * */
	public void mouseClicked(MouseEvent e) {
		nodeMap.put(index, new Point(e.getX(), e.getY()));
		index++;
		this.repaint();
	}
	
}
