package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import resources.Colors;


public class HintTextField extends JPasswordField implements FocusListener, DocumentListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Icon icon;
	private String hint;
	public static final int TYPE_PASSWORD = 0;
	public static final int TYPE_TEXT = 1;

	private int type = TYPE_TEXT;
	@SuppressWarnings("unused")
	private Insets dummyInsets;

	public HintTextField(String hint, int type){
		setEchoChar((char)0);
		this.hint = hint;
		this.type = type;
		Border border = UIManager.getBorder("TextField.border");
		JTextField dummy = new JTextField();
		this.dummyInsets = border.getBorderInsets(dummy);
		addFocusListener(this);
		getDocument().addDocumentListener(this);
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int textX = 2;

		if(this.icon!=null){
			int iconWidth = icon.getIconWidth();
			int iconHeight = icon.getIconHeight();
			int x = 0;
			textX = x+iconWidth+2;
			int y = (this.getHeight() - iconHeight)/2;
			icon.paintIcon(this, g, x, y);
		}

		setMargin(new Insets(2, textX, 2, 2));
		//draw hint string
		if (new String(this.getPassword()).equals("")) {
			this.getWidth();
			int height = this.getHeight();
			Font prev = g.getFont();
			Font italic = prev.deriveFont(Font.ITALIC);
			italic = italic.deriveFont(15f);

			Color prevColor = g.getColor();
			g.setFont(italic);
			g.setColor(Colors.light_grey);
			int h = g.getFontMetrics().getHeight();
			int textBottom = (height - h) / 2 + h - 4;
			int x = this.getInsets().left;
			Graphics2D g2d = (Graphics2D) g;
			RenderingHints hints = g2d.getRenderingHints();
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.drawString(hint, x, textBottom);
			g2d.setRenderingHints(hints);
			g.setFont(prev);
			g.setColor(prevColor);
		}

	}

	@Override
	public void focusGained(FocusEvent arg0) {
		this.repaint();
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		this.repaint();
	}
	public void repaintAll()
	{

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		if(HintTextField.this.type == TYPE_PASSWORD){ //if it is a password and field not empty then mask field
			if(!new String(HintTextField.this.getPassword()).isEmpty())
				setEchoChar('*');
			else
				setEchoChar((char)0);
		}
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		if(HintTextField.this.type == TYPE_PASSWORD){
			if(!new String(HintTextField.this.getPassword()).isEmpty())
				setEchoChar('*');
			else
				setEchoChar((char)0);
		}
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		if(HintTextField.this.type == TYPE_PASSWORD){
			if(!new String(HintTextField.this.getPassword()).isEmpty())
				setEchoChar('*');
			else
				setEchoChar((char)0);
		}
	}

}

