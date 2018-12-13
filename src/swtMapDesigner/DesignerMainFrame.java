package swtMapDesigner;

import java.awt.BorderLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 编辑器主窗体
 * */
public class DesignerMainFrame extends JFrame{

	/**
	 * 序列化序列号
	 */
	private static final long serialVersionUID = -6218860336474324005L;

	/**
	 * 菜单栏
	 * */
	MenuBar menuBar;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public DesignerMainFrame() {
		super("地图编辑器");
		initMenu();
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			//关闭按钮
		setExtendedState(JFrame.MAXIMIZED_BOTH);				//启动时最大化窗体
	}
	
	/**
	 * 加载菜单栏
	 * */
	private void initMenu() {
		menuBar = new MenuBar();
		this.setMenuBar(menuBar);
		Menu fileMenu = new Menu("File");			//文件菜单
		menuBar.add(fileMenu);
		MenuItem loadImageFile = new MenuItem("Load Image");
		fileMenu.add(loadImageFile);
		fileMenu.addSeparator();
		MenuItem exitMenu = new MenuItem("Exit");
		fileMenu.add(exitMenu);
		
		ActionListener menuListener = e -> {  
            String cmd = e.getActionCommand();  
            if (cmd.equals("Exit")) {  
            	int option = JOptionPane.showConfirmDialog(rootPane, "确定退出？", "退出", JOptionPane.YES_NO_OPTION); 
            	if(option == JOptionPane.YES_OPTION) System.exit(0); 
            }
            else if(cmd.equals("Load Image")) {
            	/*
            	FileDialog fileLoadDialog = new FileDialog(this,"load file",FileDialog.LOAD);
            	fileLoadDialog.setVisible(true);
            	System.out.println(fileLoadDialog.getDirectory() + fileLoadDialog.getFile());
            	*/
            	JFileChooser fc = new JFileChooser();
            	fc.addChoosableFileFilter(new FileNameExtensionFilter("图片文件(jpg, bmp, png)", "jpg", "bmp", "png"));
            	int returnVal = fc.showOpenDialog(this);		//0选择1取消
            	File file = fc.getSelectedFile();
            	if(file != null && returnVal !=1) {					//选择文件
            		System.out.println(returnVal);
                	System.out.println(file.getPath() + file.getName());
            	}
            	else {
            		System.out.println(returnVal);
            	}
            }
        };
        
        fileMenu.addActionListener(menuListener);
	}
}
