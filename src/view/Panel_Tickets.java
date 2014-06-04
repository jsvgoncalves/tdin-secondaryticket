package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import network.NetworkManager;

import resources.Colors;
import resources.SecondaryTicketCallbackInterface;
import resources.Strings;
import controller.DataStore;
import model.SecondaryTicket;
import model.SecondaryTicket.STATUS;

public class Panel_Tickets extends JPanel implements SecondaryTicketCallbackInterface{

	/**
	 * Panel in which the it guy views and solves all the tickets
	 */
	private static final long serialVersionUID = 2544401527566094718L;

	private JPanel ticket_list = new JPanel();
	public static JPanel ticket_area = new JPanel();
	
	private static boolean isFirst = true;

	public Panel_Tickets() {
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
		JPanel list_panel = new JPanel();
		list_panel.setLayout(new BorderLayout());

		ticket_list.setLayout(new BoxLayout(ticket_list, BoxLayout.PAGE_AXIS));
		//addTestTicketList();
		//loadSecondaryTickets();

		Button_App logout_btn = new Button_App(Strings.BUTTON_LOGOUT);
		logout_btn.setFont(logout_btn.getFont().deriveFont(17f));

		logout_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DataStore store = DataStore.getInstance();
				
				synchronized (store.getSyncMutex()) {
					store.currentLoggedDepartment = null;
					store.manager = NetworkManager.createNetworkManager(null, null);
				}
				
				ApplicationFrame.clearSpace();
				ApplicationFrame.SwitchPanel(ApplicationFrame.PANEL_LOGIN_ID);
			}
		});

		JScrollPane list_scroll = new JScrollPane(ticket_list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		list_scroll.getVerticalScrollBar().setUnitIncrement(16);
		JScrollPane ticket_scroll = new JScrollPane(ticket_area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		list_panel.add(list_scroll);
		list_panel.add(logout_btn, BorderLayout.SOUTH);
		JSplitPane split_pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list_panel, ticket_scroll);	
		split_pane.setResizeWeight(0.3);
		add(split_pane);

	}

	public void loadTicket(String uuid){
		
		ticket_area.removeAll();
		
		if( uuid == null )
			return;
			
		ticket_area.setBackground(Colors.dark_grey);
		
		DataStore store = DataStore.getInstance();
		SecondaryTicket secondaryTicket = null;
		synchronized (store.getSyncMutex()) {
			secondaryTicket = store.secTicketsDB.get(uuid);	
		}
		
		if( secondaryTicket == null )
			return;

		//ticket_area.removeAll();
		ticket_area.setLayout(new BorderLayout());

		JPanel ticket_info_panel = new JPanel();
		ticket_info_panel.setOpaque(false);
		ticket_info_panel.setLayout(new BoxLayout(ticket_info_panel, BoxLayout.PAGE_AXIS));
		ticket_info_panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

		JLabel title_lbl = new JLabel(secondaryTicket.getTitle());
		title_lbl.setFont(title_lbl.getFont().deriveFont(25f));
		title_lbl.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		String desc = secondaryTicket.getDescription();
		desc = desc.replaceAll("\n", "<br>");
		JLabel description_lbl = new JLabel("<html>"+desc+"</html>"); 
		description_lbl.setAutoscrolls(true);
		description_lbl.setFont(title_lbl.getFont().deriveFont(18f));
		description_lbl.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JLabel name_lbl = new JLabel(Strings.PRE_SUBMITED_BY + secondaryTicket.getSubmitedBy());
		name_lbl.setFont(title_lbl.getFont().deriveFont(15f));
		name_lbl.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 20));

		JLabel email_lbl = new JLabel(Strings.PRE_SOLVER + secondaryTicket.getSolver());
		email_lbl.setFont(title_lbl.getFont().deriveFont(15f));
		email_lbl.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 20));

		JLabel date_lbl = new JLabel(Strings.PRE_DATE + secondaryTicket.getCreated());
		date_lbl.setFont(title_lbl.getFont().deriveFont(15f));
		date_lbl.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 20));

		JLabel id_lbl = new JLabel(Strings.PRE_ID + secondaryTicket.getID());
		id_lbl.setFont(title_lbl.getFont().deriveFont(15f));
		id_lbl.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 20));

		title_lbl.setForeground(Color.white);
		description_lbl.setForeground(Color.white);
		name_lbl.setForeground(Color.white);
		email_lbl.setForeground(Color.white);
		date_lbl.setForeground(Color.white);
		id_lbl.setForeground(Color.white);

		ticket_info_panel.add(title_lbl);
		ticket_info_panel.add(description_lbl);
		ticket_info_panel.add(name_lbl);
		ticket_info_panel.add(email_lbl);
		ticket_info_panel.add(date_lbl);
		ticket_info_panel.add(id_lbl);



		JPanel solution_panel = new JPanel();
		solution_panel.setOpaque(false);
		solution_panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		solution_panel.setLayout(new BorderLayout());
		final JTextArea solution_txt = new JTextArea(secondaryTicket.getReply() == null ? "" : secondaryTicket.getReply());
		JScrollPane solution_scroll = new JScrollPane(solution_txt);
		JLabel solution_lbl = new JLabel(Strings.TITLE_SOLUTION);
		solution_lbl.setForeground(Colors.orange);
		solution_lbl.setOpaque(false);
		solution_lbl.setFont(solution_lbl.getFont().deriveFont(18f));
		solution_lbl.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
		
		Button_App solve_btn = new Button_App(Strings.BUTTON_SOLVE);
		solve_btn.setFont(solve_btn.getFont().deriveFont(17f));
		
		
		final SecondaryTicket _ticket = secondaryTicket;
		solve_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				DataStore store = DataStore.getInstance();
				
				synchronized (store.getSyncMutex()) {
					
					_ticket.setReply(solution_txt.getText().trim());
					_ticket.setStatus(STATUS.SOLVED.CODE);
					
					if( store.manager.replyTicket(_ticket) )
					{
						List<SecondaryTicket> list = store.secTickets.get(store.currentLoggedDepartment);
						
						if( list != null )
							list.remove(_ticket);
						
						store.secTicketsDB.remove(_ticket.getID());
						
						ApplicationFrame.clearSpace();
						
						Panel_Tickets.this.clearAll();
						
						for(SecondaryTicket s: list)
						{
							Panel_Tickets.this.appendSecondaryTicket(s);
						}
						
						ApplicationFrame.redraw();
					}
					
					store.writeStore(null);
				}
			}
		});

		solution_panel.add(solution_lbl,BorderLayout.NORTH);
		solution_panel.add(solution_scroll);
		solution_panel.add(solve_btn, BorderLayout.SOUTH);


		ticket_area.add(ticket_info_panel, BorderLayout.NORTH);
		ticket_area.add(solution_panel);
		ticket_area.revalidate();
		ticket_area.repaint();
	}

