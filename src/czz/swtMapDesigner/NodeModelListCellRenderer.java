package czz.swtMapDesigner;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

public class NodeModelListCellRenderer extends DefaultListCellRenderer {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 2248763976485656621L;

	//====================methods====================
	
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
			boolean isSelected,  boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (value instanceof NodeTemplate) {
			NodeTemplate node = (NodeTemplate)value;
			String text="<html>id: "+node.id + "<br/>name: " + node.name+ " <html/>";
			this.setText(text);//设置JLable的文字
			if (node.icon != null) {
				Image img = node.icon.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT); 	
				this.setIcon(new ImageIcon(img));//设置JLable的图片
			}
			this.setIconTextGap(8);
			this.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 4));
			this.setFont(new Font("宋体",Font.BOLD, 14));
			if (isSelected){
				this.setForeground(list.getSelectionForeground());
		    	this.setBackground(list.getSelectionBackground());
			}else{
				this.setForeground(list.getForeground());
				this.setBackground(list.getBackground());
			}
		}
		return this;
	}

}
