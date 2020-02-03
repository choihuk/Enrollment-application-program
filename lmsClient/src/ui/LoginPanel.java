package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import main.CSocket;


public class LoginPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private BufferedImage img = null;
	private LoginButton btn;
	private static  JTextField lb1;
	private static  JPasswordField lb2;
	private KeyHandler keyHandler;
	private JButton signUp;
	private ActionHandler actionHandler;
	private JButton search;
	private CSocket cSocket;
	
	@SuppressWarnings("static-access")
	public LoginPanel(LoginFrame loginFrame, CSocket cSocket) {
		
		this.actionHandler = new ActionHandler();
		this.keyHandler = new KeyHandler();
		this.cSocket=cSocket;
		try {
			img = ImageIO.read(new File("LoginImg.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setLayout(null);

		this.btn = new LoginButton("loginBTN",loginFrame,cSocket);
		btn.setBounds(95,420, 400, 65);
		this.add(btn);
		
		this.signUp = new JButton("회원가입");
		signUp.setBounds(400, 500, 100,20);
		signUp.addActionListener(actionHandler);
		signUp.setActionCommand("signUp");
		this.add(signUp);
		
		this.search = new JButton("Id/Pw 찾기");
		search.setBounds(290, 500, 100,20);
		search.addActionListener(actionHandler);
		search.setActionCommand("search");
		this.add(search);
		
		this.lb1 = new JTextField();
		lb1.setBounds(170,251, 340, 46);
		Border border = BorderFactory.createLineBorder(new Color(239,239,239));
		lb1.setBorder(border);
		lb1.addKeyListener(keyHandler);
		this.add(lb1);

		this.lb2 = new JPasswordField();
		lb2.setEchoChar('*');
		lb2.addKeyListener(keyHandler);
		lb2.setBorder(border);
		lb2.setBounds(170,349, 340, 46);
		this.add(lb2);
		
		
	}
	public static JTextField lb1() {
		return lb1;
	}
	public static JPasswordField lb2() {
		return lb2;
	}
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
		lb1.repaint();
		lb2.repaint();
		btn.repaint();
		signUp.repaint();
		search.repaint();
	}
	public class KeyHandler implements KeyListener{
		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
				btn.refreshKey();
			}
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("signUp")) {
				SignUpFrame signUpFrame = new SignUpFrame(cSocket);
				signUpFrame.setVisible(true);
			}else if(e.getActionCommand().equals("search")) {
				SearchFrame searchFrame = new SearchFrame(cSocket);
				searchFrame.setVisible(true);
			}
		}
		
	}

}
