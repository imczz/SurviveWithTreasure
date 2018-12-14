package czz.swtMapDesigner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
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
	JMenuBar menuBar;
	
	/**
	 * 工具栏
	 * */
	JToolBar toolBar;
	
	/**
	 * 全局Panel
	 * */
	JPanel mainPanel;
	
	/**
	 * 左边栏
	 * */
	JPanel leftPanel;
	
	/**
	 * 连接两个顶点，形成一条边
	 * */
	JButton lineToButton;
	
	/**
	 * 左边栏工具
	 * */
	JPanel leftPanelTools;
	
	/**
	 * 添加节点模板
	 * */
	JButton addNodeTemplateButton;
	
	/**
	 * 移除节点模板
	 * */
	JButton removeNodeTemplateButton;
	
	/**
	 * 内容区
	 * */
	JPanel contextPanle;
	
	//====================methods====================
	
	/**
	 * 构造方法
	 * */
	public DesignerMainFrame() {
		super("地图编辑器");
		
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			//关闭按钮
		//setSize(Toolkit.getDefaultToolkit().getScreenSize()); 
		//setLocation(0,0); 
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);				//启动时最大化窗体
		
		initMenu();
		initLeftPanel();
		
		this.toolBar = new JToolBar("工具栏");
		this.add(this.toolBar, BorderLayout.NORTH);
		this.toolBar.add(new JButton("000000"));
	}
	
	/**
	 * 加载菜单栏
	 * */
	private void initMenu() {
		this.menuBar = new JMenuBar();
		this.setJMenuBar(this.menuBar);
		JMenu fileMenu = new JMenu("文件");			//文件菜单
		this.menuBar.add(fileMenu);
		JMenuItem loadImageFile = new JMenuItem("加载图片");
		loadImageFile.addActionListener(e -> {
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
		});
		fileMenu.add(loadImageFile);
		fileMenu.addSeparator();
		JMenuItem exitMenu = new JMenuItem("退出(X)");
		exitMenu.setMnemonic('X');
		exitMenu.addActionListener(e -> {
			int option = JOptionPane.showConfirmDialog(rootPane, "确定退出？", "退出", JOptionPane.YES_NO_OPTION); 
        	if(option == JOptionPane.YES_OPTION) System.exit(0); 
	    });
		fileMenu.add(exitMenu);
	}
	
	/**
	 * 加载左边栏
	 * */
	private void initLeftPanel() {
		this.mainPanel = new JPanel();
		this.add(this.mainPanel, BorderLayout.CENTER);
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints bagConstraints= new GridBagConstraints();
		bagConstraints.fill = GridBagConstraints.BOTH;
		bagConstraints.weighty = 1;
		this.mainPanel.setLayout(bagLayout);
		
		this.leftPanel = new JPanel();
		BoxLayout boxLayout=new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS); 
		this.leftPanel.setLayout(boxLayout);
		this.mainPanel.add(leftPanel);
		this.leftPanel.setBackground(Color.GRAY);
		bagConstraints.gridy = 0;
		bagConstraints.gridx = 0;
		bagConstraints.ipadx = 10;
		bagConstraints.ipady = 10;
		bagConstraints.gridheight = 1;
		bagConstraints.gridwidth = 1;
		bagConstraints.weightx = 0;
		bagLayout.setConstraints(leftPanel, bagConstraints);
		
		this.contextPanle = new JPanel();
		this.mainPanel.add(contextPanle);
		this.contextPanle.add(new JButton("222222"));
		this.contextPanle.setBackground(Color.white);
		bagConstraints.gridy = 0;
		bagConstraints.gridx = 1;
		bagConstraints.gridheight = 1;
		bagConstraints.gridwidth = 0;
		bagConstraints.weightx = 1;
		bagLayout.setConstraints(contextPanle, bagConstraints);
		
		this.lineToButton = new JButton("------------------");
		//this.lineToButton.setSize(100, 30);
		this.leftPanel.add(this.lineToButton);
		
		this.leftPanelTools = new JPanel();
		this.leftPanel.add(this.leftPanelTools);
		this.addNodeTemplateButton = new JButton("添加");
		this.leftPanelTools.add(this.addNodeTemplateButton);
		this.removeNodeTemplateButton = new JButton("移除");
		this.leftPanelTools.add(this.removeNodeTemplateButton);
	}
	
}
