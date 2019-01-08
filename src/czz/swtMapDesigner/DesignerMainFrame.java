package czz.swtMapDesigner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 地图编辑器主窗体
 * @author CZZ
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
	 * 工具栏-打开图片
	 * */
	JButton openImageTB;
	
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
	 * 添加节点模板
	 * */
	JButton addNodeTemplateButton;
	
	/**
	 * 编辑节点模板
	 * */
	JButton editNodeTemplateButton;
	
	/**
	 * 移除节点模板
	 * */
	JButton removeNodeTemplateButton;
	
	/**
	 * 节点列表的滚动条
	 * */
	JScrollPane nodeScrollPane;
	
	/**
	 * 节点模板列表
	 * */
	JList<NodeTemplate> nodeTemplateList;
	
	/**
	 * 节点模板列表
	 * */
	DefaultListModel<NodeTemplate> listModel;
	
	/**
	 * 内容区
	 * */
	JScrollPane contextPanle;
	
	/**
	 * 地图图片
	 * */
	ImageIcon mapImage;
	
	/**
	 * 内容区域的背景图片
	 * */
	JLabel imageBackgroundLabel;
	
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
		initToolBar();
		initPanel();
	}
	
	/**
	 * 加载菜单栏
	 * */
	private void initMenu() {
		this.menuBar = new JMenuBar();
		this.setJMenuBar(this.menuBar);
		JMenu fileMenu = new JMenu("文件(F)");			//文件菜单
		fileMenu.setMnemonic('F');
		this.menuBar.add(fileMenu);
		JMenuItem loadImageFile = new JMenuItem("加载图片");
		loadImageFile.addActionListener(e -> openImage_action());
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
	
	private void initToolBar() {
		this.toolBar = new JToolBar("工具栏");
		this.add(this.toolBar, BorderLayout.NORTH);
		ImageIcon openImageIcon = new ImageIcon("source/icon/Picture.png");
		openImageIcon.setImage(openImageIcon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
		this.openImageTB = new JButton(openImageIcon);
		this.openImageTB.addActionListener(e -> openImage_action());
		this.toolBar.add(openImageTB);
	}
	
	/**
	 * 加载左边栏
	 * */
	private void initPanel() {
		this.mainPanel = new JPanel();
		this.add(this.mainPanel, BorderLayout.CENTER);
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints bagConstraints= new GridBagConstraints();
		bagConstraints.fill = GridBagConstraints.BOTH;
		bagConstraints.weighty = 1;
		this.mainPanel.setLayout(bagLayout);
		
		this.leftPanel = new JPanel();
		this.leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
		
		this.contextPanle = new JScrollPane();
		this.contextPanle.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.mainPanel.add(contextPanle);
		this.contextPanle.setBackground(Color.GRAY);
		bagConstraints.gridy = 0;
		bagConstraints.gridx = 1;
		bagConstraints.gridheight = 1;
		bagConstraints.gridwidth = 0;
		bagConstraints.weightx = 1;
		bagLayout.setConstraints(contextPanle, bagConstraints);
		
		this.lineToButton = new JButton("----------");
		this.lineToButton.setMinimumSize(new Dimension(200, 100));
		JPanel lineToPanel = new JPanel();
		lineToPanel.setMinimumSize(new Dimension(150, 100));
		lineToPanel.setMaximumSize(new Dimension(150, 100));
		this.leftPanel.add(lineToPanel);
		lineToPanel.add(this.lineToButton);
		this.leftPanel.add(Box.createVerticalStrut(5));
		JPanel leftPanelTools = new JPanel();
		leftPanelTools.setMinimumSize(new Dimension(200, 100));
		leftPanelTools.setMaximumSize(new Dimension(200, 100));
		leftPanelTools.setLayout(new GridLayout(1, 2));
		this.leftPanel.add(leftPanelTools);
		this.addNodeTemplateButton = new JButton("添加");
		leftPanelTools.add(this.addNodeTemplateButton);
		this.editNodeTemplateButton = new JButton("编辑");
		leftPanelTools.add(this.editNodeTemplateButton);
		this.removeNodeTemplateButton = new JButton("移除");
		leftPanelTools.add(this.removeNodeTemplateButton);
		this.leftPanel.add(Box.createVerticalStrut(5));
		this.nodeScrollPane = new JScrollPane();
		this.leftPanel.add(this.nodeScrollPane);
		
		this.imageBackgroundLabel = new JLabel();
		this.contextPanle.setViewportView(this.imageBackgroundLabel);
		
		this.nodeTemplateList = new JList<NodeTemplate>();
		this.nodeScrollPane.setViewportView(this.nodeTemplateList);
		this.nodeTemplateList.setBounds(0, 0, 100, 300);
		this.nodeTemplateList.setCellRenderer(new NodeModelListCellRenderer());
		this.nodeTemplateList.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		this.listModel = new DefaultListModel<NodeTemplate>();
		this.addNodeTemplateButton.addActionListener(e -> {
			NodeTemplate node = new NodeTemplate();
			NodeEditDialog nodeEditDialog = new NodeEditDialog(this, node);
			nodeEditDialog.setModal(true);
			nodeEditDialog.setVisible(true);
			if (node.name != null) {
				listModel.addElement(node);
				this.nodeTemplateList.setModel(listModel);
			}
		});
		
		this.editNodeTemplateButton.addActionListener(e -> {
			int index = this.nodeTemplateList.getSelectedIndex();
			if (index >= 0) {
				NodeEditDialog nodeEditDialog = new NodeEditDialog(this, listModel.getElementAt(index));
				nodeEditDialog.setModal(true);
				nodeEditDialog.setVisible(true);
				this.nodeTemplateList.setModel(listModel);
			}
		});
		
		this.removeNodeTemplateButton.addActionListener(e -> {
			int index = this.nodeTemplateList.getSelectedIndex();
			if (index >= 0) {
				listModel.removeElementAt(index);
				this.nodeTemplateList.setModel(listModel);
			}
		});
	}
	
	private void changeBackground(String imagePath) {
		if (imagePath != null && !"".equals(imagePath)) {
			this.mapImage = new ImageIcon(imagePath);
			if (this.mapImage != null) {
				this.imageBackgroundLabel.setBounds(0, 0, this.mapImage.getIconWidth(), this.mapImage.getIconHeight());
				this.imageBackgroundLabel.setIcon(mapImage);
				this.imageBackgroundLabel.setVerticalAlignment(JLabel.NORTH);
			}
		}
	}
	
	/**
	 * 打开图片操作
	 * */
	private void openImage_action() {
		JFileChooser fc = new JFileChooser();
    	fc.addChoosableFileFilter(new FileNameExtensionFilter("图片文件(jpg, bmp, png)", "jpg", "bmp", "png"));
    	File imageDir = new File("source/image");
    	fc.setCurrentDirectory(imageDir);
    	int returnVal = fc.showOpenDialog(this);		//0选择1取消
    	File file = fc.getSelectedFile();
    	if(file != null && returnVal !=1) {					//选择文件
    		System.out.println(returnVal);
        	System.out.println(file.getPath() + file.getName());
        	changeBackground(file.getPath());
    	}
    	else {
    		System.out.println(returnVal);
    	}
	}
}

/**
 * 节点编辑器类
 * @author CZZ
 * */
class NodeEditDialog extends JDialog {

    /**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 3596621668052690347L;

	/**
	 * 对应的节点
	 * */
	NodeTemplate node;
	
	/**
	 * id输入
	 * */
	JTextField idText;
	
	/**
	 * 名字提示文本
	 * */
	final JLabel nameLabel = new JLabel("名称");
	
	/**
	 * 名字输入
	 * */
	JTextField nameText;
	
	/**
	 * 图标展示
	 * */
	JLabel icon;
	
	/**
	 * 选择图标文件按钮
	 * */
	final JButton iconButton = new JButton("选择图标");
	
	/**
	 * 确定按钮
	 * */
	final JButton okButton = new JButton("确定");
	
	/**
	 * 取消按钮
	 * */
	final JButton cancelButton = new JButton("取消");
	
	//====================methods====================
	
	public NodeEditDialog(DesignerMainFrame frame, NodeTemplate node){
        super(frame,"节点模板",true);
        this.setIconImage(null);
        this.setLayout(new GridLayout(4, 2));
        this.node = node;
        this.add(new JLabel("ID"));
        idText = new JTextField(10);
        this.add(idText);
        this.add(this.nameLabel);
        nameText = new JTextField(30);
        this.add(nameText);
        icon = new JLabel();
        //icon.setPreferredSize(new Dimension(100, 100));
        this.add(icon);
        this.add(iconButton);
        this.add(okButton);
        this.add(cancelButton);
        if (this.node != null) {
        	this.idText.setText(Integer.valueOf(node.id).toString());
        	this.nameText.setText(node.name);
        	if (node.icon != null) {
        		Image img = node.icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT); 	
				this.icon.setIcon(new ImageIcon(img));//设置JLable的图片
        	}
        }
        int width = 200;
        int height = 400;
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
        this.setBounds(x, y, width, height);
        
        this.iconButton.addActionListener(e -> {
        	JFileChooser fc = new JFileChooser();
        	fc.addChoosableFileFilter(new FileNameExtensionFilter("图片文件(jpg, bmp, png)", "jpg", "bmp", "png"));
        	File imageDir = new File("source/image");
        	fc.setCurrentDirectory(imageDir);
        	int returnVal = fc.showOpenDialog(this);		//0选择1取消
        	File file = fc.getSelectedFile();
        	if(file != null && returnVal !=1) {					//选择文件
        		System.out.println(returnVal);
            	System.out.println(file.getPath() + file.getName());
            	ImageIcon imageIcon = new ImageIcon(file.getPath());
            	imageIcon.setImage(imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    			this.icon.setIcon(imageIcon);
        	}
        	else {
        		System.out.println(returnVal);
        	}
        });
        
        this.okButton.addActionListener(e -> {
        	if (this.nameText.getText() != null && !"".equals(this.nameText.getText())) {
        		this.node.id = Integer.parseInt(this.idText.getText());
        		this.node.name = this.nameText.getText();
        		this.node.icon = (ImageIcon) this.icon.getIcon();
        		this.dispose();
        	} else {
        		this.nameLabel.setText("名称（必填）");
        		this.nameLabel.repaint();
        		System.out.println("名称为空");
        	}
        });
        
        this.cancelButton.addActionListener(e -> {
        	this.dispose();
        });
    }
}
