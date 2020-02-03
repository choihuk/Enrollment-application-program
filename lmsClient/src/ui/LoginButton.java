package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.CSocket;
import view.VLogin;

public class LoginButton extends JButton{

	private static final long serialVersionUID = 1L;
	Color basicColor = new Color(193,222,249);
	Color changeColor= new Color(230,240,250);
	Color textColor= new Color(0,0,0);
	private MainFrame mainFrame;
	private LoginFrame loginFrame; 
	private CSocket cSocket;

	private Calendar t = Calendar.getInstance();
	private int year =  t.get(Calendar.YEAR);
	private int month = t.get(Calendar.MONTH)+1;
	private int date = t.get(Calendar.DATE);
	private int hour = t.get(Calendar.HOUR_OF_DAY);
	private int min = t.get(Calendar.MINUTE);
	private int[] startTime;
	private int[] endTime;
	private boolean time;
	public LoginButton(String Name, LoginFrame loginFrame, CSocket cSocket) {
		this.loginFrame = loginFrame;
		this.cSocket = cSocket; 
		LoginMouseHandler mouseHandler = new LoginMouseHandler();
		LoginActionHandler actionHandler = new LoginActionHandler();
		this.addMouseListener(mouseHandler);
		this.setBackground(basicColor);
		this.setForeground(textColor);
		this.setActionCommand(Name);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.addActionListener(actionHandler);
		this.setText("로그인");
		this.setFont(new Font("",Font.PLAIN,30));
	}
	class LoginMouseHandler implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {

		}
		@Override
		public void mouseEntered(MouseEvent e) {
			MouseEnterAction();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			MouseExitAction();
		}
		@Override
		public void mousePressed(MouseEvent e) {

		}
		@Override
		public void mouseReleased(MouseEvent e) {

		}
	}
	private void MouseEnterAction() {
		this.setBackground(changeColor);
	}
	private void MouseExitAction() {
		this.setBackground(basicColor);
	}
	public void refresh(ActionEvent e) {
		JTextField lb1 = LoginPanel.lb1();
		JPasswordField lb2 = LoginPanel.lb2();
		String userIdText = lb1.getText();
		@SuppressWarnings("deprecation")
		String passwordText = lb2.getText();
		Object[] object = cSocket.getTime("getTime,null");
		this.startTime = (int[]) object[0];
		endTime = (int[]) object[1];
		time = false;
		availableTime();

		if(userIdText.equals("admin")&&passwordText.equals("123123")) {
			AdminFrame adminFrame = new AdminFrame(cSocket);
			adminFrame.setVisible(true);
			loginFrame.dispose();
		}else {
			VLogin vLogin = new VLogin(cSocket);
			boolean isTrue = vLogin.authenticate(userIdText, passwordText);
			if(isTrue) {
				if(time) {
					this.mainFrame = new MainFrame(userIdText,passwordText,cSocket);
					mainFrame.setVisible(true);
					loginFrame.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "수강신청 기간이 아닙니다.", "시간 오류", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	public void refreshKey() {
		JTextField lb1 = LoginPanel.lb1();
		JPasswordField lb2 = LoginPanel.lb2();
		String userIdText = lb1.getText();
		@SuppressWarnings("deprecation")
		String passwordText = lb2.getText();

		Object[] object = cSocket.getTime("getTime,null");
		this.startTime = (int[]) object[0];
		endTime = (int[]) object[1];
		time = false;
		availableTime();
		if(userIdText.equals("admin")&&passwordText.equals("123123")) {
			AdminFrame adminFrame = new AdminFrame(cSocket);
			adminFrame.setVisible(true);
			loginFrame.dispose();
		}else {
			VLogin vLogin = new VLogin(cSocket);
			boolean isTrue = vLogin.authenticate(userIdText, passwordText);
			if(isTrue) {
				if(time) {
					this.mainFrame = new MainFrame(userIdText,passwordText,cSocket);
					mainFrame.setVisible(true);
					loginFrame.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "수강신청 기간이 아닙니다.", "시간 오류", JOptionPane.WARNING_MESSAGE);
				}
			}
		}

	}
	public class LoginActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			refresh(e);
		}
	}
	private void availableTime() {
		boolean a = false;
		boolean b = false;
		if(this.year>this.startTime[0]) {
			a=true;
		}else if(this.year==this.startTime[0]){
			if(this.month>this.startTime[1]) {
				a=true;
			}else if(this.month==this.startTime[1]){
				if(this.date>this.startTime[2]) {
					a=true;
				}else if(this.date==this.startTime[2]){
					if(this.hour>this.startTime[3]) {
						a=true;
					}else if(this.hour==this.startTime[3]){
						if(this.min>=this.startTime[4]) {
							a=true;
						}
					}
				}
			}
		}
		if(this.year<this.endTime[0]) {
			b=true;
		}else if(this.year==this.endTime[0]) {
			if(this.month<this.endTime[1]) {
				b=true;
			}else if(this.month==this.endTime[1]) {
				if(this.date<this.endTime[2]) {
					b=true;
				}else if(this.date==this.endTime[2]) {
					if(this.hour<this.endTime[3]) {
						b=true;
					}else if(this.hour==this.endTime[3]) {
						if(this.min<=this.endTime[4]) {
							b=true;
						}
					}
				}
			}
		}
		if(a&&b) {
			this.time=true;
		}
	}
}
