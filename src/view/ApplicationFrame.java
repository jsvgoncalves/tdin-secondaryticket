package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import resources.Strings;

public class ApplicationFrame extends JFrame{
	private static final long serialVersionUID = -2057679725841274317L;
	
	public static final String PANEL_LOGIN_ID = "LOGIN";
	
	private SwapablePanel swap_panel = new SwapablePanel();
	private JPanel login_panel = new Panel_Login();
	
	public ApplicationFrame() {
		init();
	}

	private void init() {
		setTitle(Strings.TITLE_APP);
		setLayout(new BorderLayout());		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(800,600));
		setMinimumSize(new Dimension(800,600));
	
		swap_panel.addPanel(PANEL_LOGIN_ID, login_panel);//Add login panel to swapable panel
		swap_panel.switchTo(PANEL_LOGIN_ID); //Use login panel as default
		
		add(swap_panel);
		setVisible(true);
	}
}
