package czz.swtMapDesigner;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JLabel;

import czz.swt.PlaceNode;

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
	private HashMap<Integer, NodePoint<PlaceNode>> nodeMap;
	
	private int index;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public ImageCanvas() {
		index = 0;
		nodeMap = new HashMap<Integer, NodePoint<PlaceNode>>();
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
		Iterator<Entry<Integer, NodePoint<PlaceNode>>>  iter = nodeMap.entrySet().iterator();
		Entry<Integer, NodePoint<PlaceNode>> entry = null;
		double px, py;
		double r = 4;
		NodePoint<PlaceNode> placeNode = null;
		Color backgroundColor = null;
		Color borderColor = null;
		Font font = null;
		while (iter.hasNext()) {
			entry = iter.next();
			placeNode = entry.getValue();
			px = placeNode.getX();
			py = placeNode.getY();
			r = placeNode.getR();
			if (px < 0 || py < 0 || r < 0) continue;
			Ellipse2D ellipse=new Ellipse2D.Double(px - r, py - r, 2 * r, 2 * r);
			backgroundColor = placeNode.getBackgroundColor();
			if (backgroundColor != null) {
				g2.setColor(backgroundColor);
				g2.fill(ellipse);
			}
			font = placeNode.getIndexFont();
			if (font != null) {
				g2.setFont(font);
				g2.drawString(Integer.valueOf(placeNode.getIndex()).toString(), (int)px, (int)py);
			}
			borderColor = placeNode.getBorderColor();
			if (borderColor != null) {
				Stroke s = new BasicStroke(placeNode.getBorderWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
				g2.setStroke(s); 
				g2.setColor(borderColor);
				g2.draw(ellipse);
			}
		}
	}
	
	/**
	 * 鼠标点击事件
	 * */
	public void mouseClicked(MouseEvent e) {
		NodePoint<PlaceNode> newNode = new NodePoint<PlaceNode>(null, null, e.getX(), e.getY(), index);
		newNode.setIndexFont(new Font("宋体", Font.BOLD, 12));
		nodeMap.put(index, newNode);
		index++;
		this.repaint();
	}
	
}
