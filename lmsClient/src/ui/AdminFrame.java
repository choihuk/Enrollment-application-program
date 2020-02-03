package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.CSocket;

public class AdminFrame extends JFrame{
	private CSocket cSocket;
	
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	private AdminFrame adminFrame;
	public AdminFrame(CSocket cSocket) {
		this.adminFrame=this;
		this.cSocket=cSocket;
		ActionHandler actionHandler = new ActionHandler();
		
		this.setSize(800, 600);
		Dimension screen =Toolkit.getDefaultToolkit().getScreenSize();
		double w = screen.getWidth();
		double h = screen.getHeight();

		this.setLocation((int) (w/2)-(800/2),(int) (h/2)-(600/2));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		this.p1 = new JPanel();
		this.p2 = new JPanel();
		this.p3 = new JPanel();
		this.p4 = new JPanel();
		
		JLabel title = new JLabel("관리자 클라이언트");
		title.setFont(new Font("문체부 쓰기 정체", Font.BOLD, 40));
		p1.add(title);
		
		JLabel lb1 = new JLabel("서버 강제종료버튼");
		p4.add(lb1);
		
		JButton kill = new JButton("서버 강제종료");
		kill.setActionCommand("kill");
		kill.addActionListener(actionHandler);
		p4.add(kill);
		this.setLayout(new BorderLayout());
		
		
		p1.setBackground(new Color(255,255,255));
		p2.setBackground(new Color(255,255,255));
		p3.setBackground(new Color(255,255,255));
		p4.setBackground(new Color(255,255,255));
		panel.setBackground(new Color(255,255,255));
		this.add(p1,"North");
		this.add(p2,"West");
		this.add(p3,"East");
		this.add(p4,"South");
		this.add(panel,"Center");
	}
	private class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("kill")) {
				cSocket.killServer();
				adminFrame.dispose();
			}
			
		}
		
	}

}
