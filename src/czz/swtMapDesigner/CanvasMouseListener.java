package czz.swtMapDesigner;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * 鼠标事件监听器
 * @author CZZ
 * */
public class CanvasMouseListener implements MouseMotionListener, MouseListener {

	/**
	 * 鼠标点击事件源
	 * */
	private ImageCanvas parent;

	//====================methods====================
	
	public CanvasMouseListener(ImageCanvas parent){
		this.parent = parent;
	}
	
	public void setParent(ImageCanvas parent){
		this.parent = parent;
	}
	
	public ImageCanvas getParent() {
		return parent;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		/*
		System.out.print("鼠标点击----" + "\t");
		if (e.getClickCount() == 1) {
			System.out.println("单击！");
		} else if (e.getClickCount() == 2) {
			System.out.println("双击！");
		} else if (e.getClickCount() == 3) {
			System.out.println("三连击！！");
		} else {
			System.out.println(e.getClickCount() + "连击！！");
		}*/
		this.parent.mouseClicked(e);
	}
 
	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("鼠标按下（" + e.getX() + "，" + e.getY() + "）");
	}
 
	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println("鼠标松开（" + e.getX() + "，" + e.getY() + "）");
	}
 
	@Override
	public void mouseEntered(MouseEvent e) {
		//System.out.println("鼠标已经进入窗体");
	}
 
	@Override
	public void mouseExited(MouseEvent e) {
		//System.out.println("鼠标已经移出窗体");
	}
 
	@Override
	public void mouseDragged(MouseEvent e) {
		//String string = "鼠标拖动到位置：（" + e.getX() + "，" + e.getY() + "）";
		//System.out.println(string);
	}
 
	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println();
	}
	
}