//	private void addTestTicketList(){
//
//		for(int i = 0; i < 25; i++){
//			Ticket t = new Ticket();
//			t.setName("USER " + i);
//			t.setDescription("This is a test description so that this\n field is not emptyhis is a test description\n so that this field is not emptyhis is a test\n description so that this field is not empty :) " + i);
//			String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
//			t.setDate(timeStamp);
//			try {
//				MessageDigest md = MessageDigest.getInstance("MD5");
//				byte[] thedigest = md.digest(new String(""+i).getBytes());
//				t.setID(new String(thedigest));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			t.setTitle("Title " + i);
//			t.setEmail("myemail" + i + "@mail.com");
//			final Panel_Ticket tp = new Panel_Ticket(t);
//
//			DataStore.getInstance().tickets.put(t.getID(), t);
//			ticket_list.add(tp);
//			if(i == 0){
//				tp.setSelected(true);
//			}
//		}
//	}
	
//	public void loadSecondaryTickets()
//	{
//		boolean first = true;
//		
//		DataStore store = DataStore.getInstance();
//		String depID = store.currentLoggedDepartment;
//		List<SecondaryTicket> list = null;
//		
//		if( depID == null )
//			return;
//		
//		list = store.secTickets.get(depID);
//		
//		if( list == null )
//			return;
//		
//		for(SecondaryTicket s : list)
//		{
//			final Panel_Ticket tp = new Panel_Ticket(s);
//
//			ticket_list.add(tp);
//			if(first)
//			{
//				tp.setSelected(true);
//				first = false;
//			}
//		}
//	}

	@Override
	public void clearAll() {
		ticket_list.removeAll();
		isFirst = true;
	}

	@Override
	public void appendSecondaryTicket(SecondaryTicket ticket)
	{
		
		final Panel_Ticket tp = new Panel_Ticket(ticket, this);

		ticket_list.add(tp);
		if(isFirst)
		{
			tp.setSelected(true);
			isFirst = false;
		}
	}

}
