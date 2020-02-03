package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.CSocket;

public class LeftPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private ActionListener actionHandler;
	private String name;
	private String userId;
	private CSocket cSocket;
	public LeftPanel(ActionListener actionHandler, String name, String userId, CSocket cSocket) {
		this.actionHandler =actionHandler;
		this.name = name;
		this.userId = userId;
		this.cSocket=cSocket;
		paint();
	}
	public void paint() {
		this.setPreferredSize(new Dimension(140,200));
		this.setBorder(new TitledBorder(new LineBorder(Color.gray,1)));
		MyPicture myPicture = new MyPicture(this.userId,cSocket);
		myPicture.setActionMap(null);
		this.add(myPicture);
		JLabel lb1 = new JLabel(name+" 님 환영합니다.");
		this.add(lb1);
		JButton mybt = new JButton("회원정보");
		mybt.setToolTipText("회원님의 정보를 보거나 수정할 수 있습니다.");
		mybt.addActionListener(actionHandler);
		mybt.setActionCommand("mybt");
		this.add(mybt);
		JButton logout = new JButton("로그아웃");
		logout.setToolTipText("로그인 화면으로 전환됩니다.");
		logout.addActionListener(actionHandler);
		logout.setActionCommand("logout");
		this.add(logout);
		

	}

}
