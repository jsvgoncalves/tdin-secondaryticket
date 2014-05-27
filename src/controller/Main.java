package controller;

import javax.swing.UIManager;
import resources.Icons;
import view.ApplicationFrame;

public class Main {

	public static void main(String[] args) {
		  try {
			UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		Icons.initResources();
		new ApplicationFrame();
	}

}
