package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.Colors;
import resources.Strings;
import model.SecondaryTicket;

public class Panel_Ticket extends JPanel implements MouseListener{

	/**
	 * Panel that shows all the ticket information
	 */
	private static final long serialVersionUID = -58416643992390702L;

	JLabel ticket_id_lbl = new JLabel();
	private boolean isSelected = false;
	private SecondaryTicket ticket;
	JPanel content_panel = new JPanel();
	private Panel_Tickets mainPanel = null;

	public Panel_Ticket(SecondaryTicket ticket, Panel_Tickets mainPanel) {
		this.ticket = ticket;
		this.mainPanel = mainPanel;
		isSelected = false;
		init();
		addMouseListener(this);
	}

	public SecondaryTicket gettiTicket(){
		return this.ticket;
	}

	private void init() {
		setLayout(new BorderLayout());
		content_panel.setOpaque(false);
		content_panel.setLayout(new BoxLayout(content_panel, BoxLayout.PAGE_AXIS));

		JLabel Title_lbl = new JLabel(ticket.getTitle());
		Title_lbl.setFont(Title_lbl.getFont().deriveFont(17f));
		content_panel.add(Title_lbl); //Add title
		JLabel Date_lbl = new JLabel(Strings.PRE_DATE + ticket.getCreated());
		content_panel.add(Date_lbl);  //Add Label
		setBackground(Colors.TICKET_IDLE_BACKCOLOR);

		add(content_panel);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	public boolean isSelected(){
		return isSelected;
	}

	/**
	 * set if panel is selected and change color accordingly
	 * @param isSelected
	 */
	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;
		if(isSelected)
		{
			setBackground(Colors.TICKET_SELECTED_BACKCOLOR);
			mainPanel.loadTicket(this.gettiTicket().getID());
		}
		else{
			setBackground(Colors.TICKET_IDLE_BACKCOLOR);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		for (Component comp : Panel_Ticket.this.getParent().getComponents()) {
			if(comp instanceof Panel_Ticket){
				((Panel_Ticket)comp).setSelected(false); //remove selection from all ticket panels
			}
		}
		this.setSelected(true);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	@Override
	public boolean equals(Object obj)
	{
		if(    obj != null
			&& obj.getClass() == SecondaryTicket.class )
		{
			SecondaryTicket _ticket = (SecondaryTicket)obj;
			
			return this.ticket != null && this.ticket.equals(_ticket.getID());
		}
		
		
		return super.equals(obj);
	}
}
