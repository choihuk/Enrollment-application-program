package ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.CSocket;


public class MyInfoPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextField tfId;
	private JTextField tfPw;
	private JTextField tfName;
	private String userId;
	private String password;
	private String name;
	private String studentNumber;
	private  String studentDepartment;
	private JButton bt2;
	private ActionListener actionHandler;
	private JTextField tfSN;
	private JTextField tfDM;
	private CSocket cSocket;
	
	public MyInfoPanel(String userId, String password, String name, String studentName, String studentDepartment, ActionListener actionHandler, CSocket cSocket) {
		this.actionHandler = actionHandler;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.studentNumber = studentName;
		this.cSocket=cSocket;
		this.studentDepartment = studentDepartment;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		paint();
	}
	public void paint() {

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p21 = new JPanel();
		JPanel p22 = new JPanel();
		JPanel p23 = new JPanel();
		JPanel p24 = new JPanel();
		JPanel p3 = new JPanel();
		
		p2.setLayout(new GridLayout(0,4));
		p2.add(p21);
		p2.add(p22);
		p2.add(p23);
		p2.add(p24);
		
		JButton homebt = new JButton("홈으로");
		homebt.addActionListener(actionHandler);
		homebt.setActionCommand("homebt");
		p1.add(homebt);
		
		JLabel lb1 = new JLabel("아이디");
		p21.add(lb1);
		this.tfId = new JTextField(10);
		tfId.setEnabled(false);
		tfId.setText(userId);
		p21.add(tfId);
		
		JLabel lb2 = new JLabel("비밀번호");
		p22.add(lb2);
		this.tfPw = new JTextField(10);
		tfPw.setText(password);
		tfPw.setEnabled(false);
		p22.add(tfPw);
		
		JLabel lb3 = new JLabel("회원이름");
		p23.add(lb3);
		this.tfName = new JTextField(10);
		tfName.setText(name);
		tfName.setEnabled(false);
		p23.add(tfName);
		
		JLabel lb4 = new JLabel("회원학번");
		p24.add(lb4);
		this.tfSN = new JTextField(10);
		tfSN.setText(studentNumber);
		tfSN.setEnabled(false);
		p24.add(tfSN);
		
		JLabel lb5 = new JLabel("회원  학과");
		p24.add(lb5);
		this.tfDM = new JTextField(10);
		tfDM.setText(studentDepartment);
		tfDM.setEnabled(false);
		p24.add(tfDM);
		
		JButton bt1 = new JButton("회원정보 수정");
		bt1.addActionListener(actionHandler);
		bt1.setActionCommand("bt1");
		p3.add(bt1);
		
		
		this.bt2 = new JButton("회원정보 저장");
		bt2.addActionListener(actionHandler);
		bt2.setActionCommand("bt2");
		bt2.setEnabled(false);
		p3.add(bt2);
		
		JButton bt3 = new JButton("회원 이미지 변경");
		bt3.addActionListener(actionHandler);
		bt3.setActionCommand("bt3");
		p3.add(bt3);
		
		
		p1.setBackground(new Color(255,255,255));
		p2.setBackground(new Color(255,255,255));
		p21.setBackground(new Color(255,255,255));
		p22.setBackground(new Color(255,255,255));
		p23.setBackground(new Color(255,255,255));
		p24.setBackground(new Color(255,255,255));
		p3.setBackground(new Color(255,255,255));
		this.setBorder(new TitledBorder(new LineBorder(Color.gray,1)));
		
		this.add(p1);
		this.add(p2);
		this.add(p3);
		
	}
	public void activation() {
		tfPw.setEnabled(true);
		tfName.setEnabled(true);
		tfSN.setEnabled(true);
		bt2.setEnabled(true);
		tfDM.setEnabled(true);
	}
	
	public void disactivation() {
		tfPw.setEnabled(false);
		tfName.setEnabled(false);
		tfSN.setEditable(false);
		bt2.setEnabled(false);
		tfDM.setEnabled(false);
	}
	public String getTfId() {
		return this.tfId.getText();
	}
	public String getTfPw() {
		return this.tfPw.getText();
	}
	public String getTfName() {
		return this.tfName.getText();
	}
	public String getTfSN() {
		return this.tfSN.getText();
	}
	public String getTfDM() {
		return this.tfDM.getText();
	}
}
