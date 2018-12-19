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
	public NodeTemplate(int id, String name, ImageIcon icon){
		this.id = id;
		this.name = name;
		this.icon = icon;
	}
	
}
