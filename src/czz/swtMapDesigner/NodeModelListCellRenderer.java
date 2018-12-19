package czz.swtMapDesigner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class NodeModelListCellRenderer extends JLabel implements ListCellRenderer<NodeTemplate> {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 2248763976485656621L;

	//====================methods====================
	
	@Override
	public Component getListCellRendererComponent(JList<? extends NodeTemplate> list, NodeTemplate value, int index, 
			boolean isSelected,  boolean cellHasFocus) {
		String text="<html>id: "+value.id + "<br/>name: " + value.name+ " <html/>";
		this.setText(text);//设置JLable的文字
		if (value.icon != null) {
			Image img = value.icon.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT); 	
			this.setIcon(new ImageIcon(img));//设置JLable的图片
		}
		this.setIconTextGap(8);
		if (isSelected){
	    	this.setBackground(Color.BLUE);
	    	this.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
		}else{
			this.setBackground(Color.WHITE);
			this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		}     
		return this;
	}

}
