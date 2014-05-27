package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.Colors;
import resources.Icons;
import resources.Strings;

public class Panel_Login extends JPanel{

	/**
	 * Panel used for all the login operations
	 */
	private static final long serialVersionUID = 3282961147446486244L;
	private final int margins = 2;
	private JPanel left_panel = new JPanel();
	private SwapablePanel swap_panel = new SwapablePanel();
	private JPanel login_panel = new JPanel(), register_panel = new JPanel();

	private final String ID_LOGIN = "LOGIN";
	private final String ID_REGISTER = "REGISTER";

	public Panel_Login() {
		init();
	}

	private void init() {
		setLayout(new GridLayout(1,2));
		left_panel.setLayout(new BorderLayout());
		left_panel.setBackground(Color.white);
		left_panel.add(new JLabel(Icons.icon_app));

		initLoginInterface();
		initRegisterInterface();
		swap_panel.addPanel(ID_LOGIN, login_panel);
		swap_panel.setBackground(Colors.dark_grey);
		swap_panel.addPanel(ID_REGISTER, register_panel);
		swap_panel.switchTo(ID_LOGIN);
		
		swap_panel.setMinimumSize(new Dimension(600,600));
		swap_panel.setPreferredSize(new Dimension(600,600));
		swap_panel.setSize(new Dimension(600,600));

		add(left_panel, BorderLayout.WEST);
		add(swap_panel, BorderLayout.CENTER);
	}

	private void initLoginInterface(){
		login_panel = new JPanel();
		login_panel.setBackground(Colors.dark_grey);
		login_panel.setLayout(new BoxLayout(login_panel, BoxLayout.PAGE_AXIS));
		Button_App login_btn = new Button_App(Strings.BUTTON_LOGIN);
		Button_App register_btn = new Button_App(Strings.BUTTON_REGISTER);
		login_btn.setFont(login_btn.getFont().deriveFont(17f));
		register_btn.setFont(register_btn.getFont().deriveFont(17f));

		register_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				initRegisterInterface();
				swap_panel.updatePanel(ID_REGISTER, register_panel);
				swap_panel.switchTo(ID_REGISTER);
			}
		});
		
		login_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO: LOGIN USER
				ApplicationFrame.SwitchPanel(ApplicationFrame.PANEL_TICKER_AREA_ID);
			}
		});

		HintTextField username_txt = new HintTextField(Strings.HINT_USERNAME, HintTextField.TYPE_TEXT);
		HintTextField password_txt = new HintTextField(Strings.HINT_PASSWORD, HintTextField.TYPE_PASSWORD);
		username_txt.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
		password_txt.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));

		username_txt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, margins, 1, margins, Colors.dark_grey),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		password_txt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, margins, 0, margins, Colors.dark_grey),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		//Make buttons occupy all width
		JPanel btn_login_panel = new JPanel();
		btn_login_panel.setLayout(new BorderLayout());
		btn_login_panel.add(login_btn);
		btn_login_panel.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
		btn_login_panel.setBorder(BorderFactory.createMatteBorder(0, margins, 0, margins, Colors.dark_grey));

		JPanel btn_register_panel = new JPanel();
		btn_register_panel.setLayout(new BorderLayout());
		btn_register_panel.add(register_btn);
		btn_register_panel.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
		btn_register_panel.setBorder(BorderFactory.createMatteBorder(0, margins, 0, margins, Colors.dark_grey));

		login_panel.add(Box.createVerticalGlue());
		login_panel.add(username_txt);
		login_panel.add(password_txt);
		login_panel.add(btn_login_panel);
		login_panel.add(btn_register_panel);
		login_panel.add(Box.createVerticalGlue());
	}

	private void initRegisterInterface(){
		register_panel = new JPanel();
		register_panel.setBackground(Colors.dark_grey);

		register_panel.setLayout(new BoxLayout(register_panel, BoxLayout.PAGE_AXIS));
		Button_App cancel_btn = new Button_App(Strings.BUTTON_CANCEL);
		Button_App register_btn = new Button_App(Strings.BUTTON_REGISTER);
		
		cancel_btn.setFont(cancel_btn.getFont().deriveFont(17f));
		register_btn.setFont(register_btn.getFont().deriveFont(17f));
		
		cancel_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				initLoginInterface();
				swap_panel.updatePanel(ID_LOGIN, login_panel);
				swap_panel.switchTo(ID_LOGIN);
			}
		});
		
		register_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO: REGISTER USER
			}
		});

		HintTextField username_txt = new HintTextField(Strings.HINT_USERNAME, HintTextField.TYPE_TEXT);
		HintTextField password_txt = new HintTextField(Strings.HINT_PASSWORD, HintTextField.TYPE_PASSWORD);
		HintTextField repassword_txt = new HintTextField(Strings.HINT_RETYPE_PASSWORD, HintTextField.TYPE_PASSWORD);
		HintTextField department_key_txt = new HintTextField(Strings.HINT_DEPARTMENT_KEY, HintTextField.TYPE_PASSWORD);

		username_txt.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
		password_txt.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
		repassword_txt.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
		department_key_txt.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
		repassword_txt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, margins, 1, margins, Colors.dark_grey),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		username_txt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, margins, 1, margins, Colors.dark_grey),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		password_txt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, margins, 1, margins, Colors.dark_grey),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		department_key_txt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, margins, 0, margins, Colors.dark_grey),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		//Make buttons occupy all width
		JPanel btn_cancel_panel = new JPanel();
		btn_cancel_panel.setLayout(new BorderLayout());
		btn_cancel_panel.add(cancel_btn);
		btn_cancel_panel.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
		btn_cancel_panel.setBorder(BorderFactory.createMatteBorder(0, margins, 0, margins, Colors.dark_grey));
		
		JPanel btn_register_panel = new JPanel();
		btn_register_panel.setLayout(new BorderLayout());
		btn_register_panel.add(register_btn);
		btn_register_panel.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
		btn_register_panel.setBorder(BorderFactory.createMatteBorder(0, margins, 0, margins, Colors.dark_grey));

		register_panel.add(Box.createVerticalGlue());
		register_panel.add(username_txt);
		register_panel.add(password_txt);
		register_panel.add(repassword_txt);
		register_panel.add(department_key_txt);
		register_panel.add(btn_register_panel);
		register_panel.add(btn_cancel_panel);
		register_panel.add(Box.createVerticalGlue());
	}
}
