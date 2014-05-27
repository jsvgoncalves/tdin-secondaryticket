package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import resources.Colors;
import resources.Strings;
import controller.Variables;
import model.Ticket;

public class Panel_Tickets extends JPanel{

	/**
	 * Panel in which the it guy views and solves all the tickets
	 */
	private static final long serialVersionUID = 2544401527566094718L;

	JPanel ticket_list = new JPanel();
	static JPanel ticket_area = new JPanel();

	public Panel_Tickets() {
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
		JPanel list_panel = new JPanel();
		list_panel.setLayout(new BorderLayout());

		ticket_list.setLayout(new BoxLayout(ticket_list, BoxLayout.PAGE_AXIS));
		addTestTicketList();

		Button_App logout_btn = new Button_App(Strings.BUTTON_LOGOUT);
		logout_btn.setFont(logout_btn.getFont().deriveFont(17f));

		logout_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
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

	public static void loadTicket(String uuid){
		ticket_area.setBackground(Colors.dark_grey);
		Ticket t = Variables.tickets.get(uuid);
		ticket_area.removeAll();
		ticket_area.setLayout(new BorderLayout());

		JPanel ticket_info_panel = new JPanel();
		ticket_info_panel.setOpaque(false);
		ticket_info_panel.setLayout(new BoxLayout(ticket_info_panel, BoxLayout.PAGE_AXIS));
		ticket_info_panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

		JLabel title_lbl = new JLabel(t.getTitle());
		title_lbl.setFont(title_lbl.getFont().deriveFont(25f));
		title_lbl.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		String desc = t.getDescription();
		desc = desc.replaceAll("\n", "<br>");
		JLabel description_lbl = new JLabel("<html>"+desc+"</html>"); 
		description_lbl.setAutoscrolls(true);
		description_lbl.setFont(title_lbl.getFont().deriveFont(18f));
		description_lbl.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JLabel name_lbl = new JLabel(Strings.PRE_SUBMITED_BY + t.getName());
		name_lbl.setFont(title_lbl.getFont().deriveFont(15f));
		name_lbl.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 20));

		JLabel email_lbl = new JLabel(Strings.PRE_EMAIL + t.getEmail());
		email_lbl.setFont(title_lbl.getFont().deriveFont(15f));
		email_lbl.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 20));

		JLabel date_lbl = new JLabel(Strings.PRE_DATE + t.getDate());
		date_lbl.setFont(title_lbl.getFont().deriveFont(15f));
		date_lbl.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 20));

		JLabel id_lbl = new JLabel(Strings.PRE_ID + t.getID());
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

		Button_App solve_btn = new Button_App(Strings.BUTTON_SOLVE);
		solve_btn.setFont(solve_btn.getFont().deriveFont(17f));
		solve_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO: solve the trouble ticket
			}
		});

		JPanel solution_panel = new JPanel();
		solution_panel.setOpaque(false);
		solution_panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		solution_panel.setLayout(new BorderLayout());
		JTextArea solution_txt = new JTextArea();
		JScrollPane solution_scroll = new JScrollPane(solution_txt);
		JLabel solution_lbl = new JLabel(Strings.TITLE_SOLUTION);
		solution_lbl.setForeground(Colors.orange);
		solution_lbl.setOpaque(false);
		solution_lbl.setFont(solution_lbl.getFont().deriveFont(18f));
		solution_lbl.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

		solution_panel.add(solution_lbl,BorderLayout.NORTH);
		solution_panel.add(solution_scroll);
		solution_panel.add(solve_btn, BorderLayout.SOUTH);


		ticket_area.add(ticket_info_panel, BorderLayout.NORTH);
		ticket_area.add(solution_panel);
		ticket_area.revalidate();
		ticket_area.repaint();
	}

	private void addTestTicketList(){

		for(int i = 0; i < 25; i++){
			Ticket t = new Ticket();
			t.setName("USER " + i);
			t.setDescription("This is a test description so that this\n field is not emptyhis is a test description\n so that this field is not emptyhis is a test\n description so that this field is not empty :) " + i);
			String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			t.setDate(timeStamp);
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] thedigest = md.digest(new String(""+i).getBytes());
				t.setID(new String(thedigest));
			} catch (Exception e) {
				e.printStackTrace();
			}
			t.setTitle("Title " + i);
			t.setEmail("myemail" + i + "@mail.com");
			final Panel_Ticket tp = new Panel_Ticket(t);

			Variables.tickets.put(t.getID(), t);
			ticket_list.add(tp);
			if(i == 0){
				tp.setSelected(true);
			}
		}
	}
}
