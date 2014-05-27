package view;

import java.awt.Graphics;

import javax.swing.JButton;

import resources.Colors;

public class Button_App extends JButton{
	/**
	 * Default button
	 */
	private static final long serialVersionUID = 897086233324086011L;

	public Button_App() {
		init();
	}

	public Button_App(String text) {
		setText(text);
		init();
	}

	private void init() {
		setBorderPainted(false);
		setContentAreaFilled(false);
		setBackground(Colors.dark_grey);
		setForeground(Colors.orange);
		setFocusable(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setOpaque(false);
		if (getModel().isPressed() || getModel().isArmed()) {
			g.setColor(Colors.BUTTON_PRESSED_BACKCOLOR);
			setForeground(Colors.BUTTON_PRESSED_TEXTCOLOR);
		} else if (getModel().isRollover()) {
			g.setColor(Colors.BUTTON_OVER_BACKCOLOR);
			setForeground(Colors.BUTTON_OVER_TEXTCOLOR);
		} else {
			g.setColor(Colors.BUTTON_IDLE_BACKCOLOR);
			setForeground(Colors.BUTTON_IDLE_TEXTCOLOR);
		}
		
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
}
