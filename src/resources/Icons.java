package resources;

import javax.swing.ImageIcon;

public class Icons {

	public static ImageIcon icon_app;

	public static void initResources() {
		icon_app = new ImageIcon(Icons.class.getResource("images/app_icon.png"));
	}
}
