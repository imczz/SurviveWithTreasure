import java.util.Arrays;

import czz.swtMapDesigner.DesignerMainFrame;

public class MainClass {

	public static void main(String[] args) {
		System.out.println("Hello world!");
		System.out.println(Arrays.toString(args));
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("-designer")) {
				System.out.println("打开地图编辑器");
				DesignerMainFrame designer = new DesignerMainFrame();
				designer.setVisible(true);
				designer.pack();
			}
		}
	}
}
