package czz.swtMapDesigner;

import javax.swing.ImageIcon;

/**
 * 节点模板，作为JList的元素
 * @author CZZ
 * */
public class NodeTemplate {

	/**
	 * 节点id
	 * */
	int id;
	
	/**
	 * 节点名
	 * */
	String name;
	
	/**
	 * 展示图标
	 * */
	ImageIcon icon;

	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public NodeTemplate(){
		this.id = 0;
		this.name = null;
		this.icon = null;
	}
	
	/**
	 * 构造方法2
	 * */
	public NodeTemplate(int id, String name, ImageIcon icon){
		this.id = id;
		this.name = name;
		if (icon != null) {
			this.icon = icon;
		} else {
			this.icon = null;
		}
	}
	
}
