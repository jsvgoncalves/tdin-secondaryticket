package view;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JPanel;

public class SwapablePanel extends JPanel{

	private static final long serialVersionUID = -540974679709539859L;
	private HashMap<String, JPanel> panels_map = new HashMap<String, JPanel>();

	public SwapablePanel() {
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
	}

	public void addPanel(String s, JPanel p){
		panels_map.put(s, p);
	}
	
	public void removePanel(String s){
		panels_map.remove(s);
	}

	public void updatePanel(String s, JPanel new_panel){
		if(panels_map.containsKey(s)){
			panels_map.remove(s);
			panels_map.put(s, new_panel);
		}
	}

	public void switchTo(String panel_key){
		removeAll();
		add(panels_map.get(panel_key));
		revalidate();
		repaint();
	}
}
