package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import resources.Strings;

public class ApplicationFrame extends JFrame{
	private static final long serialVersionUID = -2057679725841274317L;
	
	public ApplicationFrame() {
		init();
	}

	private void init() {
		setTitle(Strings.TITLE_APP);
		setLayout(new BorderLayout());		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(800,600));
		setMinimumSize(new Dimension(800,600));
		setVisible(true);
	}
}
