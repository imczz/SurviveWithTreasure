package czz.swtMapDesigner;

import javax.swing.ImageIcon;

public class NodeTemplate {

	int id;
	
	String name;
	
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
